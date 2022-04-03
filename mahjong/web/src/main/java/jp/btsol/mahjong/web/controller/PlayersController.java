package jp.btsol.mahjong.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.PlayerRegistration;
import jp.btsol.mahjong.web.form.LoginForm;
import jp.btsol.mahjong.web.form.PlayerForm;
import jp.btsol.mahjong.web.service.PlayerService;

/**
 * Players controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class PlayersController {
    /**
     * Service playerService
     */
    private final PlayerService playerService;

    /**
     * Constructor
     * 
     * @param playerService PlayerService
     */
    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * show players
     * 
     * @param model Model
     * @return String template name
     */
    @GetMapping("/players")
    public String players(Model model) {
        List<Player> players = playerService.getPlayers();
        model.addAttribute("players", players);
        return "player/players";
    }

    /**
     * display new player form
     * 
     * @param playerForm PlayerForm
     * @return String template name
     */
    @GetMapping("/signin")
    public String signin(@ModelAttribute("playerForm") PlayerForm playerForm/*
                                                                             * create a playerForm object in model for
                                                                             * display an empty input form
                                                                             */) {
        return "player/signin";
    }

    /**
     * display login form
     * 
     * @param loginForm LoginForm
     * @param error     String
     * @param model     Model
     * @return String template name
     */
    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, /*
                                                                           * create a playerForm object in model for
                                                                           * display an empty input form
                                                                           */
            @RequestParam(name = "error", required = false) Optional<String> error, Model model) {
        if (error.isPresent()) {
            model.addAttribute("message", "Please confirm your login ID and password.");
        }
        return "player/login";
    }

    /**
     * logout
     * 
     * @return String template name
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/afterLogout";
    }

    // ログアウト成功時の画面へ遷移
    @GetMapping("/afterLogout")
    String afterLogout() {
        return "player/afterLogout";
    }

    /**
     * do login
     * 
     * @param loginForm LoginForm
     * @return String view name
     */
    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("loginForm") LoginForm loginForm) {
        playerService.loadUserByUsername(loginForm.getLoginId());
        return "redirect:/players";
    }

    /**
     * create new player
     * 
     * @param playerForm
     * @return String template name
     */
    @PostMapping("/signin")
    public String doSignin(@Valid //
    @ModelAttribute("playerForm") PlayerForm playerForm // get input data from browser
    ) {
        PlayerRegistration playerRegistration = new PlayerRegistration();
        playerRegistration.setLoginId(playerForm.getLoginId());
        playerRegistration.setNickname(playerForm.getNickname());
        playerRegistration.setPassword(playerForm.getPassword());
        playerService.createPlayer(playerRegistration);
        return "redirect:/login";
    }
}
