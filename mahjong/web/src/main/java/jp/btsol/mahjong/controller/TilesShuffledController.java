package jp.btsol.mahjong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Show arranged tiles
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class TilesShuffledController {
    /**
     * show shuffled tiles
     * 
     * @param show  boolean show tiles
     * @param model Model Model
     * @return String arranged tiles template
     */
    @GetMapping("/tiles/shuffled")
    public String home(@RequestParam(name = "show", required = false) boolean show, Model model) {
        model.addAttribute("show", show);
        return "tiles/shuffled";
    }
}
