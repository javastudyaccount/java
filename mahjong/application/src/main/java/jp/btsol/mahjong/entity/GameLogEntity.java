package jp.btsol.mahjong.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * game_log table
 * 
 */
@Data
public class GameLogEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** ${column.remarks} */
    private long gameLogId;
    /** ${column.remarks} */
    private long gameId;
    /** ${column.remarks} */
    private long playerId;
    /** ${column.remarks} */
    private String operation;
    /** ${column.remarks} */
    private long playerIdCounterpart;
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
