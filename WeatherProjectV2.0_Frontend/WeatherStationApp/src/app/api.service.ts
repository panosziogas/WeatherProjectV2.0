import { Injectable } from '@angular/core';
import { HttpClient ,HttpHeaders} from '@angular/common/http';
import { environment } from './../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin':'*'
    })
  }

  public getWeatherDataByFilter(filter){
    return this.httpClient.post(environment.apiUrl + '/weather-station/search', JSON.stringify(filter), this.httpOptions)
  } 


}

