export interface Usuario {
  id?: number;
  username: string;
  password?: string;
  nombreCompleto: string;
  rol: 'ADMIN' | 'RECEPCIONISTA' | 'MANTENIMIENTO';
}

export interface AuthResponse {
  token: string;
  username: string;
  rol: string;
  nombreCompleto: string;
}