import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.services'; 

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  
  if (req.url.includes('/auth/')) {
    return next(req);
  }

  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  let clonedReq = req;
  if (token) {
    clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(clonedReq).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocurrió un error inesperado en el servidor.';

      if (error.status === 401) {
        authService.logout(); 
        router.navigate(['/login']);
        errorMessage = 'La sesión ha expirado. Por favor, inicie sesión nuevamente.';
      } else if (error.status === 403) {
        errorMessage = 'No cuenta con los permisos necesarios para realizar esta acción.';
      } else if (error.status === 404) {
        errorMessage = 'El recurso solicitado no fue encontrado.';
      } else if (error.status >= 500) {
        errorMessage = 'Error interno en el servidor. Intente más tarde.';
      }

      console.error('[SICIG Error Interceptor]:', error);
      return throwError(() => new Error(errorMessage));
    })
  );
};