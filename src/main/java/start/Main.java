package start;

import sourceService.WeatherFactory;

public class Main {
    public static void main(String[] args) {
        WeatherFactory.getWeatherToCSVFile("weatherstack");
    }
}
