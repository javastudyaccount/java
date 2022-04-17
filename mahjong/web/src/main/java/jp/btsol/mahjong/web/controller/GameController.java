package jp.btsol.mahjong.web.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.model.GameModel;
import jp.btsol.mahjong.web.service.GameService;
import lombok.extern.slf4j.Slf4j;

/**
 * Rooms controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
@Slf4j
public class GameController {
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
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * create game
     * 
     * @param roomId long
     * @return String template name
     */
    @PostMapping("/createGame")
    public String createGame(@RequestParam long roomId) {
        GameId game = gameService.createGame(roomId);
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(GameController.class, "game", Model.class, game.getGameId()).build();

        URI location = uriComponents.toUri();

        return "redirect:" + location.toString();
    }

    /**
     * show game
     * 
     * @param model  Model
     * @param gameId long
     * @return String view name
     */
    @GetMapping("/game/{gameId}")
    public String game(Model model, @Valid //
    @PathVariable("gameId") long gameId) {
        GameModel gameModel = gameService.getGame(gameId);
        model.addAttribute("game", gameModel);
        return "game/game";
    }
}
