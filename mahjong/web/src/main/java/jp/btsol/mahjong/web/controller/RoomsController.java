package jp.btsol.mahjong.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.web.service.RoomService;
import lombok.extern.slf4j.Slf4j;

/**
 * Rooms controller
 * 
 * @author B&T Solutions Inc.
 *
 */
@Controller
@Slf4j
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
     * @return String view name
     */
    @GetMapping("/rooms")
    public String rooms(Model model) {
        List<Room> rooms = roomService.getRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    /**
     * enter room
     * 
     * @param model  Model
     * @param roomId long
     * @return String view name
     */
    @PostMapping("/enterRoom")
    public String enterRoom(@Valid @RequestParam long roomId) {
        log.info("roomId: {}", roomId);
        roomService.enterRoom(roomId);

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(RoomsController.class, "room", Model.class, roomId).build();

        URI location = uriComponents.toUri();

        return "redirect:" + location.toString();
    }

    /**
     * enter room
     * 
     * @param model  Model
     * @param roomId long
     * @return String view name
     */
    @GetMapping("/room/{roomId}")
    public String room(Model model, @Valid @PathVariable("roomId") long roomId) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "room";
    }
}
