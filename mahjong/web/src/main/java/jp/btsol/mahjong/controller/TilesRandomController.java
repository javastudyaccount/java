package jp.btsol.mahjong.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.mahjong4j.tile.Tile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.tile.Position;

@Controller
public class TilesRandomController {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final double ANGLE_MIN = 0;
	private static final double ANGLE_MAX = 2 * Math.PI;
	@GetMapping("/tiles/random")
	public String home(Model model) {
		List<Position> postions = new ArrayList<>();
		Arrays.stream(Tile.values()).forEach(tile->{
			IntStream.range(0, 4).forEach(i ->{
				Position pos = new Position();
				Point center = new Point();
				center.x = ThreadLocalRandom.current().nextInt(WIDTH);
				center.y = ThreadLocalRandom.current().nextInt(HEIGHT);
				pos.setCenter(center);
				pos.setAngle(ThreadLocalRandom.current().nextDouble(ANGLE_MIN, ANGLE_MAX));
				postions.add(pos);
			});
		});
		model.addAttribute("WIDTH", WIDTH);
		model.addAttribute("HEIGHT", HEIGHT);
		model.addAttribute("positions", postions);
		return "tiles/random";
	}
}
