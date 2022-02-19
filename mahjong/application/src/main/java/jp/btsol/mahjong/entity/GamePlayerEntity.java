package jp.btsol.mahjong.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * game_player table
 * 
 */
@Data
public class GamePlayerEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** ${column.remarks} */
    private long gameId;
    /** ${column.remarks} */
    private long playerId;
    /** ${column.remarks} */
    private int last;
    /** ${column.remarks} */
    private String direction;
    /** ${column.remarks} */
    private boolean isEast;
    /** ${column.remarks} */
    private boolean deletedFlg;
    /** ${column.remarks} */
    private java.sql.Time createdTimestamp;
    /** ${column.remarks} */
    private String createdUser;
    /** ${column.remarks} */
    private java.sql.Time updatedTimestamp;
    /** ${column.remarks} */
    private String updatedUser;
}
