package jp.btsol.mahjong.application.fw.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogHandler implements HandlerInterceptor {
    /**
     * log all request
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
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("Class name: " + hm.getMethod().getDeclaringClass().getName() + " Method name: "
                    + hm.getMethod().getName());
        }
        return true;
    }
}
