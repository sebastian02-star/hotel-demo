import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { CajaTurno, MovimientoCaja } from './caja.model';

@Injectable({
  providedIn: 'root'
})
export class CajaService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/caja`;

  obtenerEstadoCaja(): Observable<CajaTurno> {
    return this.http.get<CajaTurno>(`${this.apiUrl}/estado`);
  }

  abrirCaja(montoApertura: number): Observable<CajaTurno> {
    return this.http.post<CajaTurno>(`${this.apiUrl}/abrir`, { montoApertura });
  }

  cerrarCaja(montoCierre: number): Observable<CajaTurno> {
    return this.http.post<CajaTurno>(`${this.apiUrl}/cerrar`, { montoCierre });
  }

  registrarMovimiento(movimiento: MovimientoCaja): Observable<MovimientoCaja> {
    return this.http.post<MovimientoCaja>(`${this.apiUrl}/movimientos`, movimiento);
  }
}