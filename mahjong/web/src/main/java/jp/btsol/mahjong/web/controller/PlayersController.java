package jp.btsol.mahjong.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.btsol.mahjong.entity.Player;
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
     * @param playerForm
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
     * create new player
     * 
     * @param playerForm
     * @return String template name
     */
    @PostMapping("/signin")
    public String doSignin(@Valid //
    @ModelAttribute("playerForm") PlayerForm playerForm // get input data from browser
    ) {
        playerService.createPlayer(playerForm.getNickname(), playerForm.getPassword());
        return "redirect:/login";
    }
}
