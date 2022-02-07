package jp.btsol.mahjong.mixin;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MentsuMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public MentsuMixin(@JsonProperty("mentsu") boolean isMentsu, 
    		@JsonProperty("open") boolean isOpen,
    		@JsonProperty("identifierTile") Tile identifierTile
    		) {
    }
    @JsonIgnore
    int fu;

    @JsonIgnore
    Tile tile;
}