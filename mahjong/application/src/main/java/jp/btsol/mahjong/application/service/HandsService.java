package jp.btsol.mahjong.application.service;

//CHECKSTYLE:OFF
import static org.mahjong4j.tile.Tile.CHN;
import static org.mahjong4j.tile.Tile.P4;
import static org.mahjong4j.tile.Tile.TON;

//CHECKSTYLE:ON
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.mahjong4j.IllegalMentsuSizeException;
import org.mahjong4j.MahjongTileOverFlowException;
import org.mahjong4j.hands.Hands;
import org.mahjong4j.hands.Kantsu;
import org.mahjong4j.hands.Kotsu;
import org.mahjong4j.hands.Mentsu;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Hands service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@Transactional
public class HandsService {
    /**
     * get hands
     * 
     * @return Hands
     */
    public Hands getHands() {
        //@formatter:off
        int[] otherTiles = {
            0, 0, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0,
            2, 0, 0, 0,
            0, 0, 0
        };
        //@formatter:on
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
