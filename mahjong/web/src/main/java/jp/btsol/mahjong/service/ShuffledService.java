package jp.btsol.mahjong.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param objectMapper          ObjectMapper object mapper
     */
    public ShuffledService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * get tiles of shuffled
     * 
     * @return tiles
     */
    public int[] getShuffledTiles() {
        RestTemplate rest = new RestTemplate();

        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getHands();
        ResponseEntity<String> json = rest.getForEntity(url, String.class);

        int[] tiles = null;
        try {
            tiles = objectMapper.readValue(json.getBody(), int[].class);
        } catch (JsonProcessingException e) {
            log.error("Hands error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return tiles;
    }
}
