package jp.btsol.mahjong.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class DefaultMessageSourceResolvableMixin {
    @JsonCreator
    public DefaultMessageSourceResolvableMixin(@JsonProperty("code") String code) {
    }
}
