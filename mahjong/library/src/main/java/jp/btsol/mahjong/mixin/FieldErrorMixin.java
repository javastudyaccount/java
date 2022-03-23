package jp.btsol.mahjong.mixin;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class FieldErrorMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public FieldErrorMixin(@JsonProperty("objectName") String objectName, 
            @JsonProperty("field") String field,
            @JsonProperty("rejectedValue") @Nullable Object rejectedValue, 
            @JsonProperty("bindingFailure") boolean bindingFailure, 
            @JsonProperty("codes") String[] codes,
            @JsonProperty("arguments") @Nullable Object[] arguments, 
            @JsonProperty("defaultMessage") @Nullable String defaultMessage) {
    }
    //@formatter:on
    // CHECKSTYLE:ON
}
