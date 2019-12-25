package sourceService;

import bean.Weather;
import com.google.gson.JsonObject;

import java.util.List;

public class OpenWeatherMap implements SourceService {

    @Override
    public List<Weather> getAllInNextDays(int countDays) {
        throw new UnsupportedOperationException("Метод не реалізований!");
    }

    @Override
    public Weather parseToWeatherInfo(JsonObject jsonObject) {
        throw new UnsupportedOperationException("Метод не реалізований!");
    }

    @Override
    public JsonObject executeGet(String targetURL) {
        throw new UnsupportedOperationException("Метод не реалізований!");
    }
}
