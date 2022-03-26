package jp.btsol.mahjong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.btsol.mahjong.web.service.ShuffledService;

/**
 * Show arranged tiles
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class TilesShuffledController {
    /**
     * Service to get tiles of shuffled
     */
    private final ShuffledService shuffledService;

    /**
     * Constructor
     * 
     * @param shuffledService ShuffledService
     */
    @Autowired
    public TilesShuffledController(ShuffledService shuffledService) {
        this.shuffledService = shuffledService;
    }

    /**
     * show shuffled tiles
     * 
     * @param show  boolean show tiles
     * @param model Model Model
     * @return String arranged tiles template
     */
    @GetMapping("/tiles/shuffled")
    public String shuffle(@RequestParam(name = "show", required = false) boolean show, Model model) {
        model.addAttribute("show", show);
        int[] tiles = shuffledService.getShuffledTiles();
        model.addAttribute("tiles", tiles);
        return "tiles/shuffled";
    }
}
