package jp.btsol.mahjong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.service.PlayerService;

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
     * @return String List<Player>
     */
    @GetMapping("/players")
    public String players(Model model) {
        List<Player> players = playerService.getPlayers();
        model.addAttribute("players", players);
        return "players";
    }
}
