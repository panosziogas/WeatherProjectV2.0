package zio.range.weather.rest.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.inject.Singleton;

/**
 *
 * @author Panos
 */
@Singleton
public class RestresouceSerializer implements ObjectMapperCustomizer{
    
      public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    @Override
    public void customize(ObjectMapper om) {        
         final DateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT);
         om.setDateFormat(dateFormat);            
    }
    
}
