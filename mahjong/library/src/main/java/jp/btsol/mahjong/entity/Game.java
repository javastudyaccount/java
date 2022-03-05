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
    @Column(nullable = false)
    private java.sql.Time startedTimestamp;
    /**  */
    @Column(nullable = true)
    private java.sql.Time endedTimestamp;
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
    private java.sql.Time createdTimestamp;
    /**  */
    @Column(length = 20, nullable = false)
    private String createdUser;
    /**  */
    @Column(nullable = false)
    private java.sql.Time updatedTimestamp;
    /**  */
    @Column(length = 20, nullable = true)
    private String updatedUser;
}
