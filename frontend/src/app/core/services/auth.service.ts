// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { BehaviorSubject, Observable, tap } from 'rxjs';
// import { Router } from '@angular/router';
// import { User, LoginRequest, LoginResponse } from '../user/user.model';

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private readonly API_URL = 'http://localhost:8080/api/auth';
//   private currentUserSubject = new BehaviorSubject<User | null>(null);
//   public currentUser$ = this.currentUserSubject.asObservable();

//   constructor(
//     private http: HttpClient,
//     private router: Router
//   ) {
//     this.loadUserFromStorage();
//   }

//   login(credentials: LoginRequest): Observable<LoginResponse> {
//     return this.http.post<LoginResponse>(`${this.API_URL}/login`, credentials)
//       .pipe(
//         tap(response => {
//           this.setSession(response);
//         })
//       );
//   }

//   logout(): void {
//     localStorage.removeItem('token');
//     localStorage.removeItem('user');
//     localStorage.removeItem('expires_at');
//     this.currentUserSubject.next(null);
//     this.router.navigate(['/login']);
//   }

//   isLoggedIn(): boolean {
    
//     const expiresAt = localStorage.getItem('expires_at');
//     if (!expiresAt) return false;
//     return Date.now() < parseInt(expiresAt);
//   }

//   getToken(): string | null {
//     return localStorage.getItem('token');
//   }

//   getCurrentUser(): User | null {
//     return this.currentUserSubject.value;
//   }

//   private setSession(authResult: LoginResponse): void {
//     const expiresAt = Date.now() + (authResult.expiresIn * 1000);
    
//     localStorage.setItem('token', authResult.token);
//     localStorage.setItem('user', JSON.stringify(authResult.user));
//     localStorage.setItem('expires_at', expiresAt.toString());
    
//     this.currentUserSubject.next(authResult.user);
//   }

//   private loadUserFromStorage(): void {
//     const userStr = localStorage.getItem('user');
//     if (userStr && this.isLoggedIn()) {
//       this.currentUserSubject.next(JSON.parse(userStr));
//     }
//   }
// }

import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { BehaviorSubject, Observable, tap, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { User, LoginRequest, LoginResponse } from '../user/user.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';



export interface AuthenticationResponse {
  accessToken: string;
  refreshToken: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
     private readonly API_URL = 'http://localhost:8080/api/auth';

  private readonly TOKEN_KEY = 'finapi_token';
  private readonly USER_KEY = 'finapi_user';
  private readonly REFRESH_TOKEN_KEY = 'finapi_refresh_token';

  private currentUserSubject = new BehaviorSubject<User | null>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, credentials)
      .pipe(
        tap(response => {
          console.log('Login response:', response);
          if (response.token && response.user) {
            this.setSession(response);
          }
        }),
        catchError(this.handleError)
      );
  }

 

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
      localStorage.removeItem(this.REFRESH_TOKEN_KEY);
    }
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp * 1000;
      const isValid = Date.now() < exp;

      if (!isValid) {
        this.logout();
      }

      return isValid;
    } catch (error) {
      console.error('Error parsing token:', error);
      this.logout();
      return false;
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.TOKEN_KEY);
    }
    return null;
  }

  getRefreshToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.REFRESH_TOKEN_KEY);
    }
    return null;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  
  private setSession(response: LoginResponse): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.TOKEN_KEY, response.token);
      localStorage.setItem(this.USER_KEY, JSON.stringify(response.user));
    }
    this.currentUserSubject.next(response.user);
  }

  
  private getUserFromStorage(): User | null {
    if (isPlatformBrowser(this.platformId)) {
      const userJson = localStorage.getItem(this.USER_KEY);
      return userJson ? JSON.parse(userJson) : null;
    }
    return null;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Si è verificato un errore durante il login';

    if (error.error instanceof ErrorEvent) {
      errorMessage = `Errore: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 401:
          errorMessage = 'Credenziali non valide';
          break;
        case 403:
          errorMessage = 'Accesso negato';
          break;
        case 404:
          errorMessage = 'Servizio non trovato';
          break;
        case 500:
          errorMessage = 'Errore interno del server';
          break;
        default:
          errorMessage = `Errore ${error.status}: ${error.message}`;
      }
    }

    console.error('Auth Error:', error);
    return throwError(() => new Error(errorMessage));
  }

 

}