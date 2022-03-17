package jp.btsol.mahjong.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.fw.exception.BadRequestException;
import jp.btsol.mahjong.application.service.PlayerService;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.Nickname;
import jp.btsol.mahjong.utils.validator.Validator;

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
     * @param nickname String
     * @return Player
     */
    @PostMapping(value = "/new")
    public Player createNewPlayer(@RequestBody(required = true) Nickname nickname) {
        int maxNameLen = Validator.getMaxLength(new Player(), "nickname");
        if (maxNameLen > 0 && nickname.getNickname().length() > maxNameLen) {
            throw new BadRequestException("nickname is more than " + maxNameLen + ".");
        }
        return playerService.createNewPlayer(nickname.getNickname());
    }

}
