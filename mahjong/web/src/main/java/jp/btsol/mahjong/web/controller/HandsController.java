package jp.btsol.mahjong.web.controller;

import org.mahjong4j.hands.Hands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.web.service.HandsService;

/**
 * Show tiles of a hand
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class HandsController {
    /**
     * Service to get tiles of a hand
     */
    private final HandsService handsService;

    /**
     * Constructor
     * 
     * @param handsService HandsService
     */
    @Autowired
    public HandsController(HandsService handsService) {
        this.handsService = handsService;
    }

    /**
     * show tiles of a hand
     * 
     * @param model Model
     * @return String hands template
     */
    @GetMapping("/hands")
    public String hands(Model model) {
        Hands hands = handsService.getHands();
        model.addAttribute("hands", hands);
        return "hands";
    }
}
