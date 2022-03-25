package jp.btsol.mahjong.mixin;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
// CHECKSTYLE:OFF
//@JsonSubTypes({//
//        @JsonSubTypes.Type(value = Kantsu.class, name = "@class"), //
//        @JsonSubTypes.Type(value = Kotsu.class, name = "@class"), //
//        @JsonSubTypes.Type(value = Shuntsu.class, name = "@class"), //
//        @JsonSubTypes.Type(value = Toitsu.class, name = "@class"),//
//})
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