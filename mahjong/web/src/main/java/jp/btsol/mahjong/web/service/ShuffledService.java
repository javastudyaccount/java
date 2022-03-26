package jp.btsol.mahjong.web.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Get tiles of shuffled
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class ShuffledService {
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;
    /**
     * MahjongRestTemplate
     */
    private final MahjongRestTemplate restTemplate;

    /**
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param restTemplate          MahjongRestTemplate
     */
    public ShuffledService(ApplicationProperties applicationProperties, MahjongRestTemplate restTemplate) {
        this.applicationProperties = applicationProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * get tiles of shuffled
     * 
     * @return tiles
     */
    public int[] getShuffledTiles() {
        String path = applicationProperties.getUri() + applicationProperties.getPath().getShuffled();
        GameId gameId = new GameId();
        gameId.setGameId(1);
        return restTemplate.post(path, gameId, int[].class);
    }
}
