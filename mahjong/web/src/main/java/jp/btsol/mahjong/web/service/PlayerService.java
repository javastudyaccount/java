package jp.btsol.mahjong.web.service;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.emory.mathcs.backport.java.util.Arrays;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.Nickname;
import jp.btsol.mahjong.web.fw.MahjongRestErrorHandler;
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
     * Object mapper
     */
    private final ObjectMapper objectMapper;
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param objectMapper          ObjectMapper object mapper
     * @param mahjongRestTemplate   MahjongRestTemplate
     */
    public PlayerService(ApplicationProperties applicationProperties, //
            ObjectMapper objectMapper, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
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

        Player[] players = mahjongRestTemplate.get(url, Player[].class, new MahjongRestErrorHandler() {

            @Override
            public void handle(int statusCode, HttpStatusCodeException e) throws RuntimeException {
                log.error(e.getLocalizedMessage());
            }

        });

        List<Player> playersList = Arrays.asList(players);
        return playersList;
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
