package jp.btsol.mahjong.config;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.mahjong4j.hands.Hands;
import org.mahjong4j.hands.Kantsu;
import org.mahjong4j.hands.Kotsu;
import org.mahjong4j.hands.Mentsu;
import org.mahjong4j.hands.MentsuComp;
import org.mahjong4j.hands.Shuntsu;
import org.mahjong4j.hands.Toitsu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.btsol.mahjong.mixin.BeanPropertyBindingResultMixin;
import jp.btsol.mahjong.mixin.ConstraintViolationImplMixin;
import jp.btsol.mahjong.mixin.DefaultMessageSourceResolvableMixin;
import jp.btsol.mahjong.mixin.FieldErrorMixin;
import jp.btsol.mahjong.mixin.HandsMixin;
import jp.btsol.mahjong.mixin.KantsuMixin;
import jp.btsol.mahjong.mixin.KotsuMixin;
import jp.btsol.mahjong.mixin.MentsuCompMixin;
import jp.btsol.mahjong.mixin.MentsuMixin;
import jp.btsol.mahjong.mixin.NodeImplMixin;
import jp.btsol.mahjong.mixin.ResolvableAttributeMixin;
import jp.btsol.mahjong.mixin.ShuntsuMixin;
import jp.btsol.mahjong.mixin.ToitsuMixin;
import jp.btsol.mahjong.mixin.ViolationFieldErrorMixin;
import jp.btsol.mahjong.mixin.ViolationObjectErrorMixin;

/**
 * Config bean
 * 
 * @author B&T Solutions Inc.
 *
 */
@Configuration
public class MahjongConfigBean {
    /**
     * DATETIME_FORMAT
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    /**
     * LOCAL_DATETIME_SERIALIZER
     */
    private static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(
            DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    /**
     * ObjectMapper
     * 
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        config(om);
        return om;
    }

    /**
     * ObjectMapperを設定する
     * 
     * @param om ObjectMapper
     */
    public void config(ObjectMapper om) {
//        om.configure(SerializationFeature.WRAP_ROOT_VALUE, true); //add {} as root
//        SecurityJackson2Modules.enableDefaultTyping(om);
        om.setDefaultTyping(createAllowlistedDefaultTyping()); // add type @class

        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // used
//        om.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        // Pretty Printにする
        om.enable(SerializationFeature.INDENT_OUTPUT); // used
        om.addMixIn(Hands.class, HandsMixin.class);
        om.addMixIn(MentsuComp.class, MentsuCompMixin.class);
        om.addMixIn(Toitsu.class, ToitsuMixin.class);
        om.addMixIn(Mentsu.class, MentsuMixin.class);
        om.addMixIn(Shuntsu.class, ShuntsuMixin.class);
        om.addMixIn(Kotsu.class, KotsuMixin.class);
        om.addMixIn(Kantsu.class, KantsuMixin.class);
        om.addMixIn(BeanPropertyBindingResult.class, BeanPropertyBindingResultMixin.class);
        try {
            om.addMixIn(
                    Class.forName(
                            "org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationFieldError"),
                    ViolationFieldErrorMixin.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            om.addMixIn(Class.forName(
                    "org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationObjectError"),
                    ViolationObjectErrorMixin.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            om.addMixIn(
                    Class.forName(
                            "org.springframework.validation.beanvalidation.SpringValidatorAdapter$ResolvableAttribute"),
                    ResolvableAttributeMixin.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        om.addMixIn(DefaultMessageSourceResolvable.class, DefaultMessageSourceResolvableMixin.class);
        om.addMixIn(ConstraintViolationImpl.class, ConstraintViolationImplMixin.class);
        om.addMixIn(NodeImpl.class, NodeImplMixin.class);
        om.addMixIn(FieldError.class, FieldErrorMixin.class);

        om.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.ALL,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE);
        om.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.FIELD,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
    }

    public static TypeResolverBuilder<? extends TypeResolverBuilder> createAllowlistedDefaultTyping() {
        TypeResolverBuilder<? extends TypeResolverBuilder> result = new AllowlistTypeResolverBuilder(
                ObjectMapper.DefaultTyping.NON_FINAL); // EVERYTHING,NON_FINAL
        result = result.init(JsonTypeInfo.Id.CLASS, null);
        result = result.inclusion(JsonTypeInfo.As.PROPERTY); // PROPERTY,WRAPPER_OBJECT,WRAPPER_ARRAY,EXTERNAL_PROPERTY,EXISTING_PROPERTY
        return result;
    }

    public static class AllowlistTypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {

        public AllowlistTypeResolverBuilder(ObjectMapper.DefaultTyping defaultTyping) {
            super(defaultTyping,
                    // we do explicit validation in the TypeIdResolver
                    BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build());
        }

        @Override
        public TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType,
                PolymorphicTypeValidator subtypeValidator, Collection<NamedType> subtypes, boolean forSer,
                boolean forDeser) {
            TypeIdResolver result = super.idResolver(config, baseType, subtypeValidator, subtypes, forSer, forDeser);
            return new AllowlistTypeIdResolver(result);
        }

    }

    static class AllowlistTypeIdResolver implements TypeIdResolver {

        /** パッケージ名 */
        private static final String JP_BTSOL_MAHJONG = "jp.btsol.mahjong";
        /** パッケージ名 */
        private static final String ORG_MAHJONG4J = "org.mahjong4j";
        /** ALLOWLIST_CLASS_NAMES */
        private static final Set<String> ALLOWLIST_CLASS_NAMES;
        static {
            Set<String> names = new HashSet<>();
            names.add("java.util.ArrayList");
            names.add("java.util.Collections$EmptyList");
            names.add("java.util.Collections$EmptyMap");
            names.add("java.util.Collections$UnmodifiableRandomAccessList");
            names.add("java.util.Collections$SingletonList");
            names.add("java.util.Date");
            names.add("java.time.Instant");
            names.add("java.net.URL");
            names.add("java.util.TreeMap");
            names.add("java.util.HashMap");
            names.add("java.util.LinkedHashMap");
            names.add("org.springframework.security.core.context.SecurityContextImpl");
            names.add("java.util.Locale");
            names.add("java.lang.Long");
            names.add("java.util.concurrent.CopyOnWriteArrayList");
            names.add("org.springframework.web.servlet.FlashMap");
            names.add("java.util.ArrayDeque");
            names.add("org.springframework.validation.DefaultMessageCodesResolver");
            names.add("org.springframework.validation.DefaultMessageCodesResolver$Format");
            names.add("[Ljava.lang.Object;");
            names.add("org.hibernate.validator.internal.engine.path.PathImpl");
            names.add("java.util.HashSet");
            names.add("[Ljava.lang.String;");
            names.add("com.github.pagehelper.PageInfo");
            names.add("java.math.BigDecimal");
            names.add("java.sql.Timestamp");
            ALLOWLIST_CLASS_NAMES = Collections.unmodifiableSet(names);
        }

        /** delegate */
        private final TypeIdResolver delegate;

        AllowlistTypeIdResolver(TypeIdResolver delegate) {
            this.delegate = delegate;
        }

        @Override
        public void init(JavaType baseType) {
            this.delegate.init(baseType);
        }

        @Override
        public String idFromValue(Object value) {
            return this.delegate.idFromValue(value);
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return this.delegate.idFromValueAndType(value, suggestedType);
        }

        @Override
        public String idFromBaseType() {
            return this.delegate.idFromBaseType();
        }

        @Override
        public JavaType typeFromId(DatabindContext context, String id) throws IOException {
            DeserializationConfig config = (DeserializationConfig) context.getConfig();
            JavaType result = this.delegate.typeFromId(context, id);
            String className = result.getRawClass().getName();
            if (className.startsWith(JP_BTSOL_MAHJONG)) {
                return result;
            }
            if (className.startsWith(ORG_MAHJONG4J)) {
                return result;
            }
            if (isInAllowlist(className)) {
                return result;
            }
            boolean isExplicitMixin = config.findMixInClassFor(result.getRawClass()) != null;
            if (isExplicitMixin) {
                return result;
            }
            JacksonAnnotation jacksonAnnotation = AnnotationUtils.findAnnotation(result.getRawClass(),
                    JacksonAnnotation.class);
            if (jacksonAnnotation != null) {
                return result;
            }
            throw new IllegalArgumentException("The class with " + id + " and name of " + className
                    + " is not in the allowlist. " + "If you believe this class is safe to deserialize, "
                    + "please provide an explicit mapping using Jackson annotations or by providing a Mixin. "
                    + "If the serialization is only done by a trusted source, you can also enable default typing. "
                    + "See https://github.com/spring-projects/spring-security/issues/4370 for details");
        }

        private boolean isInAllowlist(String id) {
            return ALLOWLIST_CLASS_NAMES.contains(id);
        }

        @Override
        public String getDescForKnownTypeIds() {
            return this.delegate.getDescForKnownTypeIds();
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return this.delegate.getMechanism();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("AES", new AESPasswordEncoder());
        encoders.put("shift", ShiftPasswordEncoder.getInstance());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return passwordEncoder;
    }
}