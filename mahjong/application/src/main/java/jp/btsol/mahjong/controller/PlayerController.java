package jp.btsol.mahjong.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.entity.Player;

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
     * get player list
     * 
     * @return List<Player>
     */
    @GetMapping("/all")
    public List<Player> getPlayers() {
        return null;
    }

    /**
     * create new player
     * 
     * @param playerName String
     * @return Player
     */
    @PostMapping(value = "/new")
    public Player createNewPlayer(@RequestBody(required = true) String playerName) {
        return null;
    }

}
