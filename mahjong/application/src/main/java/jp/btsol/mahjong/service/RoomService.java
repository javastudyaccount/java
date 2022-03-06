package jp.btsol.mahjong.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.repository.BaseRepository;
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
public class RoomService {
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    public RoomService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
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
     * insert room
     * 
     * @param roomName  String
     * @param requestId String
     * @return Room
     */
    public Room createNewRoom(String roomName, String requestId) {
        if (Objects.isNull(roomName)) {
            throw new RuntimeException("room name can not be null.");
        }
        Room room = new Room();
        room.setRoomName(roomName);
        room.setCreatedUser(requestId);
        room.setUpdatedUser(requestId);
        Validator.validate(room);

        int roomId = 0;
        try {
            roomId = baseRepository.insert(room);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());

            throw new DuplicateKeyException("Room name exists.", e);
        }
        return baseRepository.findById(roomId, Room.class);
    }
}
