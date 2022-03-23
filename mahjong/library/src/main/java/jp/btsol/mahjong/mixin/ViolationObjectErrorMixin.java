package jp.btsol.mahjong.mixin;

import javax.validation.ConstraintViolation;

import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ViolationObjectErrorMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public ViolationObjectErrorMixin(@JsonProperty("objectName") String objectName, 
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
