package start;

import sourceService.WeatherFactory;

public class Main {
    /**sourceName: 'weatherstack' or 'openweathermap'**/
    public static void main(String[] args) {
        WeatherFactory.getWeatherToCSVFile("weatherstack");
    }
}
