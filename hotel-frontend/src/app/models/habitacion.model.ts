import { TipoHabitacion } from './tipo-habitacion.model';

export interface Habitacion {
  id?: number;
  numeroHabitacion: string;
  tipoHabitacion: TipoHabitacion;
  piso: number;
  precioNoche: number;
  estado: string; 
  descripcion?: string;
}