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
     * path
     */
    private Path path;

    @Data
    public static class Path {
        /**
         * path home
         */
        private String home;
        /**
         * path hands
         */
        private String hands;

        /**
         * path shuffled
         */
        private String shuffled;
        /**
         * rooms
         */
        private String rooms;
    }
}