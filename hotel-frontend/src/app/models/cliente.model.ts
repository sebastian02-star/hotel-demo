export interface Cliente {
  id?: number;
  nombres: string;
  apellidos: string;
  nacionalidad?: string;
  tipoDocumento: string;
  numeroDocumento: string;
  direccion?: string;
  correo: string;
  telefono: string;
  extranjero?: boolean;
  activo?: boolean;
}