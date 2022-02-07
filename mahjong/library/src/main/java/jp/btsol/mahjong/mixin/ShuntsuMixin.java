package jp.btsol.mahjong.mixin;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ShuntsuMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public ShuntsuMixin(@JsonProperty("isOpen") boolean isOpen, 
    		@JsonProperty("identifierTile") Tile identifierTile) {
    }

}
