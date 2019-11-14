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

  lineTempChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(173, 236, 187)',
    },
  ];
  lineHumChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(185, 233, 245)',
    },
  ];
  lineatmPreChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'rgba(236, 232, 173)',
    },
  ];

  lineChartLegend = true;
  lineChartPlugins = [];
  lineChartType = 'line';

  gaugeType = "semi";     
  gaugeTempValue;
  gaugeHumValue;
  gaugeAtmPressureValue;
  gaugeTempLabel = "Temperature";
  gaugeHumLabel ="Humidity";
  gaugeAtmPressureLabel="Pressure";
  gaugeTempAppendText = "°C";
  gaugeHumAppendText="%";
  gaugeAtmPressureText="mb";
  lastUpdate:any;

  constructor(private apiService: ApiService) { }
  ngOnInit() {

    let beginOfDate: Date = new Date(); 
    beginOfDate.setHours(0,0,0,0);
    let endOfDate: Date = new Date(); 
    endOfDate.setHours(23,59,59,999);

    let filterCharts = {
      "logDateFrom":beginOfDate,
      "logDateUntil":endOfDate,
      "searchOrder": "DESC",
      "pageNumber": 0,
      "pageSize": "144"
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
        let counter=0;

        for (let temp of Object.keys(this.weatherChartsDataResult)) {
          var dataObject = this.weatherChartsDataResult[temp];
          if(counter===0){
            this.lastUpdate = moment(dataObject.logDate).format('YYYY-MM-DD H:mm:s');
            this.gaugeTempValue = dataObject.temperature;
            this.gaugeHumValue= dataObject.humidity;
            this.gaugeAtmPressureValue= dataObject.atmPressure;
          }
          chartTempData.push(dataObject.temperature);
          chartHumData.push(dataObject.humidity);
          chartPreData.push(dataObject.atmPressure);
          chartDates.push(moment(dataObject.logDate).format('H:m:s'));
          counter++;
        }

      });

    this.templineChartData = [{ data: chartTempData, label: 'Temperature Chart' }];
    this.humlineChartData = [{ data: chartHumData, label: 'Humidity Chart' }];
    this.prelineChartData = [{ data: chartPreData, label: 'Atmospheric Pressure Chart' }];
    this.lineChartLabels = chartDates;

  }
}
