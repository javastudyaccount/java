package jp.btsol.mahjong.mixin;

import java.util.ArrayList;
import java.util.List;

import org.mahjong4j.hands.Mentsu;
import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Annotations;
@JsonAppend(props = { @JsonAppend.Prop(value = MentsuCompMixin.MentsuCompWriter.class, 
name = "mentsuList", 
type = List.class) })
public abstract class MentsuCompMixin {
	// CHECKSTYLE:OFF
	@JsonCreator
	// @formatter:off
	public MentsuCompMixin(@JsonProperty("mentsuList") List<Mentsu> mentsuList, @JsonProperty("last") Tile last) {
	}

	private static class MentsuCompWriter extends VirtualBeanPropertyWriter {
		public MentsuCompWriter() {
		}

		public MentsuCompWriter(BeanPropertyDefinition propDef, Annotations contextAnnotations, JavaType declaredType) {
			super(propDef, contextAnnotations, declaredType);
		}

		@Override
		protected Object value(Object bean, JsonGenerator gen, SerializerProvider prov) {
//			MentsuComp mentsuComp = (MentsuComp) bean;
//			return mentsuComp.getAllMentsu();
			return new ArrayList<Mentsu>() {};
		}

		@Override
		public VirtualBeanPropertyWriter withConfig(MapperConfig<?> config, AnnotatedClass declaringClass,
				BeanPropertyDefinition propDef, JavaType type) {
			return new MentsuCompWriter(propDef, declaringClass.getAnnotations(), type);
		}
	}
}