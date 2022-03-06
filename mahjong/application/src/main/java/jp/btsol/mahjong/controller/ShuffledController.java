package jp.btsol.mahjong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.service.ShuffledService;

/**
 * Shuffled controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
public class ShuffledController {
    /**
     * shuffling service
     */
    private final ShuffledService shuffledService;

    /**
     * Constructor
     * 
     * @param shuffledService ShuffledService
     */
    public ShuffledController(ShuffledService shuffledService) {
        this.shuffledService = shuffledService;
    }

    /**
     * get shuffled tiles
     * 
     * @return int[] shuffled tiles
     */
    @GetMapping("/shuffled")
    public int[] getShuffledTiles(@RequestBody(required = true) long gameId,
            @RequestHeader(value = "request-id", required = true) String requestId) {
        return shuffledService.shuffled(gameId, requestId);
    }
}
