package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Nickname {
    /**
     * nickname
     */
    @NotBlank
    private String nickname;
}
