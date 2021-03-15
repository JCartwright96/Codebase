/*
 * Name     :   Stock.java
 * Author   :   joeca
 * Date     :   12 Mar 2021
 */
package stock_tracker;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * A class to track the information of a specific stock
 */
public class Stock {

    private String tickerSymbol;
    private LocalDate dayTraded;
    private double price;
    private double open;
    private double high;
    private double low;
    private double changePercent;

    /**
     * Default constructor
     */
    public Stock() {
        this.tickerSymbol = "";
        this.dayTraded = LocalDate.now();
        this.price = 0;
        this.low = 0;
        this.high = 0;
        this.changePercent = 0;
    }

    /**
     * Constructor to create a stock using just the ticker symbol and default values
     * @param tickerSymbol the ticker symbol of the stock
     */
    public Stock(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
        this.dayTraded = LocalDate.now();
        this.price = 0;
        this.low = 0;
        this.high = 0;
        this.changePercent = 0;
    }

    /**
     * Custom constructor to set values for all fields
     * @param tickerSymbol the ticker symbol of the stock
     * @param price the price of the stock at closing the previous day
     * @param open the opening price of the stock
     * @param high the highest price of the stock during the trading day
     * @param low the lowest price of the stock during the trading day
     * @param changePercent the percentage difference between the closing price for the current and previous day
     * @param dayTraded the date of the trading day
     */
    public Stock(String tickerSymbol, double price, double open, double high, double low, double changePercent, LocalDate dayTraded) {
        this.tickerSymbol = tickerSymbol;
        this.dayTraded = dayTraded;
        this.price = (price >= 0) ? price : 0;
        this.open = (open >= 0) ? open : 0;
        this.high = (high >= 0) ? high : 0;
        this.low = (low >= 0) ? low : 0;
        this.changePercent = changePercent;
    }

    /**
     * @return a String of the ticker symbol for the stock
     */
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Sets the ticker symbol for the stock
     * @param tickerSymbol the ticker symbol to set
     */
    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    /**
     * @return the closing price for stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the closing price of the stock
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = (price >= 0) ? price : 0;
    }

    /**
     * Rets
     * @return a date of the day traded for the stock
     */
    public LocalDate getDayTraded() {
        return dayTraded;
    }

    /**
     * Sets the date for the day of trading
     * @param dayTraded the date to set
     */
    public void setDayTraded(LocalDate dayTraded) {
        this.dayTraded = dayTraded;
    }

    /**
     * @return The price of the stock at the opening of the trading day
     */
    public double getOpen() {
        return open;
    }

    /**
     * Set the price of the stock at the opening of the trading day
     * @param open the price to set
     */
    public void setOpen(double open) {
        this.open = (open >= 0) ? open : 0;
    }

    /**
     * @return The highest price of the stock during the whole day of trading
     */
    public double getHigh() {
        return high;
    }

    /**
     * Set the highest traded price of the stock for a trading day
     * @param high the price to set
     */
    public void setHigh(double high) {
        this.high = (high >= 0) ? high : 0;
    }

    /**
     * @return The lowest price of the stock during the whole day of trading
     */
    public double getLow() {
        return low;
    }

    /**
     * Set the lowest price of the stock in the trading day
     * @param low the price to set
     */
    public void setLow(double low) {
        this.low = (low >= 0) ? low : 0;
    }

    /**
     * @return The percentage change between the current days closing and the previous days closing price. Will be positive if the price is higher for the current day or negative if the price is lower
     */
    public double getChangePercent() {
        return changePercent;
    }

    /**
     * Set the percentage change between the current days closing price = and the previous days closing price
     * @param changePercent the percent to set
     */
    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public void populateStock(Map<String, String> stockData) {
        setDayTraded(LocalDate.parse(stockData.get("dayTraded")));
        setHigh(Double.parseDouble(stockData.get("high")));
        setLow(Double.parseDouble(stockData.get("low")));
        setOpen(Double.parseDouble(stockData.get("open")));
        setTickerSymbol(stockData.get("tickerSymbol"));
        setPrice(Double.parseDouble(stockData.get("price")));
    }

    @Override
    public String toString() {
        return "Stock{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", dayTraded=" + dayTraded +
                ", price=" + price +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", changePercent=" + changePercent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return Double.compare(stock.getPrice(), getPrice()) == 0 && Double.compare(stock.getOpen(), getOpen()) == 0 && Double.compare(stock.getHigh(), getHigh()) == 0 && Double.compare(stock.getLow(), getLow()) == 0 && Double.compare(stock.getChangePercent(), getChangePercent()) == 0 && Objects.equals(getTickerSymbol(), stock.getTickerSymbol()) && Objects.equals(getDayTraded(), stock.getDayTraded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTickerSymbol(), getDayTraded(), getPrice(), getOpen(), getHigh(), getLow(), getChangePercent());
    }
}
