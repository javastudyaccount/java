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
 * game table
 * 
 */
@Data
@Entity
public class Game implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=YES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	// sqlTypeName: BIGINT
    private long gameId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: BIGINT
    private long roomeId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: TIME
    private java.sql.Time startedTimestamp;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: TIME
    private java.sql.Time endedTimestamp;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: JSON
    private String shuffledTiles;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String fromDirection;
    /**  */
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: INT
    private int fromColumn;
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
    @Column(nullable = true)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String updatedUser;
}
