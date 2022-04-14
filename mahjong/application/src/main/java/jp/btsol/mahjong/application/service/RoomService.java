package jp.btsol.mahjong.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.entity.RoomPlayer;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.utils.validator.Validator;
import lombok.extern.slf4j.Slf4j;

/**
 * Room service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@Transactional
public class RoomService {
    /**
     * userContext アプリユーザー情報コンテキスト
     */
    private final UserContext userContext;
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    public RoomService(BaseRepository baseRepository, //
            UserContext userContext) {
        this.baseRepository = baseRepository;
        this.userContext = userContext;
    }

    /**
     * get rooms
     * 
     * @return List<Room>
     */
    public List<Room> getRooms() {
        return baseRepository.findForList("select * from room", Room.class);
    }

    /**
     * get players in room
     * 
     * @param roomId long
     * @return List<Player>
     */
    public List<Player> getPlayers(long roomId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roomId", roomId);
        return baseRepository.findForList(//
                "select * from player where player.player_id in "//
                        + "(select player_id from room_player where room_id = :roomId) "//
                        + "order by player_id",
                param, Player.class);
    }

    /**
     * enter room
     * 
     * @param roomId long
     * 
     */
    public void enterRoom(long roomId) {
        log.info("player id {}", userContext.playerId());
        RoomPlayer roomPlayer = new RoomPlayer();
        roomPlayer.setRoleId(roomId);
        roomPlayer.setPlayerId(userContext.playerId());
        roomPlayer.setRoleId(0); // visitor
        baseRepository.insert(roomPlayer);
    }

    /**
     * insert room
     * 
     * @param roomName String
     * @return Room
     */
    public Room createNewRoom(String roomName) {
        if (Objects.isNull(roomName)) {
            throw new RuntimeException("room name can not be null.");
        }
        Room room = new Room();
        room.setRoomName(roomName);
        Validator.validateMaxLength(room);

        int roomId = 0;
        try {
            roomId = baseRepository.insertWithSurrogateKey(room);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());

            DuplicateKeyException dke = new DuplicateKeyException("Room name exists.", e);
            throw dke;
        }
        return baseRepository.findById(roomId, Room.class);
    }
}
