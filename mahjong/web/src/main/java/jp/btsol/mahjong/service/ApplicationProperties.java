package jp.btsol.mahjong.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("application")
@Data
public class ApplicationProperties {

    /**
     * A url for the application.
     */
    private String uri;
    /**
     * path hands
     */
    private String hands;
}