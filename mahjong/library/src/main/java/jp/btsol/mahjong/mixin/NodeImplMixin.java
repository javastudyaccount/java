package jp.btsol.mahjong.mixin;

import javax.validation.ElementKind;

import org.hibernate.validator.internal.engine.path.NodeImpl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class NodeImplMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    //@formatter:off
    public NodeImplMixin(@JsonProperty("name") String name, 
            @JsonProperty("parent") NodeImpl parent, 
            @JsonProperty("isIterable") boolean isIterable, 
            @JsonProperty("index") Integer index, 
            @JsonProperty("key") Object key, 
            @JsonProperty("kind") ElementKind kind, 
            @JsonProperty("parameterTypes") Class<?>[] parameterTypes,
            @JsonProperty("parameterIndex") Integer parameterIndex, 
            @JsonProperty("value") Object value, 
            @JsonProperty("containerClass") Class<?> containerClass, 
            @JsonProperty("typeArgumentIndex") Integer typeArgumentIndex) {
    }
    //@formatter:on
    @JsonProperty("isIterable")
    boolean isIterable;
    @JsonProperty("inIterable")
    boolean inIterable;
    // CHECKSTYLE:ON
}
