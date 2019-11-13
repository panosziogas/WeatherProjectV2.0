import { Component,OnInit } from '@angular/core';
import { ApiService } from './api.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'WeatherStationApp';
  weatherDataResult: any;
  
  constructor(private apiService: ApiService) { }
  ngOnInit() {

  let filter = {    
    "searchOrder":"DESC",
    "pageNumber":0,
    "pageSize":"1"
    };

    this.apiService.getWeatherDataByFilter(filter)
    .subscribe((
      weatherData)=>{
      console.log(weatherData);      
      let payload = weatherData['payload'];
      this.weatherDataResult = payload['weatherData'];
    });
  }
}
