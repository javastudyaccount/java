package jp.btsol.mahjong.controller;

import org.mahjong4j.hands.Hands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.service.HandsService;

@Controller
public class HandsController {
	@Autowired HandsService handsService;
	@GetMapping("/hands")
	public String home(Model model) {
		Hands hands = handsService.getHands();
		model.addAttribute("hands", hands);
		return "hands";
	}  
}
