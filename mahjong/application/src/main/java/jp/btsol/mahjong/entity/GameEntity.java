package jp.btsol.mahjong.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * game table
 * 
 */
@Data
public class GameEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** ${column.remarks} */
    private long gameId;
    /** ${column.remarks} */
    private long roomeId;
    /** ${column.remarks} */
    private java.sql.Time startedTimestamp;
    /** ${column.remarks} */
    private java.sql.Time endedTimestamp;
    /** ${column.remarks} */
    private String fromDirection;
    /** ${column.remarks} */
    private int fromColumn;
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
