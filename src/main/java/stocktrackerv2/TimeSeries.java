
package stocktrackerv2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Data class containing information on various time periods for specific stock api request
 * Contains raw json values and a map of pojos with string date keys
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TimeSeries {

    protected final LocalDateTime latestTradingDay = LocalDateTime.now();
    protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected Map<String, Map<String, String>> jsonContent;
    protected Map<String, TimeSeriesStock> stockValues;

    public TimeSeries(final Map<String, Map<String, String>> content) {
        this.jsonContent = content;
        stockValues = new HashMap<>();
        populateStockList(content);
    }

    public Map<String, Map<String, String>> getJsonContent() {
        return jsonContent;
    }

    public Map<String, TimeSeriesStock> getStockValues() {
        return stockValues;
    }

    public LocalDateTime getLatestTradingDay() {
        return latestTradingDay;
    }

    public Map<String, String> getQuote(String localDateDayFormat) throws Exception {
        if(jsonContent.get(localDateDayFormat) == null) {
            throw new Exception("No data for day");
        } else {
            return jsonContent.get(localDateDayFormat);
        }
    }

    public Map<String, String> getQuote(int year, int month, int day) throws Exception {
        return getQuote(createStringDate(year, month, day));
    }

    public Optional<TimeSeriesStock> getStockByDate(final String date) {
        return Optional.ofNullable(stockValues.get(date));
    }

    private String createStringDate(int year, int month, int day) {
        return convertIntToCorrectFormatString(year) + "-" + convertIntToCorrectFormatString(month) + "-" + convertIntToCorrectFormatString(day);
    }

    private String convertIntToCorrectFormatString(final int value) {
        if(value < 10) {
            return 0 + String.valueOf(value);
        } else {
            return String.valueOf(value);
        }
    }

    protected void populateStockList(Map<String, Map<String,String>> content) {
        String tradingDay = getLatestDayAsString();
        for (int i = 0; i < content.size(); i++) {
            while (content.get(tradingDay) == null) {
                tradingDay = decrementDay(tradingDay);
            }
            TimeSeriesStock stock = createSingleStockValue(tradingDay, content.get(tradingDay));
            stockValues.put(tradingDay, stock);
            tradingDay = decrementDay(tradingDay);
        }
    }

    private TimeSeriesStock createSingleStockValue(final String date, final Map<String, String> values) {
        return new TimeSeriesStock(date,
                Double.parseDouble(values.get("1. open")),
                Double.parseDouble(values.get("2. high")),
                Double.parseDouble(values.get("3. low")),
                Double.parseDouble(values.get("4. close")),
                Double.parseDouble(values.get("5. volume"))
        );
    }

    private String getLatestDayAsString() {
        return dateTimeFormatter.format(latestTradingDay);
    }

    private String decrementDay(String day) {
        return dateTimeFormatter.format(LocalDate.parse(day, dateTimeFormatter).minusDays(1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeries that = (TimeSeries) o;
        return Objects.equals(latestTradingDay, that.latestTradingDay) && Objects.equals(dateTimeFormatter, that.dateTimeFormatter) && Objects.equals(jsonContent, that.jsonContent) && Objects.equals(stockValues, that.stockValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latestTradingDay, dateTimeFormatter, jsonContent, stockValues);
    }

    @Override
    public String toString() {
        return "TimeSeriesDaily{" +
                "latestTradingDay=" + latestTradingDay +
                ", dateTimeFormatter=" + dateTimeFormatter +
                ", jsonContent=" + jsonContent +
                ", stockValues=" + stockValues +
                '}';
    }
}
