package org.mahjong4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Utils {
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

    /**
     * Implementing Fisher–Yates shuffle
     * 
     * @param ar array of integers
     */
    public static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
