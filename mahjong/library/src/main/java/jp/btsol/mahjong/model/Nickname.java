package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nickname {
    /**
     * nickname
     */
    @NotBlank
    @Size(max = 20)
    private String nickname;
}
