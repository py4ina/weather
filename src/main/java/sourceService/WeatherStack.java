package sourceService;

import bean.Weather;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherStack implements SourceService {
    private static final String SOURCE = "weatherstack";
    private static final String URI = "http://api.weatherstack.com/current?access_key=664039f5ca83d922bcd3213d9b5b514c&query=";

    @Override
    public List<Weather> getAllInNextDays(int countDays) {
        List<Weather> weathers = CITIES.stream()
                .map(city -> URI + city)
                .map(this::executeGet)
                .map(this::parseToWeatherInfo)
                .collect(Collectors.toList());
        return weathers;
    }

    @Override
    public Weather parseToWeatherInfo(JsonObject jsonObject) {
        JsonObject location = jsonObject.get("location").getAsJsonObject();
        JsonObject current = jsonObject.get("current").getAsJsonObject();

        LocalDateTime localtime_epoch = LocalDateTime
                .ofEpochSecond(location.get("localtime_epoch").getAsInt(), 0, ZoneOffset.UTC);

        float celsius = current.get("temperature").getAsFloat();
        float fahrenheit = (float) ((9.0/5.0) * celsius + 32);

        return Weather.builder()
                .city(location.get("name").getAsString())
                .source(SOURCE)
                .forecastDate(localtime_epoch.toLocalDate().toString())
                .creationDate(LocalDate.now().toString())
                .temperatureF(fahrenheit)
                .temperatureC(celsius)
                .build();
    }
}
