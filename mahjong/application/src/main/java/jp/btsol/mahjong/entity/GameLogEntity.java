package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * game_log table
 * 
 */
@Data
@Entity
public class GameLogEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long gameLogId;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long playerId;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String operation;
    /**  */
    @Column(nullable = false)
    // JSON
    private String tiles;
    /**  */
    @Column(nullable = true)
    // BIGINT
    private long playerIdCounterpart;
    /**  */
    @Column(nullable = false)
    // BIT
    private boolean deletedFlg;
    /**  */
    @Column(nullable = false)
    // TIME
    private java.sql.Time createdTimestamp;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String createdUser;
    /**  */
    @Column(nullable = false)
    // TIME
    private java.sql.Time updatedTimestamp;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String updatedUser;
}
