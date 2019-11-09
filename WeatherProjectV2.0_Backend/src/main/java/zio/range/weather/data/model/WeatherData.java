package zio.range.weather.data.model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Panos
 */
@Entity
@Table(name = "WeatherData")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "weatherDataId", nullable = false)
    private Integer weatherDataId;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logDate", nullable = false)
    private Date logDate = new Date();
    @Basic(optional = false)
    @NotNull
    @Column(name = "temperature", nullable = false)
    private Double temperature;
    @Basic(optional = false)
    @NotNull
    @Column(name = "humidity", nullable = false)
    private Integer humidity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pressure", nullable = false)
    private Double atmPressure;

    public WeatherData() {
        //JPA Required
    }

    public WeatherData(Double temperature, Integer humidity, Double atmPressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.atmPressure = atmPressure;
    }    
    

    public Integer getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(Integer weatherDataId) {
        this.weatherDataId = weatherDataId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getAtmPressure() {
        return atmPressure;
    }

    public void setAtmPressure(Double atmPressure) {
        this.atmPressure = atmPressure;
    }
    
    

}
