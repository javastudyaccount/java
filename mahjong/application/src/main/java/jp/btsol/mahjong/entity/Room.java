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
 * room table
 * 
 */
@Data
@Entity
public class Room implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=YES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	// sqlTypeName: BIGINT
    private long roomId;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String roomName;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: BIT
    private boolean deletedFlg;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: TIMESTAMP
    private java.sql.Timestamp createdTimestamp;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String createdUser;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: TIMESTAMP
    private java.sql.Timestamp updatedTimestamp;
    /**  */
    @Column(nullable = false)
    // autoIncremnt: autoIncrement=NO
	// sqlTypeName: VARCHAR
    private String updatedUser;
}
