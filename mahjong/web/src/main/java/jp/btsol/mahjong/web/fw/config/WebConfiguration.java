package jp.btsol.mahjong.web.fw.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jp.btsol.mahjong.web.fw.RequestLoggingFilter;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * アプリ向けAPIのベースパス
     */
    @Value("${mahjong.base-path:/v1}")
    private String majhongBasePath;
    /**
     * handlerInterceptorリスト
     */
    @Autowired
    private List<HandlerInterceptor> handlerInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        handlerInterceptors.forEach(registry::addInterceptor);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter(majhongBasePath);
    }
}
