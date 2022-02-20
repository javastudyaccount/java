package jp.btsol.mahjong.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import lombok.Data;

/**
 * 
 * game_log table
 * 
 */
@Data
@Entity
public class GameLog implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=YES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	// sqlTypeName: BIGINT
    private long gameLogId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: BIGINT
    private long playerId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String operation;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: JSON
    private String tiles;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: BIGINT
    private long playerIdCounterpart;
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
