package jp.btsol.mahjong.web.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PlayerForm {
    /**
     * nickname
     */
    @NotEmpty
    @Size(max = 20)
    private String nickname;
}
