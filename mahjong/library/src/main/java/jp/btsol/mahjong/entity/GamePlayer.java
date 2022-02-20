package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.Data;

/**
 * 
 * game_player table
 * 
 */
@Data
public class GamePlayer implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: BIGINT
    private long playerId;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: JSON
    private String mentsu;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: INT
    private int last;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: VARCHAR
    private String direction;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: BIT
    private boolean isEast;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: BIT
    private boolean deletedFlg;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: TIME
    private java.sql.Time createdTimestamp;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: VARCHAR
    private String createdUser;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: TIME
    private java.sql.Time updatedTimestamp;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: VARCHAR
    private String updatedUser;
}
