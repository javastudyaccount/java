package jp.btsol.mahjong.mixin;

import org.mahjong4j.hands.Kantsu;
import org.mahjong4j.hands.Kotsu;
import org.mahjong4j.hands.Shuntsu;
import org.mahjong4j.hands.Toitsu;
import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "_type")
// CHECKSTYLE:OFF
@JsonSubTypes({//
        @JsonSubTypes.Type(value = Kantsu.class, name = "_type"), //
        @JsonSubTypes.Type(value = Kotsu.class, name = "_type"), //
        @JsonSubTypes.Type(value = Shuntsu.class, name = "_type"), //
        @JsonSubTypes.Type(value = Toitsu.class, name = "_type"),//
})
//CHECKSTYLE:ON
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