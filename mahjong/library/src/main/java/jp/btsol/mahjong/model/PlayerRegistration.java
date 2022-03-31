package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PlayerRegistration {
    /**
     * nickname
     */
    @NotBlank
    @Size(max = 20)
    private String nickname;
    /**
     * password
     */
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
