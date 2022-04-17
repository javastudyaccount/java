package jp.btsol.mahjong.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomModel {
    /**
     * roomId
     */
    private long roomId;
    /**
     * roomName
     */
    private String roomName;
    /**
     * indicated if I entered the room
     */
    private boolean entered;
    /**
     * players in room
     */
    private List<PlayerModel> playersInRoom;
    /**
     * game id
     */
    private Long gameId;
}
