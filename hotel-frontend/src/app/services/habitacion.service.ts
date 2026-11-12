import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Habitacion } from '../models/habitacion.model';
import { TipoHabitacion } from '../models/tipo-habitacion.model';

@Injectable({
  providedIn: 'root'
})
export class HabitacionService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/habitaciones`;
  private apiTiposUrl = `${environment.apiUrl}/tipos-habitacion`;

  getTiposHabitacion(): Observable<TipoHabitacion[]> {
    return this.http.get<TipoHabitacion[]>(this.apiTiposUrl);
  }
  
  getHabitaciones(): Observable<Habitacion[]> {
    return this.http.get<Habitacion[]>(this.apiUrl);
  }

  crearHabitacion(habitacion: any): Observable<Habitacion> {
    return this.http.post<Habitacion>(this.apiUrl, habitacion);
  }

  
  actualizarHabitacion(id: number, habitacion: any): Observable<Habitacion> {
    return this.http.put<Habitacion>(`${this.apiUrl}/${id}`, habitacion);
  }

  actualizarEstado(id: number, estado: string): Observable<Habitacion> {
    return this.http.patch<Habitacion>(`${this.apiUrl}/${id}/estado`, { estado });
  }

  eliminarHabitacion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}