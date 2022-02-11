package jp.btsol.mahjong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.service.HandsService;

@Controller
public class TilesController {
	@Autowired HandsService handsService;
	@GetMapping("/tiles")
	public String home(Model model) {
		return "tiles";
	}  
}
