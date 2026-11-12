import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReservaService } from './reserva.service';
import { Reserva } from './reserva.model';
import { ClienteService } from '../clientes/cliente.service';
import { HabitacionService } from '../../services/habitacion.service';
import { Cliente } from '../../models/cliente.model';
import { Habitacion } from '../../models/habitacion.model';

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reservas.component.html',
  styleUrl: './reservas.component.css'
})
export class ReservasComponent implements OnInit {
  private fb = inject(FormBuilder);
  private reservaService = inject(ReservaService);
  private clienteService = inject(ClienteService);
  private habitacionService = inject(HabitacionService);

  reservas: Reserva[] = [];
  clientes: Cliente[] = [];
  habitaciones: Habitacion[] = [];
  
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  isModalOpen = false;

  reservaForm: FormGroup = this.fb.group({
    clienteId: ['', Validators.required],
    habitacionId: ['', Validators.required],
    fechaIngreso: ['', Validators.required],
    fechaSalida: ['', Validators.required],
    montoTotal: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.isLoading = true;
    this.reservaService.getReservas().subscribe({
      next: (data) => { this.reservas = data; this.isLoading = false; },
      error: (err) => { this.errorMessage = 'Error al cargar reservas.'; this.isLoading = false; }
    });

    this.clienteService.getClientes().subscribe(data => this.clientes = data);
    this.habitacionService.getHabitaciones().subscribe(data => this.habitaciones = data);
  }

  abrirModal(): void {
    this.reservaForm.reset();
    this.isModalOpen = true;
  }

  cerrarModal(): void {
    this.isModalOpen = false;
  }

  guardarReserva(): void {
    if (this.reservaForm.invalid) {
      this.reservaForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.reservaService.crearReserva(this.reservaForm.value).subscribe({
      next: () => {
        this.successMessage = 'Reserva creada exitosamente.';
        this.cargarDatos();
        this.cerrarModal();
      },
      error: (err) => {
        this.errorMessage = err.message || 'Error al procesar la reserva.';
        this.isLoading = false;
      }
    });
  }
}