package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * game_log table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLog implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** game_log_id */
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameLogId;
    /** game_id */
    @Column(nullable = false)
    private long gameId;
    /** player_id */
    @Column(nullable = false)
    private long playerId;
    /** operation */
    @Column(length = 120, nullable = false)
    private String operation;
    /** tiles */
    @Column(nullable = true)
    private String tiles;
    /** player_id_counterpart */
    @Column(nullable = true)
    private long playerIdCounterpart;
    /** log */
    @Column(length = 256, nullable = true)
    private String log;
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
