package jp.btsol.mahjong.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.PlayerService;
import jp.btsol.mahjong.entity.PersistentLogins;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.InvitePlayerModel;
import jp.btsol.mahjong.model.PlayerAuthentication;
import jp.btsol.mahjong.model.PlayerModel;
import jp.btsol.mahjong.model.PlayerRegistration;
import lombok.extern.slf4j.Slf4j;

/**
 * Player controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
//@RequestMapping("/player")
@Slf4j
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
     * @return List<PlayerModel>
     */
    @GetMapping("/player/all")
    public List<PlayerModel> getPlayers() {
        return playerService.getPlayers();
    }

    /**
     * create player
     * 
     * @param playerRegistration PlayerRegistration
     * @return Player
     */
    @PostMapping(value = "/player/new", produces = {"application/json"}, consumes = {"application/json"})
    public Player createNewPlayer(@Valid // validate annotation
    @RequestBody(required = true) PlayerRegistration playerRegistration) {
        return playerService.createPlayer(playerRegistration);
    }

    /**
     * create token
     * 
     * @param persistentRememberMeToken PersistentRememberMeToken
     */
    @PostMapping(value = "/token/new", produces = {"application/json"}, consumes = {"application/json"})
    public void createNewToken(@Valid // validate annotation
    @RequestBody(required = true) PersistentRememberMeToken persistentRememberMeToken) {
        playerService.createToken(persistentRememberMeToken);
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
     * get token
     * 
     * @param series String
     * @return PersistentRememberMeToken
     */
    @GetMapping(value = "/token/get", produces = {"application/json"})
    public PersistentRememberMeToken getToken(@Valid // validate annotation
    @RequestParam(required = true) String series) {
        PersistentLogins persistentLogin = playerService.getToken(series);
        return new PersistentRememberMeToken(persistentLogin.getLoginId(), persistentLogin.getSeries(),
                persistentLogin.getToken(), persistentLogin.getLastUsed());
    }

    /**
     * delete token
     * 
     * @param loginId String
     */
    @DeleteMapping(value = "/token/delete", produces = {"application/json"})
    public void deleteToken(@Valid // validate annotation
    @RequestParam(required = true) String loginId) {
        playerService.deleteToken(loginId);
    }

    /**
     * player authentication
     * 
     * @param loginId LoginId
     * @return PlayerAuthentication
     */
    @GetMapping("/player/authentication")
    public PlayerAuthentication getPlayerAuthentication(@Valid //
    @RequestParam(required = true) String loginId) {
        return playerService.getPlayerAuthentication(loginId);
    }

    /**
     * invite players
     * 
     * @param InvitePlayerModel player IDs
     */
    @PostMapping("/player/invite")
    public void invite(@Valid //
    @RequestBody(required = true) InvitePlayerModel invitePlayerModel) {
        log.info("invite players {}", invitePlayerModel);
        playerService.invite(invitePlayerModel.getInvitePlayers());
    }
}
