import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/cliente.model';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  private fb = inject(FormBuilder);
  private clienteService = inject(ClienteService);
  private cdr = inject(ChangeDetectorRef); 

  clientes: Cliente[] = [];
  clienteSeleccionado: Cliente | null = null;
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  isModalOpen = false;


  isConfirmModalOpen = false;
  clienteIdAEliminar: number | null = null;

  clienteForm: FormGroup = this.fb.group({
    id: [null],
    nombres: ['', [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')]],
    apellidos: ['', [Validators.required, Validators.minLength(2), Validators.pattern('^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$')]],
    nacionalidad: ['Perú', Validators.required],
    tipoDocumento: ['DNI', Validators.required],
    numeroDocumento: ['', [Validators.required, Validators.pattern('^\\d{8}$')]],
    direccion: [''],
    telefono: ['', [Validators.required, Validators.pattern('^\\+?[0-9]{7,15}$')]],
    correo: ['', [Validators.required, Validators.email]],
    extranjero: [false]
  });

  ngOnInit(): void {
    this.cargarClientes();
    this.configurarValidadoresDinamicos();
  }

  configurarValidadoresDinamicos(): void {
    this.clienteForm.get('tipoDocumento')?.valueChanges.subscribe(tipo => {
      const docControl = this.clienteForm.get('numeroDocumento');
      docControl?.setValue(''); 
      
      if (tipo === 'DNI') {
        docControl?.setValidators([Validators.required, Validators.pattern('^\\d{8}$')]);
      } else if (tipo === 'Carnet Ext.' || tipo === 'CE') {
        docControl?.setValidators([Validators.required, Validators.pattern('^\\d{9}$')]);
      } else if (tipo === 'Pasaporte') {
        docControl?.setValidators([Validators.required, Validators.pattern('^[0-9A-Za-z]{8,12}$')]);
      }
      docControl?.updateValueAndValidity();
    });

    this.clienteForm.get('nacionalidad')?.valueChanges.subscribe(pais => {
      const esExtranjero = pais ? pais.trim().toLowerCase() !== 'perú' : false;
      this.clienteForm.patchValue({ extranjero: esExtranjero }, { emitEvent: false });
    });
  }

  soloLetras(event: KeyboardEvent): void {
    const charCode = event.key;
    const regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]$/;
    if (!regex.test(charCode)) {
      event.preventDefault();
    }
  }

  soloNumerosDoc(event: KeyboardEvent): void {
    const tipo = this.clienteForm.get('tipoDocumento')?.value;
    if (tipo === 'DNI' || tipo === 'CE' || tipo === 'Carnet Ext.') {
      const charCode = event.key;
      const regex = /^[0-9]$/;
      if (!regex.test(charCode) && event.key !== 'Backspace' && event.key !== 'Tab') {
        event.preventDefault();
      }
    }
  }

  getMaxLengthDocumento(): number {
    const tipo = this.clienteForm.get('tipoDocumento')?.value;
    if (tipo === 'DNI') return 8;
    if (tipo === 'CE' || tipo === 'Carnet Ext.') return 9;
    return 12;
  }

  cargarClientes(): void {
    this.isLoading = true;
    this.clienteService.getClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.isLoading = false;
        this.cdr.detectChanges(); 
      },
      error: (err) => {
        this.errorMessage = err.message || 'Error al cargar la lista de clientes.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  abrirModalNuevo(): void {
    this.clienteSeleccionado = null;
    this.clienteForm.reset({ nacionalidad: 'Perú', tipoDocumento: 'DNI', extranjero: false });
    this.isModalOpen = true;
  }

  abrirModalEditar(cliente: Cliente): void {
    this.clienteSeleccionado = cliente;
    this.clienteForm.patchValue(cliente);
    this.isModalOpen = true;
  }

  cerrarModal(): void {
    this.isModalOpen = false;
    this.clienteForm.reset();
  }

  
  guardarCliente(): void {
    if (this.clienteForm.invalid) {
      this.clienteForm.markAllAsTouched();
      return;
    }

    const clienteData: Cliente = this.clienteForm.value;
    this.isLoading = true;

    if (clienteData.id) {
      this.clienteService.actualizarCliente(clienteData.id, clienteData).subscribe({
        next: () => {
          this.cerrarModal(); 
          this.successMessage = 'Cliente actualizado correctamente.'; 
          this.cargarClientes();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'Error al actualizar el cliente.';
          this.isLoading = false;
        }
      });
    } else {
      this.clienteService.crearCliente(clienteData).subscribe({
        next: () => {
          this.cerrarModal(); 
          this.successMessage = 'Cliente registrado correctamente.'; 
          this.cargarClientes();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'Error al registrar el cliente.';
          this.isLoading = false;
        }
      });
    }
  }


  eliminarCliente(id: number): void {
    this.clienteIdAEliminar = id;
    this.isConfirmModalOpen = true;
  }

  cerrarConfirmModal(): void {
    this.isConfirmModalOpen = false;
    this.clienteIdAEliminar = null;
  }

  ejecutarEliminacion(): void {
    if (this.clienteIdAEliminar !== null) {
      this.isLoading = true;
      this.clienteService.eliminarCliente(this.clienteIdAEliminar).subscribe({
        next: () => {
          this.cerrarConfirmModal(); 
          this.successMessage = 'Cliente eliminado del sistema.'; 
          this.cargarClientes();
          this.isLoading = false;
        },
        error: (err) => {
          this.errorMessage = err.message || 'No se pudo eliminar el cliente.';
          this.isLoading = false;
          this.cerrarConfirmModal();
        }
      });
    }
  }
}