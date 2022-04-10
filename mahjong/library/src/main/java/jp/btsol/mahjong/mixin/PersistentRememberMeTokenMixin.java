package jp.btsol.mahjong.mixin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PersistentRememberMeTokenMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public PersistentRememberMeTokenMixin(@JsonProperty("username") String username, 
            @JsonProperty("series") String series, 
            @JsonProperty("tokenValue") String tokenValue,
            @JsonProperty("date") Date date) {
    }
}
