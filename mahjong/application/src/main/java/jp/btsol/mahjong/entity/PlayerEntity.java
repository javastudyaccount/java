package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

/**
 * 
 * player table
 * 
 */
@Data
@Entity
public class PlayerEntity implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // BIGINT
    private long playerId;
    /**  */
    @Column(nullable = false)
    // VARCHAR
    private String nickename;
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
