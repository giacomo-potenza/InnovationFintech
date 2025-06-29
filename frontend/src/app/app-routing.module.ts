import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//import { AuthGuard } from './core/services/auth.guard';
//import { LoginGuard } from './core/guards/login.guard';

const routes: Routes = [
  {
    path: 'login',
    //loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule),
    // canActivate: [LoginGuard] // Previene accesso se già loggato
  },
  {
    path: 'dashboard',
    // loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule),
    // canActivate: [AuthGuard]
  },
  {
    path: '',
    redirectTo: '/login', // Cambiato: reindirizza sempre al login
    pathMatch: 'full'
  },
  {
    path: '**',
    redirectTo: '/login' // Anche per pagine non trovate
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // Opzioni aggiuntive per il routing
    enableTracing: false, // Metti true solo per debug
    useHash: false, // Usa HTML5 routing
    scrollPositionRestoration: 'top'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }