package jp.btsol.mahjong.model;

import java.util.List;

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
    /**
     * game id
     */
    private long gameId;
    /**
     * game status
     */
    private String gameStatus;
    /**
     * room id
     */
    private long roomId;
    /**
     * players List<PlayerModel>
     */
    private List<PlayerModel> players;
}
