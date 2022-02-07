package jp.btsol.mahjong.mixin;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class KotsuMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public KotsuMixin(@JsonProperty("isOpen") boolean isOpen, 
    		@JsonProperty("identifierTile") Tile identifierTile) {
    }

}
