package jp.btsol.mahjong.web.fw.filter;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {

    /**
     * リクエストIDを格納するためのキー文字列
     */
    private static final String REQUEST_ID_KEY = "X-Request-Id";

    /**
     * 表示対象とするヘッダーのリスト(LowerCaseで記載)
     */
    private static final List<String> HEADER_ALLOW_LIST = List.of("x-forwarded-for", "x-forwarded-proto",
            "x-forwarded-port", "host", "user-agent", "app-user"
    // test用で呼び出していないか念のため
    );

    /**
     * ベースPath
     */
    private final String[] basePath;

    public RequestLoggingFilter(String... basePath) {
        this.setIncludeClientInfo(false);
        this.setIncludeQueryString(true);
        this.setIncludeHeaders(true);
        this.setIncludePayload(false);
        this.setMaxPayloadLength(1024);
        this.setHeaderPredicate(target -> HEADER_ALLOW_LIST.contains(target));
        this.basePath = basePath;
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
//        for (String path : this.basePath) {
//            if (request.getRequestURI().startsWith(path)) {
//                return true;
//            }
//        }
//
//        // apiのアクセスログ以外、ヘルスチェックエンドポイントは無視
//        return false;
        return true;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID_KEY, requestId);
//        if (this.logger.isInfoEnabled() && !request.getServletPath().startsWith("/actuator/")) {
//            this.logger.info(message);
//        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
//        if (this.logger.isInfoEnabled() && !request.getServletPath().startsWith("/actuator/")) {
//            this.logger.info(message);
//        }
        MDC.remove(REQUEST_ID_KEY);
    }
}
