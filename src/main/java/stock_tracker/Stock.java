/*
 * Name     :   Stock.java
 * Author   :   joeca
 * Date     :   12 Mar 2021
 */
package stock_tracker;

/**
 * A class to track the information of a specific stock
 */
public class Stock {

    /**
     * The name of the stock
     */
    private String stockName;
    /**
     * The abbreviated name of the stock, usually in the format of £STO or $STO for uk and us stock
     */
    private String tickerSymbol;
    private double sharesOwned;
    private double sharePrice;
}
