import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CajaService } from './caja.service';
import { CajaTurno } from './caja.model';

@Component({
  selector: 'app-caja',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './caja.component.html',
  styleUrl: './caja.component.css'
})
export class CajaComponent implements OnInit {
  private fb = inject(FormBuilder);
  private cajaService = inject(CajaService);

  cajaActual: CajaTurno | null = null;
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  cajaForm: FormGroup = this.fb.group({
    monto: [0, [Validators.required, Validators.min(1)]]
  });

  ngOnInit(): void {
    this.verificarCaja();
  }

  verificarCaja(): void {
    this.isLoading = true;
    this.cajaService.obtenerEstadoCaja().subscribe({
      next: (data) => {
        this.cajaActual = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'No se pudo obtener el estado de la caja.';
        this.isLoading = false;
      }
    });
  }

  abrirTurno(): void {
    if (this.cajaForm.invalid) return;
    const monto = this.cajaForm.get('monto')?.value;

    this.cajaService.abrirCaja(monto).subscribe({
      next: (res) => {
        this.cajaActual = res;
        this.successMessage = 'Caja abierta correctamente para el turno.';
        this.cajaForm.reset();
      },
      error: (err) => {
        this.errorMessage = err.message || 'Error al abrir la caja.';
      }
    });
  }

  cerrarTurno(): void {
    if (this.cajaForm.invalid) return;
    const monto = this.cajaForm.get('monto')?.value;

    this.cajaService.cerrarCaja(monto).subscribe({
      next: (res) => {
        this.cajaActual = res;
        this.successMessage = 'Turno de caja cerrado con éxito.';
        this.cajaForm.reset();
      },
      error: (err) => {
        this.errorMessage = err.message || 'Error al cerrar la caja.';
      }
    });
  }
}