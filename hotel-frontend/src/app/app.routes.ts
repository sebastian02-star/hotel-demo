import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { authGuard } from './guards/auth.guard';
import { ClientesComponent } from './features/clientes/clientes.component';
import { HabitacionesComponent } from './features/habitaciones/habitaciones.component';
import { ReservasComponent } from './features/reservas/reservas.component';
import { CajaComponent } from './features/caja/caja.component';
import { TipoHabitacionComponent } from './features/habitaciones/tipo-habitacion.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'clientes', pathMatch: 'full' }, 
      { path: 'clientes', component: ClientesComponent },
      { path: 'habitaciones', component: HabitacionesComponent },
      { path: 'reservas', component: ReservasComponent },
      { path: 'caja', component: CajaComponent },
      { path: 'tipos-habitacion', component: TipoHabitacionComponent }
    ]
  },
  { path: '**', redirectTo: 'login' }
];