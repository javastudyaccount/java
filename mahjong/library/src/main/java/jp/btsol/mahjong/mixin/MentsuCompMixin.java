package jp.btsol.mahjong.mixin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.mahjong4j.hands.Mentsu;
import org.mahjong4j.hands.MentsuComp;
import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.util.Annotations;

@JsonAppend(props = {@JsonAppend.Prop(value = MentsuCompMixin.MentsuCompWriter.class, //
        name = "mentsuList", //
        type = ArrayList.class)})
public abstract class MentsuCompMixin {
    // CHECKSTYLE:OFF
    @JsonCreator
    // @formatter:off
	public MentsuCompMixin(@JsonProperty("mentsuList") List<Mentsu> mentsuList, 
			@JsonProperty("last") Tile last) {
	}

	public static class MentsuCompWriter extends VirtualBeanPropertyWriter {
		/**
         * serialVersionUID
         */
        private static final long serialVersionUID = -3037187443109861496L;
        /**
         * Constructor
         */
        @SuppressWarnings("unused")
        public MentsuCompWriter() {
            
        }
        public MentsuCompWriter(BeanPropertyDefinition propDef, Annotations contextAnnotations, JavaType declaredType) {
			super(propDef, contextAnnotations, declaredType);
		}

		@Override
		protected Object value(Object bean, JsonGenerator gen, SerializerProvider prov) {
			MentsuComp mentsuComp = (MentsuComp) bean;
			return mentsuComp.getAllMentsu();
		}

	    @Override
	    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception
	    {
	        // NOTE: mostly copied from base class, but off-lined get() access
	        final Object value = value(bean, gen, prov);

	        if (value == null) {
	            if (_nullSerializer != null) {
	                gen.writeFieldName(_name);
	                _nullSerializer.serialize(null, gen, prov);
	            }
	            return;
	        }
	        JsonSerializer<Object> ser = _serializer;
	        if (ser == null) {
	            Class<?> cls = value.getClass();
	            PropertySerializerMap m = _dynamicSerializers;
	            ser = m.serializerFor(cls);
	            if (ser == null) {
	                ser = _findAndAddDynamic(m, cls, prov);
	            }
	        }
	        if (_suppressableValue != null) {
	            if (MARKER_FOR_EMPTY == _suppressableValue) {
	                if (ser.isEmpty(prov, value)) {
	                    return;
	                }
	            } else if (_suppressableValue.equals(value)) {
	                return;
	            }
	        }
	        if (value == bean) { // simple check for direct cycles
	            // four choices: exception; handled by call; or pass-through; write null
	            if (_handleSelfReference(bean, gen, prov, ser)) {
	                return;
	            }
	        }
	        gen.writeFieldName(_name);
	        WritableTypeId typeIdDef = new WritableTypeId(value, JsonToken.START_ARRAY);
	        typeIdDef.include = WritableTypeId.Inclusion.METADATA_PROPERTY;
            typeIdDef.asProperty = "@class";
            typeIdDef.id = "java.util.ArrayList";
            gen.writeTypePrefix(typeIdDef);
            
            gen.setCurrentValue(value);
            
            Method serializeContents = ser.getClass().getDeclaredMethod("serializeContents", Object.class, JsonGenerator.class, SerializerProvider.class);
            serializeContents.setAccessible(true);
            serializeContents.invoke(ser, value, gen, prov);
//            ((IndexedListSerializer)ser).serializeContents(value, gen, prov);
//	        if (_typeSerializer == null) {
//	            ser.serialize(value, gen, prov);
//	        } else {
//	            ser.serializeWithType(value, gen, prov, _typeSerializer);
//	        }
	        gen.writeTypeSuffix(typeIdDef);
	    }
		@Override
		public VirtualBeanPropertyWriter withConfig(MapperConfig<?> config, AnnotatedClass declaringClass,
				BeanPropertyDefinition propDef, JavaType type) {
		    VirtualBeanPropertyWriter writer = new MentsuCompWriter(propDef, declaringClass.getAnnotations(), type);
		    return writer;
		}
	}
}