package jp.btsol.mahjong.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.PlayerService;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.PlayerRegistration;

/**
 * Player controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
@RequestMapping("/player")
public class PlayerController {
    /**
     * room service
     */
    private final PlayerService playerService;

    /**
     * Constructor
     * 
     * @param playerService PlayerService
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * get player list
     * 
     * @return List<Player>
     */
    @GetMapping("/all")
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }

    /**
     * create new player
     * 
     * @param playerRegistration String
     * @return Player
     */
    @PostMapping(value = "/new", produces = {"application/json"}, consumes = {"application/json"})
    public Player createNewPlayer(@Valid // validate annotation
    @RequestBody(required = true) PlayerRegistration playerRegistration) {
        return playerService.createNewPlayer(playerRegistration);
    }

}
