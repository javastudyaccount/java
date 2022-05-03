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
         * room
         */
        private String room;

        /**
         * enter room
         */
        private String enterRoom;

        /**
         * exit room
         */
        private String exitRoom;
        /**
         * create room
         */
        private String createRoom;
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
        /**
         * invite players
         */
        private String invitePlayers;

        /**
         * invites for player
         */
        private String invites4Player;
        /**
         * invited
         */
        private String invited;

        /**
         * create game
         */
        private String createGame;
        /**
         * get game
         */
        private String game;
        /**
         * enter game
         */
        private String enterGame;
        /**
         * ready2GrabSeat
         */
        private String ready2GrabSeat;
        /**
         * grabSeat
         */
        private String grabSeat;
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