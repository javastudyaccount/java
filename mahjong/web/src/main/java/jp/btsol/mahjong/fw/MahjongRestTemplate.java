/**
 * 
 */
package jp.btsol.mahjong.fw;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplate
 * 
 * @author B&T Solutions Inc.
 *
 */
@Component
@Slf4j
public class MahjongRestTemplate {
    /** ヘッダのキー(x-mahjong-user) */
    private static final String X_MAHJONG_USER = "x-mahjong-user";
    /** ヘッダのキー(request-id) */
    private static final String REQUEST_ID = "request-id";

    /** REST連携用 */
    private final RestTemplate restTemplate;
    /**
     * to convert error object
     */
    private final ObjectMapper om;

    @Autowired
    public MahjongRestTemplate(RestTemplate restTemplate, //
            ObjectMapper om) {
        this.restTemplate = restTemplate;
        this.om = om;
    }

    /**
     * 
     * GETでデータの送信を行います。
     * 
     * @param <R>   レスポンス型
     * @param path  送信先
     * @param clazz レスポンスのクラス型
     * @return レスポンス
     */
    public <R> R get(String path, Class<R> clazz) throws RuntimeException {
        return get(path, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * 
     * GETでデータの送信を行います。
     * 
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R get(String path, Class<R> clazz, MahjongRestErrorHandler errorHandler) throws RuntimeException {
        RequestEntity<?> request = createRequest(path, RequestEntity.get(path)).build();
        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(request, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpHeaders headers = e.getResponseHeaders();
            // 404 NOT_FOUNDの場合は、nullに戻ります
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return null;
            }
            String requestId = "";
            if (Objects.nonNull(headers)) {
                List<String> requestIds = headers.get(REQUEST_ID);
                if (Objects.nonNull(requestIds)) {
                    requestId = requestIds.toString();
                }
            }
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();
    }

    /**
     * POSTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param <R>       レスポンス型
     * @param path      送信先
     * @param paramater ボディパラメータ
     * @param clazz     レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R post(String path, P paramater, Class<R> clazz) throws RuntimeException {
        return post(path, paramater, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * POSTでデータの送信を行います。
     * 
     * @param <P>          パラメータ型
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param paramater    ボディパラメータ
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R post(String path, P paramater, Class<R> clazz, MahjongRestErrorHandler errorHandler)
            throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.post(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(path, HttpMethod.POST, request, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();
    }

    /**
     * POSTでファイルデータの送信を行います。
     * 
     * @param <R>   レスポンス型
     * @param path  送信先
     * @param body  ボディパラメータ
     * @param clazz レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R postMultipartFile(String path, MultiValueMap<String, Object> body, Class<R> clazz)
            throws RuntimeException {
        return postMultipartFile(path, body, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * POSTでファイルデータの送信を行います。
     * 
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param body         ボディパラメータ
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R postMultipartFile(String path, MultiValueMap<String, Object> body, Class<R> clazz,
            MahjongRestErrorHandler errorHandler) throws RuntimeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // キーの設定
        headers.add(X_MAHJONG_USER, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(path, HttpMethod.POST, requestEntity, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            headers = e.getResponseHeaders();
            String requestId = "";
            if (Objects.nonNull(headers)) {
                List<String> requestIds = headers.get(REQUEST_ID);
                if (Objects.nonNull(requestIds)) {
                    requestId = requestIds.toString();
                }
            }
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();
    }

    /**
     * PUTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param <R>       レスポンス型
     * @param path      送信先
     * @param paramater ボディパラメータ
     * @param clazz     レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R put(String path, P paramater, Class<R> clazz) throws RuntimeException {
        return put(path, paramater, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * PUTでデータの送信を行います。
     * 
     * @param <P>          パラメータ型
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param paramater    ボディパラメータ
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R put(String path, P paramater, Class<R> clazz, MahjongRestErrorHandler errorHandler)
            throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.put(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(path, HttpMethod.PUT, request, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpHeaders headers = e.getResponseHeaders();
            String requestId = "";
            if (Objects.nonNull(headers)) {
                List<String> requestIds = headers.get(REQUEST_ID);
                if (Objects.nonNull(requestIds)) {
                    requestId = requestIds.toString();
                }
            }
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();
    }

    /**
     * PUTでファイルデータの送信を行います。
     * 
     * @param <R>   レスポンス型
     * @param path  送信先
     * @param body  ボディパラメータ
     * @param clazz レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R putMultipartFile(String path, MultiValueMap<String, Object> body, Class<R> clazz)
            throws RuntimeException {
        return putMultipartFile(path, body, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * PUTでファイルデータの送信を行います。
     * 
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param body         ボディパラメータ
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R putMultipartFile(String path, MultiValueMap<String, Object> body, Class<R> clazz,
            MahjongRestErrorHandler errorHandler) throws RuntimeException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // キーの設定
        headers.add(X_MAHJONG_USER, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(path, HttpMethod.PUT, requestEntity, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            headers = e.getResponseHeaders();
            String requestId = "";
            if (Objects.nonNull(headers)) {
                List<String> requestIds = headers.get(REQUEST_ID);
                if (Objects.nonNull(requestIds)) {
                    requestId = requestIds.toString();
                }
            }
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();
    }

    /**
     * 
     * DELETEでデータの送信を行います。
     * 
     * @param <R>   レスポンス型
     * @param path  送信先
     * @param clazz レスポンスのクラス型
     * @return レスポンス
     */
    public <R> R delete(String path, Class<R> clazz) throws RuntimeException {
        return delete(path, clazz, (status, e) -> {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        });
    }

    /**
     * 
     * DELETEでデータの送信を行います。
     * 
     * @param <R>          レスポンス型
     * @param path         送信先
     * @param clazz        レスポンスのクラス型
     * @param errorHandler エラーレスポンス時のハンドラ
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R delete(String path, Class<R> clazz, MahjongRestErrorHandler errorHandler) throws RuntimeException {
        RequestEntity<?> request = createRequest(path, RequestEntity.delete(path)).build();
        ResponseEntity<R> response = null;
        try {
            response = restTemplate.exchange(request, clazz);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpHeaders headers = e.getResponseHeaders();
            String requestId = "";
            if (Objects.nonNull(headers)) {
                List<String> requestIds = headers.get(REQUEST_ID);
                if (Objects.nonNull(requestIds)) {
                    requestId = requestIds.toString();
                }
            }
            errorHandler.handle(e.getStatusCode().value(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }

        return response.getBody();

    }

    /**
     * 共通ヘッダを設定します。
     * 
     * @param path   パス
     * @param header リクエストヘッダ
     * @return header リクエストヘッダ
     */
    private HeadersBuilder<?> createRequest(String path, HeadersBuilder<?> header) {
        header.header(X_MAHJONG_USER,
                "eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=");
        return header;
    }

    /**
     * 共通ヘッダを設定します。
     * 
     * @param path   パス
     * @param header リクエストヘッダ
     * @return header リクエストヘッダ
     */
    private BodyBuilder createRequest(String path, BodyBuilder header) {
        // キーの設定
        header.header(X_MAHJONG_USER,
                "eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=");
        header.header("request-id", path.replaceAll(".*/", ""));
        return header;
    }

}
