package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * game table
 * 
 */
@Data
@Entity
public class GameEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long roomeId;
    /**  */
    @Column(nullable = false)
    // TIME
    private java.sql.Time startedTimestamp;
    /**  */
    @Column(nullable = true)
    // TIME
    private java.sql.Time endedTimestamp;
    /**  */
    @Column(nullable = true)
    // JSON
    private String shuffledTiles;
    /**  */
    @Column(nullable = true)
    // VARCHAR
    private String fromDirection;
    /**  */
    @Column(nullable = true)
    // INT
    private int fromColumn;
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
    @Column(nullable = true)
    // VARCHAR
    private String updatedUser;
}
