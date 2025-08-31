import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent} from './pages/dashboard/dashboard.component';
//import { OrdersManagementComponent} from './pages/orders-management/orders-management.component';

const routes: Routes = [
  // { path: '', redirectTo: 'login', pathMatch: 'full' }, // reindirizza "" → "login"
  { path: 'login', component: LoginComponent },         // pagina login
  {
    path: 'dashboard',
    component: DashboardComponent // Questo contiene sidebar + router-outlet
  }
];

export { routes };
// @NgModule({
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule]
// })
// export class AppRoutingModule {}