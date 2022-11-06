package stocktrackerv2;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler {

    private static final String API_KEY = "OSKXT3K0OSQ9UVZO";
    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private static final String INTERVAL = "5min";

    private ApiHandler() {

    }

    public static String get(final String function, final String symbol) throws IOException {
        HttpURLConnection connection = setupConnection(new URL(generateURLString(function, symbol)));
        StringBuilder content = readContent(connection);
        connection.disconnect();
        return content.toString();
    }

    public static TimeSeriesDaily getStockVersion(final String function, final String symbol) throws IOException {
        String jsonString = get(function, symbol);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.readValue(jsonString, TimeSeriesDaily.class);
    }

    private static String generateURLString(final String function, final String symbol) {
        return BASE_URL + "function=" + function + "&symbol=" + symbol + "&apikey=" + API_KEY;
    }

    private static HttpURLConnection setupConnection(final URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    private static StringBuilder readContent(final HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder content = new StringBuilder();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content;
    }

    public static void main(String[] args) throws Exception {
        String jsonString = ApiHandler.get(Function.TIME_SERIES_DAILY_ADJUSTED.name(), "GME");
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        System.out.println(jsonString);
//        TimeSeriesWeekly daily = mapper.readValue(jsonString, TimeSeriesWeekly.class);
//        System.out.println(daily.getStockValues().toString());
    }
}