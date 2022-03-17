package jp.btsol.mahjong.web.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
     * Object mapper
     */
    private final ObjectMapper objectMapper;
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
     * @param objectMapper          ObjectMapper object mapper
     * @param restTemplate          MahjongRestTemplate
     */
    public ShuffledService(ApplicationProperties applicationProperties, ObjectMapper objectMapper,
            MahjongRestTemplate restTemplate) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * get tiles of shuffled
     * 
     * @return tiles
     */
    public int[] getShuffledTiles() {
        String path = applicationProperties.getUri() + applicationProperties.getPath().getShuffled();
        return restTemplate.post(path, "{\"gameId\":1}", int[].class);
    }
}
