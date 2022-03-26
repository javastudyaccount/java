package jp.btsol.mahjong.mixin;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BeanPropertyBindingResultMixin {
    @JsonCreator
    public BeanPropertyBindingResultMixin(@JsonProperty("target") @Nullable Object target,
            @JsonProperty("objectName") String objectName) {
    }
}
