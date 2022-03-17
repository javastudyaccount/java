package jp.btsol.mahjong.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.fw.exception.BadRequestException;
import jp.btsol.mahjong.application.service.RoomService;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.model.RoomName;
import jp.btsol.mahjong.utils.validator.Validator;

/**
 * Room controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    /**
     * room service
     */
    private final RoomService roomService;

    /**
     * Constructor
     * 
     * @param roomService RoomService
     */
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * get room list
     * 
     * @return List<Room>
     */
    @GetMapping("/all")
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    /**
     * create new room
     * 
     * @param roomName String
     * @return Room
     */
    @PostMapping(value = "/new")
    public Room createNewRoom(@RequestBody(required = true) RoomName roomName) {
        int maxNameLen = Validator.getMaxLength(new Room(), "roomName");
        if (maxNameLen > 0 && roomName.getRoomName().length() > maxNameLen) {
            throw new BadRequestException("room name is more than " + maxNameLen + ".");
        }
        return roomService.createNewRoom(roomName.getRoomName());
    }
}
