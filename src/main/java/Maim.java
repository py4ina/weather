import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Maim {
    private static final String ACCESS_KEY = "664039f5ca83d922bcd3213d9b5b514c";
    private static final String CITY = "New York";
    private static final String URI = "https://api.weatherstack.com/current";
           ;
    private static final String PARAMMETER =  "?access_key=664039f5ca83d922bcd3213d9b5b514c&query=New York";

    public static void main(String[] args) throws IOException {
        System.out.println("Start!");

//        URL url = new URL("http://api.weatherstack.com/current?access_key="+ACCESS_KEY+"&query="+CITY);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        int responseCode = connection.getResponseCode();
//        System.out.println(responseCode);

        //Create connection
        executePost(URI, PARAMMETER);
    }

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
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
