package jp.btsol.mahjong.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.web.service.RoomService;

/**
 * Rooms controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
public class RoomsController {
    /**
     * Service roomService
     */
    private final RoomService roomService;

    /**
     * Constructor
     * 
     * @param roomService RoomService
     */
    @Autowired
    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * show rooms
     * 
     * @param model Model
     * @return String List<Room>
     */
    @GetMapping("/rooms")
    public String rooms(Model model) {
        List<Room> rooms = roomService.getRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }
}
