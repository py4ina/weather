import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Maim {
    private static final String URI = "http://api.weatherstack.com/";
    private static final String TYPE = "current";
    private static final String ACCESS_KEY = "664039f5ca83d922bcd3213d9b5b514c";
    private static final String CITY = "London";

    private static final String PARAMETER =  TYPE+"?access_key="+ACCESS_KEY+"&query="+CITY;

    public static void main(String[] args) {
        executePost(URI+PARAMETER);
    }

    public static String executePost(String targetURL) {
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
                System.out.println(line);
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
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
