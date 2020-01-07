package sourceService;

import bean.Weather;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherFactory {
    private static final String SOURCE_1 = "weatherstack";
    private static final String SOURCE_2 = "openweathermap";
    private static final String PATH_FOLDER = System.getProperty("user.dir") + "/src/main/resources/";

    private static final String CITY = "City";
    private static final String SOURCE = "Source";
    private static final String FORECAST_DATE = "Forecast Date";
    private static final String CREATION_DATE = "Creation Date";
    private static final String TEMPERATURE_F = "Temperature F";
    private static final String TEMPERATURE_C = "Temperature C";

    public static void getWeatherEveryDayToCSVFile(String sourceName){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
                () -> getWeatherOnceToCSVFile(sourceName)
                , 0, 1, TimeUnit.DAYS);
    }

    private static void getWeatherOnceToCSVFile(String sourceName){
        SourceService sourceService = WeatherFactory.defineSource(sourceName);
        List<Weather> weather = sourceService.getAllCitiesWeather();
        WeatherFactory.writeToCSV(weather);
    }

    private static SourceService defineSource(String sourceName){
        SourceService sourceService;
        switch (sourceName){
            case SOURCE_1: sourceService = new WeatherStack(); break;
            case SOURCE_2: sourceService = new OpenWeatherMap(); break;
            default: throw new UnsupportedOperationException("Source name is incorrect");
        }
        return sourceService;
    }

    private static void writeToCSV(List<Weather> weathers) {
        String fileName = "weather_"+LocalDateTime.now().toString()+".csv";
        File file = new File(PATH_FOLDER+fileName);

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))){
            String[] header = {CITY, SOURCE, FORECAST_DATE, CREATION_DATE, TEMPERATURE_F, TEMPERATURE_C};
            writer.writeNext(header);
            weathers.forEach(weather -> writer.writeNext(createCells(weather)));

            System.out.println("file: '"+file+"' is created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] createCells(Weather weather) {
        return new String[]{
                weather.getCity(), weather.getSource(), weather.getForecastDate(),
                weather.getCreationDate(), String.valueOf(weather.getTemperatureF()),
                String.valueOf(weather.getTemperatureC())
        };
    }
}