/**
 * 
 */
package jp.btsol.mahjong.web.fw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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
     * @throws RuntimeException 業務例外
     */
    public <R> R get(String path, Class<R> clazz) throws RuntimeException {
        RequestEntity<?> request = createRequest(path, RequestEntity.get(path)).build();
        ResponseEntity<R> response = null;
        response = restTemplate.exchange(request, clazz);

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
        RequestEntity<P> request = createRequest(path, RequestEntity.post(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        ResponseEntity<R> response = null;
        response = restTemplate.exchange(path, HttpMethod.POST, request, clazz);
        return response.getBody();
    }

    /**
     * POSTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param path      送信先
     * @param paramater ボディパラメータ
     * @throws RuntimeException 業務例外
     */
    public <P> void post(String path, P paramater) throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.post(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        restTemplate.exchange(path, HttpMethod.POST, request, Void.class);
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // キーの設定
        headers.add(X_MAHJONG_USER, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = null;
        response = restTemplate.exchange(path, HttpMethod.POST, requestEntity, clazz);
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
        RequestEntity<P> request = createRequest(path, RequestEntity.put(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        ResponseEntity<R> response = null;
        response = restTemplate.exchange(path, HttpMethod.PUT, request, clazz);

        return response.getBody();
    }

    /**
     * PUTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param path      送信先
     * @param paramater ボディパラメータ
     * @throws RuntimeException 業務例外
     */
    public <P> void put(String path, P paramater) throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.put(path)).contentType(MediaType.APPLICATION_JSON)
                .body(paramater);
        restTemplate.exchange(path, HttpMethod.PUT, request, Void.class);
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // キーの設定
        headers.add(X_MAHJONG_USER, "");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = null;
        response = restTemplate.exchange(path, HttpMethod.PUT, requestEntity, clazz);

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
     * @throws RuntimeException 業務例外
     */
    public <R> R delete(String path, Class<R> clazz) throws RuntimeException {
        RequestEntity<?> request = createRequest(path, RequestEntity.delete(path)).build();
        ResponseEntity<R> response = null;
        response = restTemplate.exchange(request, clazz);

        return response.getBody();
    }

    /**
     * 
     * DELETEでデータの送信を行います。
     * 
     * @param path 送信先
     * @throws RuntimeException 業務例外
     */
    public void delete(String path) throws RuntimeException {
        RequestEntity<?> request = createRequest(path, RequestEntity.delete(path)).build();
        restTemplate.exchange(request, Void.class);
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
