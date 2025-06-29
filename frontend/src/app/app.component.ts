import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  // template: `
  //   <div class="app-wrapper">
  //     <router-outlet></router-outlet>
  //   </div>
  // `,
  // styles: [`
  //   .app-wrapper {
  //     min-height: 100vh;
  //     width: 100%;
  //   }
  // `]
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent /*implements OnInit*/ {
  title = 'fintech-banking';

  // constructor(
  //   private router: Router,
  //   private authService: AuthService
  // ) {}

  // ngOnInit(): void {
  //   this.handleInitialNavigation();
    
  //   this.router.events
  //     .pipe(filter(event => event instanceof NavigationEnd))
  //     .subscribe((event: NavigationEnd) => {
  //       this.updatePageTitle(event.urlAfterRedirects);
  //     });
  // }

  // private handleInitialNavigation(): void {
  //   const currentUrl = this.router.url;
    
  //   if (currentUrl !== '/login' && !this.authService.isLoggedIn()) {
  //     this.router.navigate(['/login']);
  //   }
  //   else if (currentUrl === '/login' && this.authService.isLoggedIn()) {
  //     this.router.navigate(['/dashboard']);
  //   }
  //   else if (currentUrl === '/' && !this.authService.isLoggedIn()) {
  //     this.router.navigate(['/login']);
  //   }
  // }

  // private updatePageTitle(url: string): void {
  //   let title = 'Fintech Banking';
    
  //   switch (true) {
  //     case url.includes('/login'):
  //       title = 'Login - Fintech Banking';
  //       break;
  //     case url.includes('/dashboard'):
  //       title = 'Dashboard - Fintech Banking';
  //       break;
  //     default:
  //       title = 'Fintech Banking';
  //   }
    
  //   document.title = title;
  // }
}