package jp.btsol.mahjong.service;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.emory.mathcs.backport.java.util.Arrays;
import jp.btsol.mahjong.entity.Room;
import lombok.extern.slf4j.Slf4j;

/**
 * Room service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class RoomService {
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
    public RoomService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * get room list
     * 
     * @return List<Room>
     */
    public List<Room> getRooms() {
        RestTemplate rest = new RestTemplate();

        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getRooms();
        ResponseEntity<String> json = rest.getForEntity(url, String.class);

        List<Room> rooms = null;
        try {
            Room[] roomsArr = objectMapper.readValue(json.getBody(), Room[].class);
            rooms = Arrays.asList(roomsArr);
        } catch (JsonProcessingException e) {
            log.error("Rooms error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return rooms;
    }
}
