package jp.btsol.mahjong.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MahjongAuthenticationHeader {
    /**
     * iss
     */
    private String iss;
    /**
     * sub
     */
    private String sub;
    /**
     * loginId
     */
    private String loginId;
    /**
     * customParam
     */
    private Map<String, Object> customParam;
}
