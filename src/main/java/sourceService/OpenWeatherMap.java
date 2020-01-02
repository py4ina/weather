package sourceService;

import bean.Weather;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OpenWeatherMap implements SourceService {
    private static final String SOURCE = "openweathermap";
    private static final String URI = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY = "&APPID=3f7bbf61aa43ad657a81ef7707438be4";

    @Override
    public String uriMaker(String city) {
        return URI+city+ API_KEY;
    }

    @Override
    public Weather parseToWeatherInfo(JsonObject jsonObject) {
        JsonObject main = jsonObject.get("main").getAsJsonObject();
        LocalDateTime localtime_epoch = LocalDateTime
                .ofEpochSecond(jsonObject.get("dt").getAsInt(), 0, ZoneOffset.UTC);

        float kelvin = main.get("temp").getAsFloat();
        float celsius = (float) (kelvin - 273.0);
        float fahrenheit = (float) ((9.0/5.0) * celsius + 32);

        return Weather.builder()
                .city(jsonObject.get("name").getAsString())
                .source(SOURCE)
                .forecastDate(localtime_epoch.toLocalDate().toString())
                .creationDate(LocalDate.now().toString())
                .temperatureF(fahrenheit)
                .temperatureC(celsius)
                .build();
    }

}
