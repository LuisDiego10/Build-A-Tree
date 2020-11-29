package Challenge;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("type")
    public String type;
    @JsonProperty("value")
    public int value;
    @JsonProperty("player")
    public int player;
}
