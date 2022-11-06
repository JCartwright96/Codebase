package stocktrackerv2;

import java.util.Objects;

/**
 * Entity representing stock values over time series.
 */
public class TimeSeriesStock {

    private final String date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;

    /**
     * Create a new stock
     * @param date the date of trading
     * @param open the open price
     * @param high the highest price of the day
     * @param low the lowest price of the day
     * @param close the closing price for the day
     * @param volume the volume of stock traded in the day
     */
    public TimeSeriesStock(String date, double open, double high, double low, double close, double volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    /**
     * @return the date of trading
     */
    public String getDate() {
        return date;
    }

    /**
     * @return the open price of the interval
     */
    public double getOpen() {
        return open;
    }

    /**
     * @return the highest price of the interval
     */
    public double getHigh() {
        return high;
    }

    /**
     * @return the lowest price of the interval
     */
    public double getLow() {
        return low;
    }

    /**
     * @return the closing price of the interval
     */
    public double getClose() {
        return close;
    }

    /**
     * @return the volume of stock traded of the interval
     */
    public double getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesStock that = (TimeSeriesStock) o;
        return Double.compare(that.open, open) == 0 && Double.compare(that.high, high) == 0 && Double.compare(that.low, low) == 0 && Double.compare(that.close, close) == 0 && Double.compare(that.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, high, low, close, volume);
    }

    @Override
    public String toString() {
        return "TimeSeriesDailyStock{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
