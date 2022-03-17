package jp.btsol.mahjong.application.controller;

import org.mahjong4j.hands.Hands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.HandsService;

/**
 * Hands controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
public class HandsController {
    /**
     * hands service
     */
    private final HandsService handsService;

    /**
     * Constructor
     * 
     * @param handsService HandsService
     */
    public HandsController(HandsService handsService) {
        this.handsService = handsService;
    }

    /**
     * get Hands
     * 
     * @return Hands
     */
    @GetMapping("/hands")
    public Hands getHands() {
        return handsService.getHands();
    }
}
