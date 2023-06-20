import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {AuthRequest} from '../models/auth-resuest.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  baseUrl = `${environment.apiUrl}/auth`;
  constructor(private httpClient: HttpClient) { }

  login(payload: AuthRequest){
    return this.httpClient
      .post<any>(this.baseUrl + '/login', payload, {
        withCredentials: true,
      })
      .pipe(
        tap((data) => console.log(`login account`)),
        catchError((err) => throwError(err))
      );

  }
}
