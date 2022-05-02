package jp.btsol.mahjong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import jp.btsol.mahjong.model.MahjongGameMessage;
import jp.btsol.mahjong.model.MahjongMessage;
import jp.btsol.mahjong.web.service.GameService;

@Controller
public class MessageController {
    /**
     * Service gameService
     */
    private final GameService gameService;

    /**
     * Constructor
     * 
     * @param gameService GameService
     */
    @Autowired
    public MessageController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MahjongMessage greeting(MahjongMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return new MahjongMessage(HtmlUtils.htmlEscape(message.getName()), HtmlUtils.htmlEscape(message.getMessage()));
    }

    @MessageMapping("/grab/begin")
    @SendTo("/topic/game")
    public MahjongGameMessage game(MahjongGameMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return gameService.log(message);
    }
}
