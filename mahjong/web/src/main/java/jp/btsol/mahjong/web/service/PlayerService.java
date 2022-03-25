package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.Nickname;
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

//        Player[] players1 = mahjongRestTemplate.get(url, Player[].class, new MahjongRestErrorHandler() {
//
//            @Override
//            public void handle(int statusCode, HttpStatusCodeException e) throws RuntimeException {
//                log.error(e.getLocalizedMessage());
//            }
//
//        });

        List<Player> players = mahjongRestTemplate.get(url, ArrayList.class);

        return players;
    }

    /**
     * create new player
     * 
     * @param nickname String
     * @return Player
     */
    public Player createPlayer(String nickname) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreatePlayer();
        Nickname nicknameModel = new Nickname();
        nicknameModel.setNickname(nickname);
        Player player = mahjongRestTemplate.post(url, nicknameModel, Player.class);

        return player;
    }
}
