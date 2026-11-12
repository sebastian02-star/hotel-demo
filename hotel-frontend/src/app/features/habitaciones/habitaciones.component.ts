import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HabitacionService } from '../../services/habitacion.service';
import { Habitacion } from '../../models/habitacion.model';
import { TipoHabitacion } from '../../models/tipo-habitacion.model';

@Component({
  selector: 'app-habitaciones',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './habitaciones.component.html',
  styleUrl: './habitaciones.component.css'
})
export class HabitacionesComponent implements OnInit {
  private fb = inject(FormBuilder);
  private habitacionService = inject(HabitacionService);
  private cdr = inject(ChangeDetectorRef); 

  habitaciones: Habitacion[] = [];
  tiposHabitacion: TipoHabitacion[] = [];
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  isModalOpen = false;

  editandoId: number | null = null;

  isConfirmModalOpen = false;
  habitacionIdAEliminar: number | null = null;

  habitacionForm: FormGroup = this.fb.group({
    numeroHabitacion: ['', Validators.required],
    tipoHabitacion: [null, Validators.required],
    piso: [1, [Validators.required, Validators.min(1)]],
    capacidad: [1, [Validators.required, Validators.min(1)]],
    precioNoche: [0, [Validators.required, Validators.min(1)]],
    estado: ['DISPONIBLE', Validators.required],
    descripcion: ['']
  });

  ngOnInit(): void {
    this.cargarHabitaciones();
    this.cargarTiposHabitacion();
  }

  cargarHabitaciones(): void {
    this.isLoading = true;
    this.habitacionService.getHabitaciones().subscribe({
      next: (data) => {
        this.habitaciones = data;
        this.isLoading = false;
        this.cdr.detectChanges(); 
      },
      error: (err) => {
        this.errorMessage = err.message || 'Error al cargar las habitaciones.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarTiposHabitacion(): void {
    this.habitacionService.getTiposHabitacion().subscribe({
      next: (data: TipoHabitacion[]) => {
        this.tiposHabitacion = data;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error al cargar tipos de habitación', err);
      }
    });
  }

  abrirModal(habitacion?: Habitacion): void {
    this.habitacionForm.reset();
    this.errorMessage = '';
    this.successMessage = '';

    if (habitacion) {
      this.editandoId = habitacion.id ?? null;
      const tipoEncontrado = this.tiposHabitacion.find(t => t.id === habitacion.tipoHabitacion?.id) || habitacion.tipoHabitacion;
      
      this.habitacionForm.patchValue({
        numeroHabitacion: habitacion.numeroHabitacion,
        tipoHabitacion: tipoEncontrado,
        piso: habitacion.piso,
        capacidad: (habitacion as any).capacidad || 1,
        precioNoche: habitacion.precioNoche,
        estado: habitacion.estado,
        descripcion: habitacion.descripcion
      });
    } else {
      this.editandoId = null;
      this.habitacionForm.reset({ piso: 1, capacidad: 1, estado: 'DISPONIBLE', precioNoche: 0 });
    }
    this.isModalOpen = true;
  }

  cerrarModal(): void {
    this.isModalOpen = false;
    this.editandoId = null;
  }

  
  guardarHabitacion(): void {
    if (this.habitacionForm.invalid) {
      this.habitacionForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formValue = this.habitacionForm.value;

    if (this.editandoId !== null) {
      this.habitacionService.actualizarHabitacion(this.editandoId, formValue).subscribe({
        next: () => {
          this.cerrarModal(); 
          this.successMessage = 'Habitación actualizada exitosamente.'; 
          this.cargarHabitaciones();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'Error al actualizar la habitación.';
          this.isLoading = false;
        }
      });
    } else {

      this.habitacionService.crearHabitacion(formValue).subscribe({
        next: () => {
          this.cerrarModal(); 
          this.successMessage = 'Habitación registrada exitosamente.'; 
          this.cargarHabitaciones();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'Error al guardar la habitación.';
          this.isLoading = false;
        }
      });
    }
  }


  confirmarDesactivar(id: number): void {
    this.habitacionIdAEliminar = id;
    this.isConfirmModalOpen = true;
  }

  cerrarConfirmModal(): void {
    this.isConfirmModalOpen = false;
    this.habitacionIdAEliminar = null;
  }

  
  ejecutarDesactivacion(): void {
    if (this.habitacionIdAEliminar !== null) {
      this.isLoading = true;
      this.habitacionService.eliminarHabitacion(this.habitacionIdAEliminar).subscribe({
        next: () => {
          this.cerrarConfirmModal(); 
          this.successMessage = 'Habitación eliminada/desactivada correctamente.'; 
          this.cargarHabitaciones();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'Error al eliminar la habitación.';
          this.isLoading = false;
          this.cerrarConfirmModal();
        }
      });
    }
  }
}