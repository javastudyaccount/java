package jp.btsol.mahjong.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.service.RoomService;

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
     * @return Room
     */
    @GetMapping("/new")
    public Room createNewRoom() {
        String roomName = "testRoom";
        return roomService.createNewRoom(roomName);
    }
}
