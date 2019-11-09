package zio.range.weather.rest.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import zio.range.weather.rest.resources.controllers.WeatherStationRestController;
import zio.range.weather.rest.resources.dtos.APIResponseEnvelope;
import zio.range.weather.rest.resources.dtos.WeatherDataDTO;

@Path("/weather-station")
@RequestScoped
public class WeatherStationRestResouce {

    @Inject
    private WeatherStationRestController weatherStationRestController;

    private static final Logger LOG = Logger.getLogger(WeatherStationRestResouce.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "weather-station";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postWeatherData(WeatherDataDTO weatherData) {
        if (weatherData == null) {
            LOG.severe("No data provided");
            APIResponseEnvelope responseEnvelope = new APIResponseEnvelope();
            responseEnvelope.setPayload(weatherData);
            responseEnvelope.setSuccess(false);
            responseEnvelope.addMessage("Failed to add data.");
            responseEnvelope.addMessage("No payload was provided.");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(responseEnvelope)
                    .build();
        }
        LOG.log(Level.INFO, "Weather data provided: Temperature {0} C° , Humidity {1} % and Atmospheric Pressure {2} mb",
                new Object[]{weatherData.getTemperature(), weatherData.getHumidity(), weatherData.getAtmPressure()});
        weatherStationRestController.persistWeatherData(weatherData);
        APIResponseEnvelope responseEnvelope = new APIResponseEnvelope();
        responseEnvelope.setPayload(weatherData);
        responseEnvelope.setSuccess(true);
        responseEnvelope.addMessage("Succefully added data.");
        return Response.status(Response.Status.OK).entity(responseEnvelope).build();
    }

}
