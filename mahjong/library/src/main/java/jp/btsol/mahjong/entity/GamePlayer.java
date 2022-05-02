package jp.btsol.mahjong.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * game_player table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamePlayer implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** game_id */
    @Column(nullable = false)
    private long gameId;
    /** player_id */
    @Column(nullable = false)
    private long playerId;
    /** mentsu */
    @Column(nullable = true)
    private String mentsu;
    /** last */
    @Column(nullable = true)
    private int last;
    /** direction */
    @Column(length = 20, nullable = true)
    private String direction;
    /** is_east */
    @Column(nullable = true)
    private boolean isEast;
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
