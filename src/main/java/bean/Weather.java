package bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Weather {
    private String city;
    private String source;
    private String forecastDate;
    private String creationDate;
    private float temperatureF;
    private float temperatureC;
}
