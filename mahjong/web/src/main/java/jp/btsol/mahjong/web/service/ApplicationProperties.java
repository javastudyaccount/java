package jp.btsol.mahjong.web.service;

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

        /**
         * enter room
         */
        private String enterRoom;
        /**
         * players in room
         */
        private String playersInRoom;
        /**
         * players
         */
        private String players;
        /**
         * createPlayer
         */
        private String createPlayer;
        /**
         * player authentication
         */
        private String playerAuthentication;
        /**
         * new token
         */
        private String createToken;
        /**
         * update token
         */
        private String updateToken;
        /**
         * get token for series
         */
        private String tokenForSeries;
        /**
         * remove token for logid_id
         */
        private String removeToken;
    }

    /**
     * default sub
     */
    private String sub;
    /**
     * default iss
     */
    private String iss;
    /**
     * default loginId
     */
    private String loginId;
}