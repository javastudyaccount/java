package jp.btsol.mahjong.model;

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
}
