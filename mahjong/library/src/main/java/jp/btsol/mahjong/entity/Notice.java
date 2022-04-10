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
 * notice table
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {

    /** デフォルトシリアルバージョンID */
    private static final long serialVersionUID = 1L;
    /**  */
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noticeId;
    /**  */
    @Column(length = 200, nullable = false)
    private String title;
    /**  */
    @Column(length = 5000, nullable = false)
    private String detail;
    /**  */
    @Column(nullable = false)
    private java.sql.Date startDate;
    /**  */
    @Column(nullable = true)
    private java.sql.Date endDate;
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
