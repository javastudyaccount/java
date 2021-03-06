package jp.btsol.mahjong.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.model.GameModel;
import jp.btsol.mahjong.model.MahjongGameMessage;
import jp.btsol.mahjong.model.RoomId;
import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Room service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class GameService {
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
    public GameService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
    }

    /**
     * create game
     * 
     * @param roomId long
     * @return GameId
     */
    public GameId createGame(long roomId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreateGame();
        RoomId roomIdModel = new RoomId(roomId, 0L);
        GameId game = mahjongRestTemplate.post(url, roomIdModel, GameId.class);
        return game;
    }

    /**
     * enter game
     * 
     * @param gameId long
     */
    public void enterGame(long gameId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getEnterGame();
        GameId gameIdModel = new GameId(gameId);
        mahjongRestTemplate.post(url, gameIdModel);
    }

    /**
     * get game
     * 
     * @param gameId long
     * @return GameModel
     */
    public GameModel getGame(long gameId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getGame();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gameId", gameId);
        GameModel game = mahjongRestTemplate.get(url, param, GameModel.class);
        return game;
    }

    /**
     * ready to grab seat
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2GrabSeat(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getReady2GrabSeat();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }

    /**
     * grab seat
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage grabSeat(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getGrabSeat();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }

    /**
     * dice dealer
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage diceDealer(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getDiceDealer();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }

    /**
     * redice dealer
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage rediceDealer(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getRediceDealer();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }

    /**
     * ready to redice
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2redice(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getReady2Redice();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }

    /**
     * ready to redice waiting
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2rediceWaiting(MahjongGameMessage message) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getReady2RediceWaiting();
        MahjongGameMessage messageRet = mahjongRestTemplate.post(url, message, MahjongGameMessage.class);
        return messageRet;
    }
}
