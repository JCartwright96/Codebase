
package stocktrackerv2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Data class containing monthly information on specific stock
 * Contains raw json string values and a map of pojos with string date keys
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesWeekly extends TimeSeries{

    /**
     * Creates a new TimeSeriesWeekly instance
     * @param content the raw json content from the api response parsed into relevant data structures
     */
    public TimeSeriesWeekly(@JsonProperty("Weekly Time Series") final Map<String, Map<String, String>> content) {
        super(content);
    }

}
