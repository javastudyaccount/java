package jp.btsol.mahjong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.web.service.HomeService;
/**
 * Home page controller
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class HomeController {
	/**
	 * Service to get home message
	 */
	@Autowired HomeService homeService;
	/**
	 * show home message
	 * @param model Model
	 * @return String home template
	 */
	@GetMapping("/")
	public String home(Model model) {
		String message = homeService.getHomeMessage();
		model.addAttribute("message", message);
		return "home";
	}  
}
