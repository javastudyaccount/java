package jp.btsol.mahjong.service;

import static org.mahjong4j.tile.Tile.CHN;
import static org.mahjong4j.tile.Tile.P4;
import static org.mahjong4j.tile.Tile.TON;

import java.util.ArrayList;
import java.util.List;

import org.mahjong4j.IllegalMentsuSizeException;
import org.mahjong4j.MahjongTileOverFlowException;
import org.mahjong4j.hands.Hands;
import org.mahjong4j.hands.Kantsu;
import org.mahjong4j.hands.Kotsu;
import org.mahjong4j.hands.Mentsu;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HandsService {
	public Hands getHands() {
		int[] otherTiles = {
	            0, 0, 1, 1, 1, 0, 0, 0, 0,
	            0, 0, 1, 1, 1, 0, 0, 0, 0,
	            0, 0, 0, 0, 0, 0, 0, 0, 0,
	            2, 0, 0, 0,
	            0, 0, 0
	        };
	        List<Mentsu> mentsuList = new ArrayList<>(2);
	        mentsuList.add(new Kotsu(true, P4));
	        mentsuList.add(new Kantsu(true, CHN));

	        Hands actualHands = null;
			try {
				actualHands = new Hands(otherTiles, TON, mentsuList);
			} catch (MahjongTileOverFlowException | IllegalMentsuSizeException e) {
				log.error("Hands error: " + e.getLocalizedMessage());
			}
	        return actualHands;
	}
}
