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
            if(interval.length > 0) {
                setInterval(interval[0]);
            }
            else {
                setInterval(TimeInterval.FIVE_MIN);
            }
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

    /**
     * Returns the api response as a single string for reading/debugging purposes
     * @param symbol the ticker symbol for the stock to get
     * @return a string of the all the stock information
     * @throws IOException if the call fails
     */
    public String getStringDebug(String symbol) throws IOException {
        this.symbol = symbol;
        StringBuilder content = new StringBuilder();
        ArrayList<Map<String, String>> stockListings = new ArrayList<>();
        if (validateUrlParameters()) {
            buildUrl();
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                content.append("\n").append(inputLine);
            }
            in.close();
            urlConnection.disconnect();
        }
        return content.toString();
    }

    /**
     * Formats the singular string response into an ArrayList of Map<String, String> when the api FunctionType is GLOBAL_QUOTE
     * The ArrayList will always have a size of 1 but follows the same return typing as formatting for other FunctionTypes
     * @param in the BufferedReader reading through each line of the response
     * @return an a list of the stock information
     * @throws IOException if th reader fails
     */
    private ArrayList<Map<String, String>> formatStockGlobalQuote(BufferedReader in) throws IOException {
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

    /**
     * Formats the singular string read from the Api call stream into an ArrayList of Map<String, String>.
     * Each Map<String, String> contains information about the stock during a specific time frame
     * @param in the BufferedReader reading through each line of the response from the Api Call
     * @return a list of Map<String, String> for each time period tracked for the stock
     * @throws IOException if the reader fails
     */
    private ArrayList<Map<String, String>> formatStockMultipleDays(BufferedReader in) throws IOException {
        ArrayList<Map<String, String>> listOfStock = new ArrayList<>();
        Map<String, String> content = new HashMap<>();
        String inputLine;
        // A count is used to skip the first several lines of the response as this is unneeded meta data
        // A different numbers of lines is skipped depending on the FunctionType used
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
                        String dayTraded = inputLine.replaceAll("\\s", "").substring(1, inputLine.replaceAll("\\s", "").length() - 3);
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

    /**
     * Returns the value from a JSONString key value pair returned as part of the api response
     * @param inputLine the current line being read from the inputStream
     * @return the value required from the current line
     */
    private String getValue(String inputLine) {
        return inputLine.substring(inputLine.indexOf(":") + 3, inputLine.length() - 2);
    }

    /**
     * Validates all urlParameters that are required have acceptable values
     * @return true if all values are valid or false if not
     */
    private boolean validateUrlParameters() {
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
    private void buildUrl() throws MalformedURLException {
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

    /**
     * Converts the current time interval into a string variant accepted by the api
     */
    private void convertTimeInterval() {
        switch (interval) {
            case ONE_MIN -> stringInterval = "1min";
            case FIFTEEN_MIN -> stringInterval = "15min";
            case THIRTY_MIN -> stringInterval = "30min";
            case SIXTY_MIN -> stringInterval = "60min";
            default -> stringInterval = "5min";
        }
    }

    public static void main(String[] args) throws IOException {
        ApiManager api = new ApiManager(FunctionType.GLOBAL_QUOTE);
        System.out.println(api.getStringDebug("AAPL"));
    }
}
