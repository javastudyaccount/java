package jp.btsol.mahjong.web.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import jp.btsol.mahjong.model.RoomModel;
import jp.btsol.mahjong.web.form.EnterRoomForm;
import jp.btsol.mahjong.web.form.RoomForm;
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
public class RoomController {
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
    public RoomController(RoomService roomService) {
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
        // get data
        List<RoomModel> allRooms = roomService.getRooms();
        // set data to model
        model.addAttribute("rooms", allRooms);
        Boolean entered = allRooms.stream().filter(roomModel -> roomModel.isEntered()).findAny().isPresent();
        model.addAttribute("entered", entered);
        // show html page
        return "room/rooms";
    }

    /**
     * enter room
     * 
     * @param enterRoomForm EnterRoomForm
     * @param invitorId     long
     * @return String view name
     */
    @PostMapping("/enterRoom")
    public String enterRoom(@ModelAttribute("enterRoomForm") EnterRoomForm enterRoomForm, //
            @RequestParam(name = "invitorId", required = false) Optional<Long> invitorId) {
        log.info("roomId: {}", enterRoomForm.getRoomId());
//        try {
        roomService.enterRoom(enterRoomForm.getRoomId(), invitorId);
//        } catch (HttpServerErrorException e) {
//            log.error(e.getLocalizedMessage());
//        }

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(RoomController.class, "room", Model.class, enterRoomForm.getRoomId()).build();

        URI location = uriComponents.toUri();

        return "redirect:" + location.toString();
    }

    /**
     * exit room
     * 
     * @param roomId long
     * @return String view name
     */
    @PostMapping("/exitRoom")
    public String exitRoom(@Valid //
    @RequestParam long roomId) {
        log.info("roomId: {}", roomId);
        roomService.exitRoom(roomId);

        return "redirect:/rooms";
    }

    /**
     * create room
     * 
     * @param roomForm RoomForm
     * @return String template name
     */
    @GetMapping("/createRoom")
    public String createRoom(@ModelAttribute("roomForm") RoomForm roomForm) {
        return "room/create";
    }

    /**
     * create room
     * 
     * @param roomForm RoomForm
     * @return String template name
     */
    @PostMapping("/createRoom")
    public String postCreateRoom(@Valid //
    @ModelAttribute("roomForm") RoomForm roomForm) {
        roomService.createRoom(roomForm.getRoomName());
        return "redirect:/rooms";
    }

    /**
     * show room
     * 
     * @param model  Model
     * @param roomId long
     * @return String view name
     */
    @GetMapping("/room/{roomId}")
    public String room(Model model, @Valid //
    @PathVariable("roomId") long roomId) {
        RoomModel room = roomService.getRoom(roomId);
        model.addAttribute("room", room);
//        List<Player> playersInRoom = roomService.getPlayers(roomId);
//        model.addAttribute("players", playersInRoom);
        return "room/room";
    }
}
