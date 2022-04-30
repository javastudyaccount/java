package jp.btsol.mahjong.web.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class EnterRoomForm {
    /**
     * room id
     */
    @NotEmpty
    private long roomId;
}
