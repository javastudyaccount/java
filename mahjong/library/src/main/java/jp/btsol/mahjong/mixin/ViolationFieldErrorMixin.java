package jp.btsol.mahjong.mixin;

import javax.validation.ConstraintViolation;

import org.springframework.lang.Nullable;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ViolationFieldErrorMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public ViolationFieldErrorMixin(@JsonProperty("objectName") String objectName, 
            @JsonProperty("field") String field,
            @JsonProperty("rejectedValue") @Nullable Object rejectedValue, 
            @JsonProperty("codes") String[] codes,
            @JsonProperty("arguments") Object[] arguments, 
            @JsonProperty("violation") ConstraintViolation<?> violation,
            @JsonProperty("adapter") SpringValidatorAdapter adapter) {
    }
    //@formatter:on
    @JsonProperty
    ConstraintViolation<?> violation;
    // CHECKSTYLE:ON
}
