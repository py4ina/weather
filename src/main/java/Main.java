import bean.Weather;
import sourceService.SourceService;
import sourceService.WeatherFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SourceService sourceService = WeatherFactory.defineSource("weatherstack");
        List<Weather> weather = WeatherFactory.getWeatherInNextDays(sourceService);
        WeatherFactory.writeToCSV(weather);
        System.out.println(weather);

    }
}
