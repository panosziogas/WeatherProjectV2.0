import { Component, OnInit } from '@angular/core';
import { ApiService } from './api.service';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import * as moment from 'moment';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'WeatherStationApp';
  weatherDataResult: any;
  weatherChartsDataResult: any;

  public templineChartData: ChartDataSets[];
  public humlineChartData: ChartDataSets[];
  public prelineChartData: ChartDataSets[];
  public lineChartLabels: Label[];


  lineChartOptions = {
    responsive: true,
  };

  lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(103, 176, 235)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';

  constructor(private apiService: ApiService) { }
  ngOnInit() {

    let filter = {
      "searchOrder": "DESC",
      "pageNumber": 0,
      "pageSize": "1"
    };

    this.apiService.getWeatherDataByFilter(filter)
      .subscribe((
        weatherData) => {
        console.log(weatherData);
        let payload = weatherData['payload'];
        this.weatherDataResult = payload['weatherData'];
      });

    let filterCharts = {
      "searchOrder": "DESC",
      "pageNumber": 0,
      "pageSize": "24"
    };

    let chartTempData = [];
    let chartHumData = [];
    let chartPreData = [];
    let chartDates = [];

    this.apiService.getWeatherDataByFilter(filterCharts)
      .subscribe((
        weatherData) => {
        console.log(weatherData);
        let payloadCharts = weatherData['payload'];
        this.weatherChartsDataResult = payloadCharts['weatherData'];

        for (let temp of Object.keys(this.weatherChartsDataResult)) {
          var dataObject = this.weatherChartsDataResult[temp];
          console.log(dataObject.temperature);
          chartTempData.push(dataObject.temperature);
          chartHumData.push(dataObject.humidity);
          chartPreData.push(dataObject.atmPressure);
          chartDates.push(moment(dataObject.logDate).format('H:m:s'));
        }

      });

    this.templineChartData = [{ data: chartTempData, label: 'Temperature Chart' }];
    this.humlineChartData = [{ data: chartHumData, label: 'Humidity Chart' }];
    this.prelineChartData = [{ data: chartPreData, label: 'Atmospheric Pressure Chart' }];
    this.lineChartLabels = chartDates;

  }
}
