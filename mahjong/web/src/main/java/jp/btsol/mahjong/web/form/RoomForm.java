package jp.btsol.mahjong.web.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class RoomForm {
    /**
     * room name
     */
    @NotEmpty
    @Length(max = 20)
    private String roomName;
}
