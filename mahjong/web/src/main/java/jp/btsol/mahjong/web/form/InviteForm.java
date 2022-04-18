package jp.btsol.mahjong.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class InviteForm {
    /**
     * inviteChecks
     */
    @NotNull
    @Size(min = 1, message = "{custom.validation.constraints.SelectSize.message}")
    private String[] inviteChecks;
}
