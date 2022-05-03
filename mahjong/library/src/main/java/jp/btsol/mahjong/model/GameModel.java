package jp.btsol.mahjong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameModel {
    /**
     * gameId long
     */
    private long gameId;
    /**
     * roomId
     */
    private long roomId;
    /**
     * roomModel
     */
    private RoomModel roomModel;
    /**
     * count of players
     */
    private int countOfPlayers;
    /**
     * game status
     */
    private String gameStatus;
}
