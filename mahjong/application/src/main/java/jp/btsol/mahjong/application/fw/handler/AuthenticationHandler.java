package jp.btsol.mahjong.application.fw.handler;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mahjong4j.Utils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.btsol.mahjong.application.service.PlayerService;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.MahjongAuthenticationHeader;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationHandler implements HandlerInterceptor {
    /** ヘッダのキー(x-mahjong-user) */
    private static final String X_MAHJONG_USER = "x-mahjong-user";
    /**
     * userContext アプリユーザー情報コンテキスト
     */
    private final UserContext userContext;
    /**
     * playerService
     */
    private final PlayerService playerService;

    /**
     * constructor
     * 
     * @param userContext
     * @param playerService
     */
    public AuthenticationHandler(UserContext userContext, //
            PlayerService playerService) {
        this.userContext = userContext;
        this.playerService = playerService;
    }

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
                if (request.getRequestURI().contains("/authentication")) {
                    return true;
                }
                String mahjongHeaderStr = request.getHeader(X_MAHJONG_USER);
                if (Objects.isNull(mahjongHeaderStr)) {
                    log.error(X_MAHJONG_USER + "ヘッダが認識できませんでした。");
                    throw new PreAuthenticatedCredentialsNotFoundException(X_MAHJONG_USER + "ヘッダが認識できませんでした。");
                }
                try {
                    MahjongAuthenticationHeader header = Utils.parseXMahjongUser(mahjongHeaderStr);
                    log.info("sub:{} , iss:{}, loginId:{}, url:{} , method:{}", header.getSub(), header.getIss(),
                            header.getLoginId(), request.getServletPath(), request.getMethod());
                    this.userContext.userId(header.getLoginId());
                    if (Objects.nonNull(header.getLoginId())) {
                        Player player = playerService.getPlayer(header.getLoginId());
                        this.userContext.playerId(player.getPlayerId());
                    }
                    return true;
                } catch (JSONException e) {
                    log.error("パースに失敗しました。：" + mahjongHeaderStr);
                    throw new PreAuthenticatedCredentialsNotFoundException(
                            X_MAHJONG_USER + "ヘッダが認識できませんでした。[" + mahjongHeaderStr + "]", e);
                }
            }
            log.error("リクエストがNULLのため認証できませんでした。");
            throw new PreAuthenticatedCredentialsNotFoundException("リクエストがNULLのため認証できませんでした。");
        }
        return true;
    }
}
