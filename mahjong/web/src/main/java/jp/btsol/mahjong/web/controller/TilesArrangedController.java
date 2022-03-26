package jp.btsol.mahjong.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Show arranged tiles
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class TilesArrangedController {
    /**
     * show arranged tiles
     * 
     * @param model Model
     * @return String arranged tiles template
     */
    @GetMapping("/tiles/arranged")
    public String home(Model model) {

        return "tiles/arranged";
    }
}
