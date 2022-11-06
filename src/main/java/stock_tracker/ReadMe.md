**API KEY:** OSKXT3K0OSQ9UVZO

**API CALL Example:**
https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=GME&interval=1min&apikey=OSKXT3K0OSQ9UVZO

First Iteration of a small  globalQuoteStock tracker app using the AlphaVantage api (prices in $ as returned by the api)

Example of returned values for each FUNCTION.TYPE

---------------------------
TIME_SERIES_DAILY - Closing price by day // System.out.println(new ApiMananger(FunctionType.TIME_SERIES_DAILY).get("GME"));

[{high=179.2400, low=170.3300, dayTraded=2021-05-21 , price=176.7900, open=171.0000}, {high=174.9100, low=166.9000, dayTraded=2021-05-20 , price=170.4900, open=170.7900}, {high=178.9799, low=164.1500, dayTraded=2021-05-19 , price=168.8300, open=171.9900}...

--------------------------
TIME_SERIES_INTRADAY - Prices for the current day in 5 minute intervals // System.out.println(new ApiMananger(FunctionType.TIME_SERIES_INTRADAY).get("GME"));

[{high=175.3400, low=175.2601, dayTraded=2021-05-21 20:00:00, price=175.2601, open=175.3000}, {high=175.2900, low=175.2900, dayTraded=2021-05-21 19:55:00, price=175.2900, open=175.2900}...

--------------------------
TIME_SERIES_MONTHLY -  prices for closing per month // System.out.println(new ApiMananger(FunctionType.TIME_SERIES_MONTHLY).get("GME"));

[{high=189.2000, low=136.5000, dayTraded=2021-05-21 , price=176.7900, open=177.4900}, {high=196.9690, low=132.0000, dayTraded=2021-04-30 , price=173.5900, open=193.3600}...

--------------------------
GLOBAL QUOTE - Last quoted price at closing of the most recent day // System.out.println(new ApiMananger(FunctionType.GLOBAL_QUOTE).get("GME"));

[{high=179.2400, low=170.3300, dayTraded=2021-05-24, price=170.4900, changePercent=3.6952, open=171.0000}]
