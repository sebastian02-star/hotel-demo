export interface MovimientoCaja {
  id?: number;
  tipoMovimiento: 'INGRESO' | 'EGRESO';
  monto: number;
  descripcion: string;
  fechaRegistro?: string;
  usuarioRegistro?: string;
}

export interface CajaTurno {
  id?: number;
  montoApertura: number;
  montoCierre?: number;
  estadoCaja: 'ABIERTA' | 'CERRADA';
  fechaApertura: string;
  fechaCierre?: string;
}