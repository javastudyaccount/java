package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * room table
 * 
 */
@Data
@Entity
public class RoomEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long roomId;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String roomName;
    /**  */
    @Column(nullable = false)
    // BIT
    private boolean deletedFlg;
    /**  */
    @Column(nullable = false)
    // TIMESTAMP
    private java.sql.Timestamp createdTimestamp;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String createdUser;
    /**  */
    @Column(nullable = false)
    // TIMESTAMP
    private java.sql.Timestamp updatedTimestamp;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String updatedUser;
}
