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
    /**  */
    @Column(nullable = false)
    private long gameId;
    /**  */
    @Column(nullable = false)
    private long playerId;
    /**  */
    @Column(nullable = true)
    private String mentsu;
    /**  */
    @Column(nullable = true)
    private int last;
    /**  */
    @Column(length = 20, nullable = true)
    private String direction;
    /**  */
    @Column(nullable = true)
    private boolean isEast;
    /**  */
    @Column(nullable = false)
    private boolean deletedFlg;
    /**  */
    @Column(nullable = false)
    private java.sql.Timestamp createdTimestamp;
    /**  */
    @Column(length = 20, nullable = false)
    private String createdUser;
    /**  */
    @Column(nullable = false)
    private java.sql.Timestamp updatedTimestamp;
    /**  */
    @Column(length = 20, nullable = false)
    private String updatedUser;
}
