
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
 * Data class containing monthly information on specific stock
 * Contains raw json string values and a map of pojos with string date keys
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesDaily extends TimeSeries {

    /**
     * Creates a new TimeSeriesDaily instance
     * @param content the raw json content from the api response parsed into relevant data structures
     */
    public TimeSeriesDaily(@JsonProperty("Time Series (Daily)") final Map<String, Map<String, String>> content) {
        super(content);
    }

}
