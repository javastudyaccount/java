package jp.btsol.mahjong.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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
public class ShuffledService {
    /**
     * Implementing Fisher–Yates shuffle
     * 
     * @param ar array of integers
     */
    static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /**
     * Shuffle tiles
     * 
     * @param gameId long
     * @return shuffled tiles
     */
    public int[] shuffled(long gameId) {
        int[] tiles = IntStream.rangeClosed(0, Constant.TOTAL_TILES - 1).toArray();
        shuffleArray(tiles);
        // save shuffled tiles

        return tiles;
    }
}
