package jp.btsol.mahjong.web.fw.interceptor;

import java.lang.annotation.Annotation;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
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
            if (Objects.nonNull(hm.getMethod().getAnnotation(PostMapping.class))) {
                Class formClazz = hm.getMethod().getParameterTypes()[0];
                request.getSession().setAttribute("formClazz", formClazz);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Annotation anno = method.getMethod().getAnnotation(GetMapping.class);
            if (Objects.nonNull(anno)) {
//                String viewName = ((GetMapping) anno).value()[0];
                // replace path variable
//                request.getSession().setAttribute("viewName", viewName);

                request.getSession().setAttribute("viewName", request.getRequestURI());
            } else {
                request.getSession().removeAttribute("viewName");
            }
        }
    }

}
