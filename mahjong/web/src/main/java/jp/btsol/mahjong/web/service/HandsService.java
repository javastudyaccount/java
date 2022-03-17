package jp.btsol.mahjong.web.service;

import org.mahjong4j.hands.Hands;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Get tiles of a hand
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class HandsService {
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
    public HandsService(ApplicationProperties applicationProperties, MahjongRestTemplate restTemplate) {
        this.applicationProperties = applicationProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * get tiles of a hand
     * 
     * @return Hands
     */
    public Hands getHands() {
        String path = applicationProperties.getUri() + applicationProperties.getPath().getHands();
        return restTemplate.get(path, Hands.class);
    }
}
