package jp.btsol.mahjong.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.web.fw.AESPasswordEncoder;
import jp.btsol.mahjong.web.fw.ShiftPasswordEncoder;

/**
 * WEB application for Mahjong
 * 
 * @author B&T Solutions Inc.
 *
 */
@SpringBootApplication(scanBasePackages = "jp.btsol.mahjong")
public class WebApplication {
    /**
     * objectMapper ObjectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * main(entry point)
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /**
     * mappingJackson2HttpMessageConverter
     * 
     * @return MappingJackson2HttpMessageConverter
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
        bean.setObjectMapper(objectMapper);
        return bean;
    }

    /**
     * API通信部品の定義
     *
     * @return 通信部品
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, mappingJackson2HttpMessageConverter());
        return template;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "shift";
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
