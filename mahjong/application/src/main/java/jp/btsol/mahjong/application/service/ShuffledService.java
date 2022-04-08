package jp.btsol.mahjong.application.service;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.mahjong4j.Utils;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.constant.Constant;
import lombok.extern.slf4j.Slf4j;

/**
 * Shuffling service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@Transactional
public class ShuffledService {

    /**
     * Shuffle tiles
     * 
     * @param gameId long
     * @return shuffled tiles
     */
    public int[] shuffled(long gameId) {
        int[] tiles = IntStream.rangeClosed(0, Constant.TOTAL_TILES - 1).toArray();
        Utils.shuffleArray(tiles);
        // save shuffled tiles

        return tiles;
    }
}
