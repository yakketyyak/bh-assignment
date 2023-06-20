import { Injectable } from '@angular/core';
import {throwError} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {SearchRequest} from '../models/search.request.model';
import {catchError, tap} from 'rxjs/operators';
import {UserResponse} from '../models/user.response';
import {StorageService} from './storage.service';
import {User} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl = `${environment.apiUrl}/users`;
  constructor(private httpClient: HttpClient,
              private storage: StorageService) { }

  getAll(request: SearchRequest){
    const httpParams = new HttpParams()
      .set('size', '' + request.size)
      .set('page', '' + request.page);

    let accessToken = this.storage.getItem('access_token');
    accessToken = accessToken.access_token;
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${accessToken}`);

    return this.httpClient
      .get<UserResponse>(this.baseUrl, {
        headers: httpHeaders,
        params: httpParams,
        withCredentials: true
      })
      .pipe(
        tap((data) => console.log(`get all users`)),
        catchError((err) => throwError(err))
      );
  }

  getById(id){
    let accessToken = this.storage.getItem('access_token');
    accessToken = accessToken.access_token;
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${accessToken}`);

    return this.httpClient
      .get<User>(this.baseUrl + '/' + id, {
        headers: httpHeaders,
        withCredentials: true
      })
      .pipe(
        tap((data) => console.log(`get one users`)),
        catchError((err) => throwError(err))
      );
  }
}
