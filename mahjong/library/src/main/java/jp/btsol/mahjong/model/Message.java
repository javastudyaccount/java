package jp.btsol.mahjong.model;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * title
     */
    @NotBlank
    private String title;
    /**
     * detail
     */
    private String detail;
    /**
     * start date
     */
    private Date startDate;
    /**
     * end date
     */
    private Date endDate;
}
