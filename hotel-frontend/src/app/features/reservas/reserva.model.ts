export interface Reserva {
  id?: number;
  clienteId: number;
  habitacionId: number;
  fechaIngreso: string;
  fechaSalida: string;
  estadoReserva: 'CONFIRMADA' | 'CHECK_IN' | 'CHECK_OUT' | 'CANCELADA';
  montoTotal: number;
  nombreCliente?: string;
  numeroHabitacion?: string;
}