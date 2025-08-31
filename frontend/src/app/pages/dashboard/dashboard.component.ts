import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
import { NgClass, TitleCasePipe, CommonModule } from '@angular/common';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { SidebarComponent } from '../../component/sidebar/sidebar.component';

// Interfaces
export interface Pratica {
  id: number;
  protocollo: string;
  codiceFiscale: string;
  data: string;
  importo: number;
  statoPratica: StatoPratica;
}

export enum StatoPratica {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
  PROCESSING = 'PROCESSING'
}

export interface FiltersData {
  dataInizio?: string;
  dataFine?: string;
  protocollo?: string;
  codiceFiscale?: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, TitleCasePipe,  SidebarComponent, NgClass, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  // Form
  

  filtersForm!: FormGroup;
  
  
  // Data
  pratiche: Pratica[] = [];
  totalElements = 0;
  totalPages = 0;
  currentPage = 0;
  pageSize = 10;
  
  // State
  loading = false;
  currentFilters: FiltersData = {};
  
  // Utilities
  private destroy$ = new Subject<void>();
  private baseUrl = 'http://localhost:8080/api/pratiche';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
    this.initializeForm();
  }

  ngOnInit(): void {
    this.setupFormSubscription();
    this.loadPratiche();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // Form Management
  private initializeForm(): void {
    this.filtersForm = this.fb.group({
      dataInizio: [''],
      dataFine: [''],
      protocollo: [''],
      codiceFiscale: ['']
    });
  }

  private setupFormSubscription(): void {
    this.filtersForm.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe(filters => {
        this.currentFilters = this.cleanFilters(filters);
        this.currentPage = 0;
        this.loadPratiche();
      });
  }

  clearFilters(): void {
    this.filtersForm.reset();
  }

  private cleanFilters(filters: any): FiltersData {
    const cleaned: FiltersData = {};
    
    Object.keys(filters).forEach(key => {
      const value = filters[key];
      if (value && typeof value === 'string' && value.trim() !== '') {
        cleaned[key as keyof FiltersData] = value.trim();
      } else if (value && typeof value !== 'string') {
        cleaned[key as keyof FiltersData] = value;
      }
    });
    
    return cleaned;
  }

  // Data Loading
  private loadPratiche(): void {
    this.loading = true;
    
    let params = new HttpParams()
      .set('page', this.currentPage.toString())
      .set('size', this.pageSize.toString());

    // Add filters to params
    if (this.currentFilters.dataInizio) {
      params = params.set('dataInizio', this.currentFilters.dataInizio);
    }
    if (this.currentFilters.dataFine) {
      params = params.set('dataFine', this.currentFilters.dataFine);
    }
    if (this.currentFilters.protocollo) {
      params = params.set('protocollo', this.currentFilters.protocollo);
    }
    if (this.currentFilters.codiceFiscale) {
      params = params.set('codiceFiscale', this.currentFilters.codiceFiscale);
    }

    this.http.get<PaginatedResponse<Pratica>>(this.baseUrl, { params })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          this.pratiche = response.content;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;
          this.loading = false;
        },
        error: (error) => {
          console.error('Errore nel caricamento delle pratiche:', error);
          this.loading = false;
          // In caso di errore, mostra dati mock per development
          this.loadMockData();
        }
      });
  }

  // Mock data for development/testing
  private loadMockData(): void {
    const mockPratiche: Pratica[] = [
      {
        id: 1,
        protocollo: 'PROT-2024-001',
        codiceFiscale: 'RSSMRA80A01H501Z',
        data: '2024-01-15',
        importo: 15000.00,
        statoPratica: StatoPratica.APPROVED
      },
      {
        id: 2,
        protocollo: 'PROT-2024-002',
        codiceFiscale: 'BNCGVN85B02H501A',
        data: '2024-01-16',
        importo: 25000.00,
        statoPratica: StatoPratica.PROCESSING
      },
      {
        id: 3,
        protocollo: 'PROT-2024-003',
        codiceFiscale: 'VRDLCU90C03H501B',
        data: '2024-01-17',
        importo: 8000.00,
        statoPratica: StatoPratica.PENDING
      },
      {
        id: 4,
        protocollo: 'PROT-2024-004',
        codiceFiscale: 'NRTFNC75D04H501C',
        data: '2024-01-18',
        importo: 12000.00,
        statoPratica: StatoPratica.REJECTED
      },
      {
        id: 5,
        protocollo: 'PROT-2024-005',
        codiceFiscale: 'GLLMTT88E05H501D',
        data: '2024-01-19',
        importo: 30000.00,
        statoPratica: StatoPratica.APPROVED
      }
    ];

    // Apply filters to mock data
    let filteredData = mockPratiche;
    
    if (this.currentFilters.protocollo) {
      filteredData = filteredData.filter(p => 
        p.protocollo.toLowerCase().includes(this.currentFilters.protocollo!.toLowerCase())
      );
    }
    
    if (this.currentFilters.codiceFiscale) {
      filteredData = filteredData.filter(p => 
        p.codiceFiscale.toLowerCase().includes(this.currentFilters.codiceFiscale!.toLowerCase())
      );
    }

    this.pratiche = filteredData;
    this.totalElements = filteredData.length;
    this.totalPages = Math.ceil(filteredData.length / this.pageSize);
    this.loading = false;
  }

  // Pagination
  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadPratiche();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadPratiche();
    }
  }

  // Formatting Utilities
  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('it-IT', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('it-IT', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  }

  getStatusLabel(status: StatoPratica): string {
    const statusLabels = {
      [StatoPratica.PENDING]: 'In Attesa',
      [StatoPratica.APPROVED]: 'Approvata',
      [StatoPratica.REJECTED]: 'Rifiutata',
      [StatoPratica.PROCESSING]: 'In Lavorazione'
    };
    return statusLabels[status] || status;
  }

  // Track by function for ngFor performance
  trackByFn(index: number, item: Pratica): number {
    return item.id;
  }

 
}