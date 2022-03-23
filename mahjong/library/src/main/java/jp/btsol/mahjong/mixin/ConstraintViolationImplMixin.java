package jp.btsol.mahjong.mixin;

import java.util.Map;

import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ConstraintViolationImplMixin<T> {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public ConstraintViolationImplMixin(@JsonProperty("messageTemplate") String messageTemplate, 
            @JsonProperty("messageParameters") Map<String, Object> messageParameters,
            @JsonProperty("expressionVariables") Map<String, Object> expressionVariables, 
            @JsonProperty("interpolatedMessage") String interpolatedMessage, 
            @JsonProperty("rootBeanClass") Class<T> rootBeanClass, 
            @JsonProperty("rootBean") T rootBean,
            @JsonProperty("leafBeanInstance") Object leafBeanInstance, 
            @JsonProperty("value") Object value, 
            @JsonProperty("propertyPath") Path propertyPath, 
            @JsonProperty("constraintDescriptor") ConstraintDescriptor<?> constraintDescriptor,
            @JsonProperty("executableParameters") Object[] executableParameters, 
            @JsonProperty("executableReturnValue") Object executableReturnValue, 
            @JsonProperty("dynamicPayload") Object dynamicPayload) {
    }
    //@formatter:on
    @JsonIgnore
    ConstraintDescriptor<?> constraintDescriptor;
    // CHECKSTYLE:ON

}
