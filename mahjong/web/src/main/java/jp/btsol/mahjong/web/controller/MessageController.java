package jp.btsol.mahjong.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.entity.ErrorDataEntity;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.MahjongGameMessage;
import jp.btsol.mahjong.model.MahjongMessage;
import jp.btsol.mahjong.web.service.GameService;

@Controller
public class MessageController {
    /**
     * ObjectMapper
     */
    private final ObjectMapper om;
    /**
     * Service gameService
     */
    private final GameService gameService;
    /**
     * userContext UserContext
     */
    private final UserContext userContext;

    /**
     * Constructor
     * 
     * @param gameService GameService
     * @param om          ObjectMapper
     * @param userContext UserContext
     */
    @Autowired
    public MessageController(GameService gameService, ObjectMapper om, UserContext userContext) {
        this.gameService = gameService;
        this.om = om;
        this.userContext = userContext;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MahjongMessage greeting(MahjongMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return new MahjongMessage(HtmlUtils.htmlEscape(message.getName()), HtmlUtils.htmlEscape(message.getMessage()));
    }

    @MessageMapping("/grab/ready")
    @SendTo("/topic/game")
    public MahjongGameMessage ready2GrabSeat(MahjongGameMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return gameService.ready2GrabSeat(message);
    }

    @MessageMapping("/grab")
    @SendTo("/topic/game")
    public MahjongGameMessage grabSeat(MahjongGameMessage message, Principal principal) throws Exception {
//        Thread.sleep(1000); // simulated delay
        // do not use messgage.player_id, to prevent client attack
        userContext.userId(principal.getName()); // login_id
        return gameService.grabSeat(message);
    }

    @MessageMapping("/diceDealer")
    @SendTo("/topic/game")
    public MahjongGameMessage diceDealer(MahjongGameMessage message, Principal principal) throws Exception {
//        Thread.sleep(1000); // simulated delay
        // do not use messgage.player_id, to prevent client attack
        userContext.userId(principal.getName()); // login_id
        MahjongGameMessage msg = gameService.diceDealer(message);
        return msg;
    }

    @MessageMapping("/rediceDealer")
    @SendTo("/topic/game")
    public MahjongGameMessage rediceDealer(MahjongGameMessage message, Principal principal) throws Exception {
        // do not use messgage.player_id, to prevent client attack
        userContext.userId(principal.getName()); // login_id
        MahjongGameMessage msg = gameService.rediceDealer(message);
        return msg;
    }

    @MessageMapping("/ready2redice")
    @SendTo("/topic/game")
    public MahjongGameMessage ready2redice(MahjongGameMessage message, Principal principal) throws Exception {
        MahjongGameMessage msg = gameService.ready2redice(message);
        return msg;
    }

    @MessageMapping("/ready2rediceWaiting")
    @SendTo("/topic/game")
    public MahjongGameMessage ready2rediceWaiting(MahjongGameMessage message, Principal principal) throws Exception {
        MahjongGameMessage msg = gameService.ready2rediceWaiting(message);
        return msg;
    }

    @MessageExceptionHandler({HttpServerErrorException.class})
    @SendTo("/topic/game")
    public MahjongGameMessage handleError(HttpServerErrorException e) {
        MahjongGameMessage me = new MahjongGameMessage();
        try {
            ErrorDataEntity error = om.readValue(e.getResponseBodyAsString(), ErrorDataEntity.class);
            me.setGameStatus(error.getErrorDetail());
            me.setMessage("Error occurred.");
        } catch (JsonProcessingException ejson) {
            ejson.printStackTrace();
            me.setGameStatus("JsonProcessingException occurred.");
        }
        return me;
    }
}
