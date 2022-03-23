package jp.btsol.mahjong.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ResolvableAttributeMixin {
    @JsonCreator
    public ResolvableAttributeMixin(@JsonProperty("resolvableString") String resolvableString) {
    }
}
