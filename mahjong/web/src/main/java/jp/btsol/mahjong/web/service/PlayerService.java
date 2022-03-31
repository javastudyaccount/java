package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.PlayerRegistration;
import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Player service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class PlayerService {
    /**
     * Rest template
     */
    private final MahjongRestTemplate mahjongRestTemplate;
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param mahjongRestTemplate   MahjongRestTemplate
     */
    public PlayerService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
    }

    /**
     * get player list
     * 
     * @return List<Player>
     */
    public List<Player> getPlayers() {

        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getPlayers();

        List<Player> players = mahjongRestTemplate.get(url, ArrayList.class);

        return players;
    }

    /**
     * create new player
     * 
     * @param nickname String
     * @return Player
     */
    public Player createPlayer(String nickname, String password) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreatePlayer();
        PlayerRegistration playerRegistration = new PlayerRegistration();
        playerRegistration.setNickname(nickname);
        playerRegistration.setPassword(password);
        Player player = mahjongRestTemplate.post(url, playerRegistration, Player.class);

        return player;
    }
}
