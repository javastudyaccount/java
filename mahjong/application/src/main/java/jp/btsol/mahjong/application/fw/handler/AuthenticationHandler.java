package jp.btsol.mahjong.application.fw.handler;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.btsol.mahjong.application.fw.token.XMahjongUser;
import jp.btsol.mahjong.application.utils.Utils;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationHandler implements HandlerInterceptor {
    /** ヘッダのキー(x-mahjong-user) */
    private static final String X_MAHJONG_USER = "x-mahjong-user";

    /**
     * check x-mahjong-user header
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @param handler  Handler
     * @return boolean true for success, false for error
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            if (Objects.nonNull(request)) {
                String mahjongUserStr = request.getHeader(X_MAHJONG_USER);
                if (Objects.isNull(mahjongUserStr)) {
                    log.error(X_MAHJONG_USER + "ヘッダが認識できませんでした。");
                    throw new PreAuthenticatedCredentialsNotFoundException(X_MAHJONG_USER + "ヘッダが認識できませんでした。");
                }
                try {
                    XMahjongUser mahjongUser = Utils.parseXMahjongUser(mahjongUserStr);
                    log.info("sub:{} , url:{} , method:{}", mahjongUser.getSub(), request.getServletPath(),
                            request.getMethod());
                    return true;
                } catch (JSONException e) {
                    log.error("パースに失敗しました。：" + mahjongUserStr);
                    throw new PreAuthenticatedCredentialsNotFoundException(
                            X_MAHJONG_USER + "ヘッダが認識できませんでした。[" + mahjongUserStr + "]", e);
                }
            }
            log.error("リクエストがNULLのため認証できませんでした。");
            throw new PreAuthenticatedCredentialsNotFoundException("リクエストがNULLのため認証できませんでした。");
        }
        return true;
    }
}
