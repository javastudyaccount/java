package jp.btsol.mahjong.web.fw.config;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        HttpSession session = getSession(request);
        if (session == null) {
            log.error("websocket permission denied");
            return false;
        }
        User user = (User) ((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication()
                .getPrincipal();
        if (user == null) {
            log.error("websocket permission denied");
            return false;
        }
        attributes.put("user", user);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {

    }

    // Refer to httpsessionhandshakeinterceptor
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}