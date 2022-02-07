package jp.btsol.mahjong.controller;

import org.mahjong4j.hands.Hands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.service.HandsService;

@RestController
public class HandsController {
	private final HandsService myService;
	public HandsController(HandsService myService) {
		this.myService = myService;
	}
	@GetMapping("/hands")
	public Hands getHands() {
		return myService.getHands();
	}
}
