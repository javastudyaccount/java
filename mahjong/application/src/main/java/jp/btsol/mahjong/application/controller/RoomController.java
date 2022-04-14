package jp.btsol.mahjong.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.RoomService;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.model.RoomId;
import jp.btsol.mahjong.model.RoomName;

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
     * get players in room
     * 
     * @param roomId long
     * @return List<Player>
     */
    @GetMapping("/players")
    public List<Player> getPlayers(@Valid @RequestParam(required = true) long roomId) {
        return roomService.getPlayers(roomId);
    }

    /**
     * enter room
     * 
     * @param roomId RoomId
     */
    @PostMapping("/enter")
    public void enterRoom(@Valid @RequestBody(required = true) RoomId roomId) {
        roomService.enterRoom(roomId.getRoomId());
    }

    /**
     * create new room
     * 
     * @param roomName String
     * @return Room
     */
    @PostMapping(value = "/new")
    public ResponseEntity<Room> createNewRoom(@Valid //
    @RequestBody(required = true) RoomName roomName) {
        Room room = roomService.createNewRoom(roomName.getRoomName());
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
