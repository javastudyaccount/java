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
    /**  */
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;
    /**  */
    @Column(nullable = false)
    private long roomeId;
    /**  */
    @Column(nullable = true)
    private java.sql.Timestamp startedTimestamp;
    /**  */
    @Column(nullable = true)
    private java.sql.Timestamp endedTimestamp;
    /**  */
    @Column(nullable = true)
    private String shuffledTiles;
    /**  */
    @Column(length = 20, nullable = true)
    private String fromDirection;
    /**  */
    @Column(nullable = true)
    private int fromColumn;
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
