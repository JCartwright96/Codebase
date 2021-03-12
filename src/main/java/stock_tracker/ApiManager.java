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
        if(function  == FunctionType.TIME_SERIES_INTRADAY) {
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
    public String get(String symbol) throws IOException {
        this.symbol = symbol;
        StringBuffer content = new StringBuffer();
        if(validateUrlParameters()) {
            buildUrl();

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append("\n").append(inputLine);
            }
            in.close();

            urlConnection.disconnect();
        }
        return content.toString();
    }

    /**
     * Validates all urlParameters that are required have acceptable values
     * @return true if all values are valid or false if not
     */
    public boolean validateUrlParameters() {
        if(function == null) {
            return false;
        }
        if(symbol.equals("")) {
            return false;
        }
        if(function == FunctionType.TIME_SERIES_INTRADAY && interval == null) {
            setInterval(TimeInterval.FIVE_MIN);
        }
        return true;
    }

    /**
     * Builds the url for the api calls  based on the set ticker_symbol, function type and time interval if relevant
     * @throws MalformedURLException if the builtUrl is of the wrong style/form
     */
    public void buildUrl() throws MalformedURLException {
        if(interval != null) {
            url = new URL(BASE_URL + "function=" + function.toString() + "&symbol=" + symbol + "&interval=" + stringInterval + "&apikey=" + API_KEY);
        }
        else {
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
     * Converts the set time interval into a string variant accepted by the api
     */
    public void convertTimeInterval() {
        switch(interval) {
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
        ApiManager api = new ApiManager();
        System.out.println(api.get("GME"));
        System.out.println(api.get("TSLA"));
        ApiManager apiCustom = new ApiManager(FunctionType.TIME_SERIES_DAILY);
    }

}
