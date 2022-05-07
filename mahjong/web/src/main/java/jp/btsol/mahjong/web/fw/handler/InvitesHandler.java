package jp.btsol.mahjong.web.fw.handler;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.btsol.mahjong.model.MahjongUser;
import jp.btsol.mahjong.web.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InvitesHandler implements HandlerInterceptor {
    /**
     * playerService
     */
    private final PlayerService playerService;

    /**
     * constructor
     * 
     * @param playerService
     */
    public InvitesHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * check invites
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
        if (Objects.nonNull(handler) && handler instanceof HandlerMethod) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.nonNull(authentication) && authentication.getPrincipal() instanceof MahjongUser) {
                MahjongUser user = (MahjongUser) authentication.getPrincipal();
                long playerId = user.getPlayerId();
                int invites = playerService.getInvites(playerId);
                user.setInvites(invites);
            }
        }
        return true;
    }
}
