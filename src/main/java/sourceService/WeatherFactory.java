package sourceService;

import bean.Weather;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WeatherFactory {
    private static final String SOURCE_1 = "weatherstack";
    private static final String SOURCE_2 = "openweathermap";
    private static final String PATH = "/home/vitalik/new.csv";

    public static SourceService defineSource(String sourceName){
        SourceService sourceService;
        switch (sourceName){
            case SOURCE_1: sourceService = new WeatherStack(); break;
            case SOURCE_2: sourceService = new OpenWeatherMap(); break;
            default: throw new UnsupportedOperationException("Некоректне ім'я ресурсу");
        }
        return sourceService;
    }

    public static List<Weather> getWeatherInNextDays(SourceService sourceService){
        return sourceService.getAllInNextDays(3);
    }

    public static void writeToCSV(List<Weather> weathers) {
        File file = new File(PATH);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))){
            String[] header = { "City", "Source", "Forecast Date", "Creation Date", "Temperature F", "Temperature C" };
            writer.writeNext(header);

            weathers.forEach(weather -> {
                String[] array = {
                        weather.getCity(), weather.getSource(), weather.getForecastDate(),
                        weather.getCreationDate(), String.valueOf(weather.getTemperatureF()),
                        String.valueOf(weather.getTemperatureC())
                };
                writer.writeNext(array);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
