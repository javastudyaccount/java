package jp.btsol.mahjong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * WEB application for Mahjong
 * 
 * @author B&T Solutions Inc.
 *
 */
@SpringBootApplication
public class WebApplication {
    /**
     * main(entry point)
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    /**
     * API通信部品の定義
     *
     * @return 通信部品
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
