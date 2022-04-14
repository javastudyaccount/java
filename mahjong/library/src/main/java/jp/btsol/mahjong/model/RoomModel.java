package jp.btsol.mahjong.model;

import jp.btsol.mahjong.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomModel extends Room {
    /**
     * indicated if I entered the room
     */
    private boolean entered;
}
