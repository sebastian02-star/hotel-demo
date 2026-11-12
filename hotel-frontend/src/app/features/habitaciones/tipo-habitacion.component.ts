import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TipoHabitacionService } from '../../services/tipo-habitacion.service';

@Component({
  selector: 'app-tipo-habitacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './tipo-habitacion.component.html',
  styleUrls: ['./tipo-habitacion.component.css']
})
export class TipoHabitacionComponent implements OnInit {
  private fb = inject(FormBuilder);
  private tipoService = inject(TipoHabitacionService);

  tipos: any[] = [];
  isModalOpen: boolean = false;
  editandoId: number | null = null; 

  
  isConfirmModalOpen: boolean = false;
  tipoIdAEliminar: number | null = null;

  tipoForm: FormGroup = this.fb.group({
    nombre: ['', Validators.required]
  });

  ngOnInit(): void {
    this.cargarTipos();
  }

  cargarTipos(): void {
    this.tipoService.getTiposHabitacion().subscribe({
      next: (data) => this.tipos = data,
      error: (err) => console.error('Error al cargar tipos:', err)
    });
  }

  abrirModal(tipo?: any): void {
    this.tipoForm.reset();
    if (tipo) {
      this.editandoId = tipo.id;

      this.tipoForm.patchValue({ nombre: tipo.nombre });
    } else {
      this.editandoId = null;
    }
    this.tipoForm.enable();
    this.isModalOpen = true;
  }

  cerrarModal(): void {
    this.isModalOpen = false;
    this.editandoId = null;
  }

  guardar(): void {
    if (this.tipoForm.invalid) return;

    const rawNombre = this.tipoForm.get('nombre')?.value.trim();
    const nombreCapitalizado = rawNombre.charAt(0).toUpperCase() + rawNombre.slice(1).toLowerCase();
    const dataToSend = { nombre: nombreCapitalizado };

    if (this.editandoId !== null && this.editandoId !== undefined) {
      
      this.tipoService.actualizarTipoHabitacion(this.editandoId, dataToSend).subscribe({
        next: () => {
          this.cargarTipos();
          this.cerrarModal();
        },
        error: (err) => console.error('Error al actualizar el tipo de habitación:', err)
      });
    } else {
      
      this.tipoService.crearTipoHabitacion(dataToSend).subscribe({
        next: () => {
          this.cargarTipos();
          this.cerrarModal();
        },
        error: (err) => console.error('Error al guardar el tipo de habitación:', err)
      });
    }
  }
  eliminar(id: number): void {
    if (confirm('¿Estás seguro de desactivar este tipo de habitación?')) {
      this.tipoService.eliminarTipoHabitacion(id).subscribe({
        next: () => {
          this.cargarTipos(); 
        },
        error: (err) => console.error('Error al realizar eliminación:', err)
      });
    }
  }
  
  confirmarDesactivar(id: number): void {
    this.tipoIdAEliminar = id;
    this.isConfirmModalOpen = true;
  }

  cerrarConfirmModal(): void {
    this.isConfirmModalOpen = false;
    this.tipoIdAEliminar = null;
  }

  ejecutarDesactivacion(): void {
    if (this.tipoIdAEliminar !== null) {
      this.tipoService.eliminarTipoHabitacion(this.tipoIdAEliminar).subscribe({
        next: () => {
          this.cargarTipos();
          this.cerrarConfirmModal();
        },
        error: (err) => console.error('Error al realizar eliminación:', err)
      });
    }
  }
}