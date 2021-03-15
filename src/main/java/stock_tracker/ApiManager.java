/*
 * Name     :   ApiManager.java
 * Author   :   joeca
 * Date     :   12 Mar 2021
 */
package stock_tracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiManager {

    private static final String API_KEY = "OSKXT3K0OSQ9UVZO";
    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private URL url;
    private String symbol;
    private FunctionType function;
    private TimeInterval interval;
    private String stringInterval;

    /**
     * Custom constructor with specified function time and optional interval depending on function type
     * @param function the FunctionTime to query the api with
     * @param interval the TimeInterval between stock calls if the function is TIME_SERIES_INTRADAY
     */
    public ApiManager(FunctionType function, TimeInterval... interval) {
        this.function = function;
        if (function == FunctionType.TIME_SERIES_INTRADAY) {
            setInterval(interval[0]);
        }
    }

    /**
     * Default constructor for ApiManager
     * Function defaults to TIME_SERIES_INTRADAY -- The latest information for a full day of trading
     * Interval defaults to 5mins -- the amount of time between stock pulls for the total day, in this case 5 minutes.
     */
    public ApiManager() {
        function = FunctionType.GLOBAL_QUOTE;
    }

    /**
     * Performs a get request with the built URL and returns any values as a string
     * @return a string of the stock values for the chosen symbol or an empty string if none  found
     * @throws IOException if the StreamReader fails on the input stream
     */
    public ArrayList<Map<String, String>> get(String symbol) throws IOException {
        this.symbol = symbol;
        ArrayList<Map<String, String>> stockListings = new ArrayList<>();
        if (validateUrlParameters()) {
            buildUrl();
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            if (function == FunctionType.GLOBAL_QUOTE) {
                stockListings = formatStockGlobalQuote(in);
            } else {
                stockListings = formatStockMultipleDays(in);
            }
            in.close();
            urlConnection.disconnect();
        }
        return stockListings;
    }

    public ArrayList<Map<String, String>> formatStockGlobalQuote(BufferedReader in) throws IOException {
        ArrayList<Map<String, String>> stockList = new ArrayList<>();
        Map<String, String> content = new HashMap<>();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("open")) {
                content.put("open", getValue(inputLine));
            }
            if (inputLine.contains("high")) {
                content.put("high", getValue(inputLine));
            }
            if (inputLine.contains("low")) {
                content.put("low", getValue(inputLine));
            }
            if (inputLine.contains("close")) {
                content.put("price", getValue(inputLine));
            }
            if (inputLine.contains("change percent")) {
                content.put("changePercent", getValue(inputLine));
            }
            if (!content.containsKey("dayTraded")) {
                content.put("dayTraded", LocalDate.now().toString());
            }
        }
        stockList.add(content);
        return stockList;
    }

    public ArrayList<Map<String, String>> formatStockMultipleDays(BufferedReader in) throws IOException {
        String inputLine;
        ArrayList<Map<String, String>> listOfStock = new ArrayList<>();
        Map<String, String> content = new HashMap<>();
        int count = 0;
        if (function == FunctionType.TIME_SERIES_MONTHLY) {
            count += 2;
        }
        if (function == FunctionType.TIME_SERIES_DAILY) {
            count++;
        }
        while ((inputLine = in.readLine()) != null) {
            if (count > 8) {
                if (!content.containsKey("open") || !content.containsKey("high") || !content.containsKey("low") || !content.containsKey("price") || !content.containsKey("dayTraded")) {
                    if (inputLine.contains("-")) {
                        String dayTraded = inputLine.replaceAll("\\s","").substring(1, inputLine.replaceAll("\\s","").length()-3);
                        content.put("dayTraded", dayTraded.substring(0, 10) + " " + dayTraded.substring(10));
                    }
                    if (inputLine.contains("open")) {
                        content.put("open", getValue(inputLine));
                    }
                    if (inputLine.contains("high")) {
                        content.put("high", getValue(inputLine));
                    }
                    if (inputLine.contains("low")) {
                        content.put("low", getValue(inputLine));
                    }
                    if (inputLine.contains("close")) {
                        content.put("price", getValue(inputLine));
                    }
                } else {
                    listOfStock.add(content);
                    content = new HashMap<>();
                }
            } else {
                count++;
            }
        }
        return listOfStock;
    }

    public String getValue(String inputLine) {
        return inputLine.substring(inputLine.indexOf(":") + 3, inputLine.length() - 2);
    }

    /**
     * Validates all urlParameters that are required have acceptable values
     * @return true if all values are valid or false if not
     */
    public boolean validateUrlParameters() {
        if (function == null) {
            return false;
        }
        if (symbol.equals("")) {
            return false;
        }
        if (function == FunctionType.TIME_SERIES_INTRADAY && interval == null) {
            setInterval(TimeInterval.FIVE_MIN);
        }
        return true;
    }

    /**
     * Builds the url for the api calls  based on the set ticker_symbol, function type and time interval if relevant
     * @throws MalformedURLException if the builtUrl is of the wrong style/form
     */
    public void buildUrl() throws MalformedURLException {
        if (interval != null) {
            url = new URL(BASE_URL + "function=" + function.toString() + "&symbol=" + symbol + "&interval=" + stringInterval + "&apikey=" + API_KEY);
        } else {
            url = new URL(BASE_URL + "function=" + function.toString() + "&symbol=" + symbol + "&apikey=" + API_KEY);
        }
    }

    /**
     * @return the current search function
     */
    public String getFunction() {
        return function.toString();
    }

    /**
     * Set the search function to use
     * @param function the search function
     */
    public void setFunction(FunctionType function) {
        this.function = function;
    }

    /**
     * @return a string of the current time interval
     */
    public String getInterval() {
        return interval.toString();
    }

    /**
     * Set the time interval and convert to an api correct string
     * @param interval the time interval between stock data
     */
    public void setInterval(TimeInterval interval) {
        this.interval = interval;
        convertTimeInterval();
    }

    public URL getUrl() {
        return url;
    }

    /**
     * Converts the current time interval into a string variant accepted by the api
     */
    public void convertTimeInterval() {
        switch (interval) {
            case ONE_MIN:
                stringInterval = "1min";
                break;
            case FIFTEEN_MIN:
                stringInterval = "15min";
                break;
            case THIRTY_MIN:
                stringInterval = "30min";
                break;
            case SIXTY_MIN:
                stringInterval = "60min";
                break;
            default:
                stringInterval = "5min";
        }
    }

    public static void main(String[] args) throws IOException {
        ApiManager api = new ApiManager(FunctionType.TIME_SERIES_INTRADAY, TimeInterval.FIVE_MIN);
        for (Map<String, String> stock: api.get("GME")) {
            System.out.println(stock.toString());
        }
        ApiManager apiCustom = new ApiManager(FunctionType.TIME_SERIES_DAILY);
    }

}
