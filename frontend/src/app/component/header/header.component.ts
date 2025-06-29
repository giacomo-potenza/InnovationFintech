import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{
    utente: any;
    

   constructor(private authService: AuthService) {}

   ngOnInit(): void {
    this.utente = this.authService.getUtenteLoggato(); // es: { matricola: 'A1234', tipo: 'manager' }
  }
}


