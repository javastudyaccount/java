package jp.btsol.mahjong.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.btsol.mahjong.model.Message;
import jp.btsol.mahjong.web.service.HomeService;

/**
 * Home page controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class HomeController {
    /**
     * Service to get home message
     */
    @Autowired
    private HomeService homeService;

    /**
     * show home message
     * 
     * @param model Model
     * @return String home template
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Message> messages = homeService.getMessages();
        model.addAttribute("messages", messages);
        return "home";
    }

    /**
     * show home message
     * 
     * @param model Model
     * @return String home template
     */
    @PostMapping("/")
    public String postHome(Model model) {
        List<Message> messages = homeService.getMessages();
        model.addAttribute("messages", messages);
        return "home";
    }
}
