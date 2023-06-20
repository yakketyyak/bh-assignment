import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  public getItem(key: string): any {
    return JSON.parse(localStorage.getItem(key));
  }
}
