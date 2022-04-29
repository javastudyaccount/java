package jp.btsol.mahjong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerModel {
    /**
     * playerId
     */
    private long playerId;
    /**
     * nickname
     */
    private String nickname;
    /**
     * roomId
     */
    private Long roomId;
    /**
     * room name
     */
    private String roomName;
    /**
     * game id
     */
    private Long gameId;
    /**
     * invited flag
     */
    private boolean invitedFlg;
}
