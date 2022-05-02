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
 * game table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** game_id */
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    /** room_id */
    @Column(nullable = false)
    private long roomId;
    /** started_timestamp */
    @Column(nullable = true)
    private java.sql.Timestamp startedTimestamp;
    /** ended_timestamp */
    @Column(nullable = true)
    private java.sql.Timestamp endedTimestamp;
    /** shuffled_tiles */
    @Column(nullable = true)
    private String shuffledTiles;
    /** from_direction */
    @Column(length = 20, nullable = true)
    private String fromDirection;
    /** from_column */
    @Column(nullable = true)
    private int fromColumn;
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
