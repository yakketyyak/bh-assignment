import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {AccountCreation} from '../models/account-creation.model';
import {StorageService} from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  baseUrl = `${environment.apiUrl}/accounts`;
  constructor(private httpClient: HttpClient,
              private storage: StorageService) { }

  create(payload: AccountCreation){

    let accessToken = this.storage.getItem('access_token');
    accessToken = accessToken.access_token;
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${accessToken}`);

    return this.httpClient
      .post<any>(this.baseUrl, payload, {
        headers: httpHeaders,
        withCredentials: true,
      })
      .pipe(
        tap((data) => console.log(`create account`)),
        catchError((err) => throwError(err))
      );

  }
}
