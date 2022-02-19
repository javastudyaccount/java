package jp.btsol.mahjong.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * room table
 * 
 */
@Data
public class RoomEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** ${column.remarks} */
    private long roomId;
    /** ${column.remarks} */
    private String roomName;
    /** ${column.remarks} */
    private boolean deletedFlg;
    /** ${column.remarks} */
    private java.sql.Timestamp createdTimestamp;
    /** ${column.remarks} */
    private String createdUser;
    /** ${column.remarks} */
    private java.sql.Timestamp updatedTimestamp;
    /** ${column.remarks} */
    private String updatedUser;
}
