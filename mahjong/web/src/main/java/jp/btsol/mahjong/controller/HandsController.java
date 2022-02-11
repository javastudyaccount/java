package jp.btsol.mahjong.controller;

import org.mahjong4j.hands.Hands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.service.HandsService;
/**
 * Show tiles of a hand
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class HandsController {
	/**
	 * Service to get tiles of a hand
	 */
	@Autowired HandsService handsService;
	/**
	 * show tiles of a hand
	 * @param model Model
	 * @return String hands template
	 */
	@GetMapping("/hands")
	public String home(Model model) {
		Hands hands = handsService.getHands();
		model.addAttribute("hands", hands);
		return "hands";
	}  
}
