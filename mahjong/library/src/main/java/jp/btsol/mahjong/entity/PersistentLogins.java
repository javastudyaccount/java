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
 * persistent_logins table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLogins implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /** login_id */
    @Column(length = 64, nullable = false)
    private String loginId;
    /** series */
    @Column(length = 64, nullable = false)
    private String series;
    /** token */
    @Column(length = 64, nullable = false)
    private String token;
    /** last_used */
    @Column(nullable = false)
    private java.sql.Timestamp lastUsed;
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
