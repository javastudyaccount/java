package jp.btsol.mahjong.mixin;

import java.util.List;

import org.mahjong4j.hands.Mentsu;
import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class HandsMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public HandsMixin(@JsonProperty("inputtedTiles") int[] inputtedTiles, 
            @JsonProperty("last") Tile last, 
            @JsonProperty("inputtedMentsuList") List<Mentsu> inputtedMentsuList) {
    }
}
