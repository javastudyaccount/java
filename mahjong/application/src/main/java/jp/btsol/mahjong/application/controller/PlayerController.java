package jp.btsol.mahjong.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.PlayerService;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.PlayerAuthentication;
import jp.btsol.mahjong.model.PlayerRegistration;

/**
 * Player controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
//@RequestMapping("/player")
public class PlayerController {
    /**
     * Player service
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
    @GetMapping("/player/all")
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }

    /**
     * create new player
     * 
     * @param playerRegistration PlayerRegistration
     * @return Player
     */
    @PostMapping(value = "/player/new", produces = {"application/json"}, consumes = {"application/json"})
    public Player createNewPlayer(@Valid // validate annotation
    @RequestBody(required = true) PlayerRegistration playerRegistration) {
        return playerService.createNewPlayer(playerRegistration);
    }

    /**
     * create new token
     * 
     * @param persistentRememberMeToken PersistentRememberMeToken
     */
    @PostMapping(value = "/token/new", produces = {"application/json"}, consumes = {"application/json"})
    public void createNewToken(@Valid // validate annotation
    @RequestBody(required = true) PersistentRememberMeToken persistentRememberMeToken) {
        playerService.createNewToken(persistentRememberMeToken);
    }

    /**
     * update token
     * 
     * @param persistentRememberMeToken PersistentRememberMeToken
     */
    @PutMapping(value = "/token/update", produces = {"application/json"}, consumes = {"application/json"})
    public void updateToken(@Valid // validate annotation
    @RequestBody(required = true) PersistentRememberMeToken persistentRememberMeToken) {
        playerService.updateToken(persistentRememberMeToken);
    }

    /**
     * player authentication
     * 
     * @param loginId LoginId
     * @return PlayerAuthentication
     */
    @GetMapping("/player/authentication")
    public PlayerAuthentication getPlayerAuthentication(@Valid @RequestParam(required = true) String loginId) {
        return playerService.getPlayerAuthentication(loginId);
    }
}
