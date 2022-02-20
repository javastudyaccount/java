package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * game_player table
 * 
 */
@Data
@Entity
public class GamePlayerEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long playerId;
    /**  */
    @Column(nullable = true)
    // JSON
    private String mentsu;
    /**  */
    @Column(nullable = true)
    // INT
    private int last;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String direction;
    /**  */
    @Column(nullable = false)
    // BIT
    private boolean isEast;
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
