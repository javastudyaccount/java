package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.RoomId;
import jp.btsol.mahjong.model.RoomModel;
import jp.btsol.mahjong.model.RoomName;
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
     * @param mahjongRestTemplate   MahjongRestTemplate
     */
    public RoomService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
    }

    /**
     * get room list
     * 
     * @return List<RoomModel>
     */
    public List<RoomModel> getRooms() {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getRooms();

        List<RoomModel> rooms = mahjongRestTemplate.get(url, ArrayList.class);
        return rooms;
    }

    /**
     * get room
     * 
     * @param roomId long
     * @return RoomModel
     */
    public RoomModel getRoom(long roomId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getRoom();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roomId", roomId);
        RoomModel room = mahjongRestTemplate.get(url, param, RoomModel.class);
        return room;
    }

    /**
     * get player list
     * 
     * @param roomId long
     * @return List<Player>
     */
    public List<Player> getPlayers(long roomId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getPlayersInRoom();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roomId", roomId);
        List<Player> players = mahjongRestTemplate.get(url, param, ArrayList.class);
        return players;
    }

    /**
     * enter room
     * 
     * @param roomId    long
     * @param invitorId long
     */
    public void enterRoom(long roomId, Optional<Long> invitorId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getEnterRoom();
        RoomId roomIdModel = new RoomId();
        roomIdModel.setRoomId(roomId);
        if (invitorId.isPresent()) {
            roomIdModel.setInvitorId(invitorId.get());
        }
        mahjongRestTemplate.post(url, roomIdModel);
    }

    /**
     * exit room
     * 
     * @param roomId long
     */
    public void exitRoom(long roomId) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getExitRoom();
        RoomId roomIdModel = new RoomId();
        roomIdModel.setRoomId(roomId);
        mahjongRestTemplate.post(url, roomIdModel);
    }

    /**
     * create room
     * 
     * @param roomName String
     */
    public void createRoom(String roomName) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreateRoom();
        RoomName roomNameModel = new RoomName(roomName);
        mahjongRestTemplate.post(url, roomNameModel);
    }
}
