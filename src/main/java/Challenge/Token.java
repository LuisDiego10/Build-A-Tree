package Challenge;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("tree_type")
    public String type;
    @JsonProperty("int_value")
    public int value;
    @JsonProperty("player")
    public int player;
}
