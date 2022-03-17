package jp.btsol.mahjong.application.fw.token;

import lombok.Data;

@Data
public class XMahjongUser {
    /**
     * クライアントID
     * application.ymlのspring.security.oauth2.client.registration.bpf-auth.client-idの設定値
     */
    private String clientId;
    /** イシュア */
    private String iss;
    /** subクレーム */
    private String sub;
    /** ユーザ名 */
    private String username;
    /** bizGroup */
    private String bizGroup;
    /** カスタムパラメータ */
    private String customParam;
}
