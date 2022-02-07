package jp.btsol.mahjong.mixin;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ToitsuMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public ToitsuMixin(@JsonProperty("tile") Tile tile) {
    }

    @JsonIgnore
    int fu;
    @JsonIgnore
    boolean open;
}
