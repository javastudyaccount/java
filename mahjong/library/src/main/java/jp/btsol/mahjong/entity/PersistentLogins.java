package jp.btsol.mahjong.entity;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * persistent_logins table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLogins implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(length = 64, nullable = false)
    private String loginId;
    /**  */
    @Column(length = 64, nullable = false)
    private String series;
    /**  */
    @Column(length = 64, nullable = false)
    private String token;
    /**  */
    @Column(nullable = false)
    private java.sql.Timestamp lastUsed;
}
