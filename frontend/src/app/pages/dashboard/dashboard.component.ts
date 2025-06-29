// import { Component, OnInit, OnDestroy } from '@angular/core';
// import { Subject, takeUntil, forkJoin } from 'rxjs';
// import { AuthService } from '../../core/services/auth.service';
// import { DashboardService } from '../../core/services/dashboard.service';
// import { PracticesService } from '../../core/services/practices.service';
// import { User } from '../../core/models/user.model';
// import { Practice, PracticeFilter, PracticeStatus } from '../../core/models/practice.model';
// import { DashboardStats, ChartData, DateFilter } from '../../core/models/dashboard.model';

// @Component({
//   selector: 'app-dashboard',
//   templateUrl: './dashboard.component.html',
//   styleUrls: ['./dashboard.component.scss']
// })
// export class DashboardComponent implements OnInit, OnDestroy {
//   private destroy$ = new Subject<void>();

//   currentUser: User | null = null;
//   sidebarOpen = false;
  
//   // Dashboard data
//   stats: DashboardStats | null = null;
//   chartData: ChartData | null = null;
//   practices: Practice[] = [];
  
//   // Filters & Pagination
//   practiceFilter: PracticeFilter = {};
//   currentPage = 0;
//   pageSize = 10;
//   totalElements = 0;
//   totalPages = 0;
  
//   // Date filter
//   dateFilter: DateFilter = {
//     periodo: 'mese',
//     dataInizio: new Date(new Date().getFullYear(), new Date().getMonth(), 1),
//     dataFine: new Date()
//   };

//   // Loading states
//   loading = {
//     stats: false,
//     chart: false,
//     practices: false
//   };

//   // Practice status options
//   practiceStatusOptions = Object.values(PracticeStatus);

//   constructor(
//     private authService: AuthService,
//     private dashboardService: DashboardService,
//     private practicesService: PracticesService
//   ) {}

//   ngOnInit(): void {
//     this.authService.currentUser$
//       .pipe(takeUntil(this.destroy$))
//       .subscribe(user => {
//         this.currentUser = user;
//       });

//     this.loadDashboardData();
//     this.loadPractices();
//   }

//   ngOnDestroy(): void {
//     this.destroy$.next();
//     this.destroy$.complete();
//   }

//   toggleSidebar(): void {
//     this.sidebarOpen = !this.sidebarOpen;
//   }

//   logout(): void {
//     this.authService.logout();
//   }

//   loadDashboardData(): void {
//     this.loading.stats = true;
//     this.loading.chart = true;

//     forkJoin({
//       stats: this.dashboardService.getStats(this.dateFilter),
//       chartData: this.dashboardService.getChartData('entrate', this.dateFilter)
//     }).pipe(takeUntil(this.destroy$))
//     .subscribe({
//       next: ({ stats, chartData }) => {
//         this.stats = stats;
//         this.chartData = chartData;
//         this.loading.stats = false;
//         this.loading.chart = false;
//       },
//       error: () => {
//         this.loading.stats = false;
//         this.loading.chart = false;
//       }
//     });
//   }

//   loadPractices(): void {
//     this.loading.practices = true;
    
//     this.practicesService.getPractices(this.currentPage, this.pageSize, this.practiceFilter)
//       .pipe(takeUntil(this.destroy$))
//       .subscribe({
//         next: (response) => {
//           this.practices = response.content;
//           this.totalElements = response.totalElements;
//           this.totalPages = response.totalPages;
//           this.loading.practices = false;
//         },
//         error: () => {
//           this.loading.practices = false;
//         }
//       });
//   }

//   onFilterChange(): void {
//     this.currentPage = 0;
//     this.loadPractices();
//   }

//   onDateFilterChange(): void {
//     this.loadDashboardData();
//   }

//   onPageChange(page: number): void {
//     this.currentPage = page;
//     this.loadPractices();
//   }

//   clearFilters(): void {
//     this.practiceFilter = {};
//     this.currentPage = 0;
//     this.loadPractices();
//   }
// }