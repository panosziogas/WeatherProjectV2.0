/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zio.range.weather.rest.resources.dtos;

import java.util.Date;

/**
 *
 * @author Panos
 */
public class WeatherDataSearchResultDTO extends WeatherDataDTO{
    
    private Date logDate;

    public WeatherDataSearchResultDTO(Double temperature, Integer humidity, Double atmPressure , Date logDate) {
        super(temperature, humidity, atmPressure);
        this.logDate = logDate;        
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }   
    
}
