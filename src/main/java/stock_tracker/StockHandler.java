/*
 * Name     :   StockHandler.java
 * Author   :   joeca
 * Date     :   15 Mar 2021
 */
package stock_tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Class to handle the creation of Stock objects through Api calls from the ApiManager
 */
public class StockHandler {

    private final ApiManager apiManager;
    private final ArrayList<Stock> stockList;

    public StockHandler() {
        this.apiManager = new ApiManager();
        this.stockList = new ArrayList<>();
    }

    public Optional<Stock> getStock(String tickerSymbol) throws IOException {
        Stock stock = new Stock();
        ArrayList<Map<String, String>> stockList = apiManager.get(tickerSymbol);
        return Optional.empty();
    }


    public ArrayList<Stock> getStockList() {
        return stockList;
    }


    // Monthly
//            "2021-03-12": {
//                 "1. open": "104.5400",
//                "2. high": "348.5000",
//                "3. low": "99.9700",
//                "4. close": "264.5000",
//                "5. volume": "394585366"
//    },

    // INTRADAY - 5mins
//            "2021-03-12 20:00:00": {
//                "1. open": "266.9900",
//                "2. high": "267.7900",
//                "3. low": "266.7500",
//                "4. close": "267.7700",
//                "5. volume": "20114"
//    },

    // Global Quote
// 1    {
// 2      "Global Quote": {
// 3               "01. symbol": "GME",
// 4               "02. open": "274.9965",
// 5               "03. high": "295.5000",
// 6               "04. low": "262.2700",
// 7               "05. price": "264.5000",
// 8               "06. volume": "25845899",
// 9               "07. latest trading day": "2021-03-12",
// 10              "08. previous close": "260.0000",
// 11              "09. change": "4.5000",
// 12              "10. change percent": "1.7308%"
// 13   }
// 14   }

}
