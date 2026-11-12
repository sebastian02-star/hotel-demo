import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TipoHabitacion } from '../models/tipo-habitacion.model';

@Injectable({
  providedIn: 'root'
})
export class TipoHabitacionService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/tipos-habitacion';

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getTiposHabitacion(): Observable<TipoHabitacion[]> {
    return this.http.get<TipoHabitacion[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  crearTipoHabitacion(tipo: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, tipo, { headers: this.getHeaders() });
  }

  actualizarTipoHabitacion(id: number, tipo: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, tipo, { headers: this.getHeaders() });
  }

  eliminarTipoHabitacion(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }
}