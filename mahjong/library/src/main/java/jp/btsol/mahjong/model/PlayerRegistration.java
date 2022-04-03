package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRegistration {
    /**
     * loginId
     */
//    @NotEmpty
//    @Length(min = 8, max = 8)
    @Pattern(regexp = "[a-zA-Z_0-9]{8,8}", message = "{majhong.loginid.pattern.error}")
    private String loginId;
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
