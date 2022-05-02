package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * passwd table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passwd implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** player_id */
    @Column(nullable = false)
    private long playerId;
    /** password */
    @Column(length = 256, nullable = false)
    private String password;
    /** deleted_flg */
    @Column(nullable = false)
    private boolean deletedFlg;
    /** created_timestamp */
    @Column(nullable = false)
    private java.sql.Timestamp createdTimestamp;
    /** created_user */
    @Column(length = 20, nullable = false)
    private String createdUser;
    /** updated_timestamp */
    @Column(nullable = false)
    private java.sql.Timestamp updatedTimestamp;
    /** updated_user */
    @Column(length = 20, nullable = false)
    private String updatedUser;
}
