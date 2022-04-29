package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * invite_player table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitePlayer implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** invite_from */
    @Column(nullable = false)
    private long inviteFrom;
    /** invite_to */
    @Column(nullable = false)
    private long inviteTo;
    /** invite_timestamp */
    @Column(nullable = false)
    private java.sql.Timestamp inviteTimestamp;
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
