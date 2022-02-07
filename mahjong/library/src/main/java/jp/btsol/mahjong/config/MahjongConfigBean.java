package jp.btsol.mahjong.config;

import org.mahjong4j.hands.Hands;
import org.mahjong4j.hands.Kantsu;
import org.mahjong4j.hands.Kotsu;
import org.mahjong4j.hands.Mentsu;
import org.mahjong4j.hands.MentsuComp;
import org.mahjong4j.hands.Shuntsu;
import org.mahjong4j.hands.Toitsu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jp.btsol.mahjong.mixin.HandsMixin;
import jp.btsol.mahjong.mixin.KantsuMixin;
import jp.btsol.mahjong.mixin.KotsuMixin;
import jp.btsol.mahjong.mixin.MentsuCompMixin;
import jp.btsol.mahjong.mixin.MentsuMixin;
import jp.btsol.mahjong.mixin.ShuntsuMixin;
import jp.btsol.mahjong.mixin.ToitsuMixin;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MahjongConfigBean {

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
    private void config(ObjectMapper om) {
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        om.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        om.addMixIn(Hands.class, HandsMixin.class);
        om.addMixIn(MentsuComp.class, MentsuCompMixin.class);
        om.addMixIn(Toitsu.class, ToitsuMixin.class);
        om.addMixIn(Mentsu.class, MentsuMixin.class);
        om.addMixIn(Shuntsu.class, ShuntsuMixin.class);
        om.addMixIn(Kotsu.class, KotsuMixin.class);
        om.addMixIn(Kantsu.class, KantsuMixin.class);

        om.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.ALL,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE);
        om.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.FIELD,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
    }
}