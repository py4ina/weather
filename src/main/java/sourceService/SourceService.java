package sourceService;

import bean.Weather;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public interface SourceService {
    String CITY_1 = "Moscow";
    String CITY_2 = "Kiev";
    String CITY_3 = "London";
    List<String> CITIES = Collections.unmodifiableList(Arrays.asList(CITY_1, CITY_2, CITY_3));

    String uriMaker(String city);
    Weather parseToWeatherInfo(JsonObject jsonObject);

    default List<Weather> getAllCitiesWeather() {
        return CITIES.stream()
                .map(this::uriMaker)
                .map(this::executeGet)
                .map(this::parseToWeatherInfo)
                .collect(Collectors.toList());
    }

    default JsonObject executeGet(String targetURL) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return new JsonParser().parse(response.toString()).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
