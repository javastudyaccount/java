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
 * player table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playerId;
    /**  */
    @Column(length = 20, nullable = false)
    private String nickename;
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
    @Column(length = 20, nullable = false)
    private String updatedUser;
}
