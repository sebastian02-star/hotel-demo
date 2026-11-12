import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  private router = inject(Router);
  nombreUsuario: string = '';
  rolUsuario: string = '';

  ngOnInit(): void {
    
    this.nombreUsuario = localStorage.getItem('username') || 'Administrador';
    this.rolUsuario = localStorage.getItem('role') || 'ROLE_ADMIN';
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}