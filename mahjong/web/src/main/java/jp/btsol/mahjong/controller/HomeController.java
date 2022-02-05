package jp.btsol.mahjong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.service.HomeService;

@Controller
public class HomeController {
	@Autowired HomeService homeService;
	@GetMapping("/")
	public String home(Model model) {
		String message = homeService.getHomeMessage();
		model.addAttribute("message", message);
		return "home";
	}  
}
