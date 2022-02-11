package jp.btsol.mahjong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TilesArrangedController {
	@GetMapping("/tiles/arranged")
	public String home(Model model) {

		return "tiles/arranged";
	}  
}
