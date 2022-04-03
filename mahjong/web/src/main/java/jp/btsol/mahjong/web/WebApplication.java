package jp.btsol.mahjong.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

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
}
