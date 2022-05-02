package jp.btsol.mahjong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MahjongGameMessage {
    /**
     * action String
     */
    private String action;
    /**
     * playerId long
     */
    private long playerId;
    /**
     * nickname String
     */
    private String nickname;
    /**
     * message String
     */
    private String message;
}
