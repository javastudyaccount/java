package jp.btsol.mahjong.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * player table
 * 
 */
@Data
public class PlayerEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** ${column.remarks} */
    private long playerId;
    /** ${column.remarks} */
    private String nickename;
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
