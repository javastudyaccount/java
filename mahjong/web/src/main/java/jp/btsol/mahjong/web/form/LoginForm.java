package jp.btsol.mahjong.web.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class LoginForm {
    /**
     * loginId
     */
//    @NotEmpty
//    @Length(min = 8, max = 8)
    @Pattern(regexp = "[a-zA-Z_0-9]{8,8}", message = "{majhong.loginid.pattern.error}")
    private String loginId;
    /**
     * password
     */
//    @NotEmpty
    @Length(min = 8, max = 16)
    private String password;
}
