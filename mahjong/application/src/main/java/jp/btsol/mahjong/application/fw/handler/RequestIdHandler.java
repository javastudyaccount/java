package jp.btsol.mahjong.application.fw.handler;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.btsol.mahjong.application.fw.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestIdHandler implements HandlerInterceptor {
    /** ヘッダのキー(request-id) */
    private static final String REQUEST_ID = "request-id";

    /**
     * check request-id header
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
                if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")
                        || request.getMethod().equals("DELETE")) {
                    String requestId = request.getHeader(REQUEST_ID);
                    if (Objects.isNull(requestId)) {
                        log.error(REQUEST_ID + "ヘッダが認識できませんでした。");
                        throw new BadRequestException(REQUEST_ID + "ヘッダが認識できませんでした。");
                    }
                    RequestContextHolder.currentRequestAttributes().setAttribute(REQUEST_ID, requestId,
                            RequestAttributes.SCOPE_REQUEST);
                    return true;
                }
                return true;
            }
            log.error("リクエストがNULLのため認証できませんでした。");
            throw new BadRequestException("リクエストがNULLのため認証できませんでした。");
        }
        return true;
    }
}
