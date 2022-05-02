package jp.btsol.mahjong.application.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.GameService;
import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.model.GameModel;
import jp.btsol.mahjong.model.MahjongGameMessage;
import jp.btsol.mahjong.model.RoomId;

/**
 * Room controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
@RequestMapping("/game")
public class GameController {
    /**
     * game service
     */
    private final GameService gameService;

    /**
     * Constructor
     * 
     * @param gameService GameService
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * create game
     * 
     * @param roomId RoomId
     * @return GameId
     */
    @PostMapping(value = "/new")
    public ResponseEntity<GameId> newGame(@Valid //
    @RequestBody(required = true) RoomId roomId) {
        GameId game = gameService.createGame(roomId.getRoomId());
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    /**
     * enter game
     * 
     * @param gameId GameId
     */
    @PostMapping(value = "/enter")
    public void enterGame(@Valid //
    @RequestBody(required = true) GameId gameId) {
        gameService.enterGame(gameId.getGameId());
    }

    /**
     * get game
     * 
     * @return GameModel
     */
    @GetMapping("/get")
    public GameModel getGame(@Valid //
    @RequestParam(required = true) long gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * log
     * 
     * @return GameModel
     */
    @PostMapping("/log")
    public MahjongGameMessage logGame(@Valid //
    @RequestBody(required = true) MahjongGameMessage message) {
        return gameService.logGame(message);
    }
}
