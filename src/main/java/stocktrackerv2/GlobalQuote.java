package stocktrackerv2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties
public class GlobalQuote {

    private Map<String, String> content;

    public GlobalQuote(@JsonProperty("Global Quote") final Map<String, String> content) {
        this.content = content;
    }

    public Map<String, String> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "GlobalQuote{" +
                "content=" + content +
                '}';
    }
}
