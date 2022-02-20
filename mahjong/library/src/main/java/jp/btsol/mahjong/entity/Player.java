package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 
 * player table
 * 
 */
@Data
public class Player implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=YES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // sqlTypeName: BIGINT
    private long playerId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
    // sqlTypeName: VARCHAR
    private String nickename;
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
