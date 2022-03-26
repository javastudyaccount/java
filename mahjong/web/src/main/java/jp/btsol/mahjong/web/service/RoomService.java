package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
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
     * Rest template
     */
    private final MahjongRestTemplate mahjongRestTemplate;
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     */
    public RoomService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
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

        List<Room> rooms = mahjongRestTemplate.get(url, ArrayList.class);
        return rooms;
    }
}
