package jp.btsol.mahjong.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import jp.btsol.mahjong.fw.token.XMahjongUser;

public class Utils {
    /**
     * 大文字英数字
     */
    private static final String AZ09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * チームコード自動採番
     * 
     * @param teamCodes List<String> キャンペーンID内既存のチームコード一覧
     * @return チームコード（大文字英数字10桁、キャンペーンID内一意）
     */
    public static String makeTeamCode(List<String> teamCodes) {
        String code = random10();
        while (Objects.nonNull(teamCodes) && teamCodes.contains(code)) {
            code = random10();
        }
        return code;
    }

    /**
     * 10桁の大文字英数字文字列を作成する
     * 
     * @return 10桁の大文字英数字文字列
     */
    private static String random10() {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomValue = random.nextInt(AZ09.length());
            code += AZ09.charAt(randomValue);
        }
        return code;
    }

    /**
     * x-mahjong-userをパースして返却します。
     * 
     * @param xMahjongUserNoDec Base64デコード前のx-mahjong-userの値
     * @return xMahjongUserのクラス
     * @throws JSONException
     */
    public static XMahjongUser parseXMahjongUser(String xMahjongUserNoDec) throws JSONException {
        String xMahjongUserJsonStr = new String(Base64.getUrlDecoder().decode(xMahjongUserNoDec),
                StandardCharsets.UTF_8);
        if (Objects.nonNull(xMahjongUserJsonStr)) {
            JSONObject xMahjongUserJson = new JSONObject(xMahjongUserJsonStr);
            XMahjongUser token = new XMahjongUser();
            token.setIss(xMahjongUserJson.getString("iss"));
            token.setSub(xMahjongUserJson.getString("sub"));
            if (xMahjongUserJson.has("username")) {
                token.setUsername(xMahjongUserJson.getString("username"));
            }
            if (xMahjongUserJson.has("bizGroup")) {
                token.setBizGroup(xMahjongUserJson.getString("bizGroup"));
            }
            if (xMahjongUserJson.has("customParam")) {
                token.setCustomParam(xMahjongUserJson.getString("customParam"));
            }
            return token;
        }
        return null;
    }
}
