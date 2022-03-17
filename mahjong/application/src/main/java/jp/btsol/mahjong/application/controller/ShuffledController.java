package jp.btsol.mahjong.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.ShuffledService;
import jp.btsol.mahjong.model.GameId;

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
     * @param gameId long
     * @return int[] shuffled tiles
     */
    @PostMapping("/shuffled")
    public int[] getShuffledTiles(@RequestBody(required = true) GameId gameId) {
        return shuffledService.shuffled(gameId.getGameId());
    }
}
