package jp.btsol.mahjong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Show all tiles
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class TilesController {
	/**
	 * show all tiles
	 * @param model Model
	 * @return String tiles template
	 */
	@GetMapping("/tiles")
	public String home(Model model) {
		return "tiles";
	}  
}
