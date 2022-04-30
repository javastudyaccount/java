package jp.btsol.mahjong.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import jp.btsol.mahjong.model.MahjongMessage;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public MahjongMessage greeting(MahjongMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new MahjongMessage(HtmlUtils.htmlEscape(message.getName()), HtmlUtils.htmlEscape(message.getMessage()));
    }
}
