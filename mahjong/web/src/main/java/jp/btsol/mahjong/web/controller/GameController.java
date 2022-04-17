package jp.btsol.mahjong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
public class GameController {
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
    public GameController(RoomService roomService) {
        this.roomService = roomService;
    }

}
