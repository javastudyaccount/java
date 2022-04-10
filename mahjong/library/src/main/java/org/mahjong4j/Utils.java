package org.mahjong4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.StringUtils;

public class Utils {
    /**
     * DELIMITER String
     */
    private static final String DELIMITER = ":";

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

    /**
     * Locates the Spring Security remember me cookie in the request and returns its
     * value. The cookie is searched for by name and also by matching the context
     * path to the cookie path.
     * 
     * @param request    the submitted request which is to be authenticated
     * @param cookieName String
     * @return the cookie value (if present), null otherwise.
     */
    public static String extractCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * Decodes the cookie and splits it into a set of token strings using the ":"
     * delimiter.
     * 
     * @param cookieValue the value obtained from the submitted cookie
     * @return the array of tokens.
     * @throws InvalidCookieException if the cookie was not base64 encoded.
     */
    public static String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        for (int j = 0; j < cookieValue.length() % 4; j++) {
            cookieValue = cookieValue + "=";
        }
        String cookieAsPlainText;
        try {
            cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
        } catch (IllegalArgumentException ex) {
            throw new InvalidCookieException("Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
        }
        String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);
        for (int i = 0; i < tokens.length; i++) {
            try {
                tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException ex) {
//                log.error(ex.getMessage(), ex);
            }
        }
        return tokens;
    }

}
