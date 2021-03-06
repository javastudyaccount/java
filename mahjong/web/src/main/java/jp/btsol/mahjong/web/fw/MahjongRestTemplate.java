package jp.btsol.mahjong.web.fw;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.RequestEntity.HeadersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.MahjongAuthenticationHeader;
import jp.btsol.mahjong.model.MahjongUser;
import jp.btsol.mahjong.web.service.ApplicationProperties;
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
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /** REST連携用 */
    private final RestTemplate restTemplate;
    /**
     * to convert error object
     */
    private final ObjectMapper om;
    /**
     * userContext UserContext
     */
    private final UserContext userContext;

    @Autowired
    public MahjongRestTemplate(RestTemplate restTemplate, //
            ObjectMapper om, //
            ApplicationProperties applicationProperties, //
            UserContext userContext) {
        this.restTemplate = restTemplate;
        this.om = om;
        this.applicationProperties = applicationProperties;
        this.userContext = userContext;
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
        ResponseEntity<R> response = restTemplate.exchange(request, clazz);

        return response.getBody();
    }

    /**
     * 
     * GETでデータの送信を行います。
     * 
     * @param <R>   レスポンス型
     * @param path  送信先
     * @param param Map<String, Object>
     * @param clazz レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <R> R get(String path, Map<String, Object> param, Class<R> clazz) throws RuntimeException {
        URI uri = buildURI(path, param);

        String mahjongUser = mahjongHeader();

        // リクエスト情報の作成
        RequestEntity<?> request = RequestEntity.get(uri).header(X_MAHJONG_USER, mahjongUser).build();
        ResponseEntity<R> response = restTemplate.exchange(uri, HttpMethod.GET, request, clazz);
        return response.getBody();
    }

    private URI buildURI(String path, Map<String, Object> param) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path);
        param.forEach((key, value) -> {
            builder.queryParam(key, value);
        });
        // 文字列をもとにURLを作成（エンコードもする）
        URI uriWithNoEscapedPlus = builder.build().encode().toUri();

        // 「+」を文字置換でエンコードする
        String strictlyEscapedQuery = StringUtils.replace(uriWithNoEscapedPlus.getRawQuery(), "+", "%2B");

        // エンコード後のクエリに置き換える
        URI uri = UriComponentsBuilder.fromUri(uriWithNoEscapedPlus).replaceQuery(strictlyEscapedQuery).build(true)
                .toUri();
        return uri;
    }

    /**
     * POSTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param <R>       レスポンス型
     * @param path      送信先
     * @param parameter ボディパラメータ
     * @param clazz     レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R post(String path, P parameter, Class<R> clazz) throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.post(path)).contentType(MediaType.APPLICATION_JSON)
                .body(parameter);
        ResponseEntity<R> response = restTemplate.exchange(path, HttpMethod.POST, request, clazz);
        return response.getBody();
    }

    /**
     * POSTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param path      送信先
     * @param parameter ボディパラメータ
     * @throws RuntimeException 業務例外
     */
    public <P> void post(String path, P parameter) throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.post(path)).contentType(MediaType.APPLICATION_JSON)
                .body(parameter);
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
        String mahjongUser = mahjongHeader();
        // キーの設定
        headers.add(X_MAHJONG_USER, mahjongUser);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = restTemplate.exchange(path, HttpMethod.POST, requestEntity, clazz);
        return response.getBody();
    }

    /**
     * PUTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param <R>       レスポンス型
     * @param path      送信先
     * @param parameter ボディパラメータ
     * @param clazz     レスポンスのクラス型
     * @return レスポンス
     * @throws RuntimeException 業務例外
     */
    public <P, R> R put(String path, P parameter, Class<R> clazz) throws RuntimeException {
        RequestEntity<P> request = createRequest(path, RequestEntity.put(path)).contentType(MediaType.APPLICATION_JSON)
                .body(parameter);
        ResponseEntity<R> response = restTemplate.exchange(path, HttpMethod.PUT, request, clazz);

        return response.getBody();
    }

    /**
     * PUTでデータの送信を行います。
     * 
     * @param <P>       パラメータ型
     * @param path      送信先
     * @param parameter ボディパラメータ
     * @throws RuntimeException 業務例外
     */
    public <P> void put(String path, P parameter) throws RuntimeException {
        try {
            log.info("param: {}", om.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RequestEntity<P> request = createRequest(path, RequestEntity.put(path)).contentType(MediaType.APPLICATION_JSON)
                .body(parameter);
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
        String mahjongUser = mahjongHeader();
        // キーの設定
        headers.add(X_MAHJONG_USER, mahjongUser);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<R> response = restTemplate.exchange(path, HttpMethod.PUT, requestEntity, clazz);

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
        ResponseEntity<R> response = restTemplate.exchange(request, clazz);

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
        RequestEntity<?> request = createRequest(path, RequestEntity.delete(path))
                .header("request-id", path.replaceAll(".*/", "").replaceAll("\\?.*", "")).build();
        restTemplate.exchange(request, Void.class);
    }

    /**
     * 
     * DELETEでデータの送信を行います。
     * 
     * @param path  送信先
     * @param param Map<String, Object>
     * @throws RuntimeException 業務例外
     */
    public void delete(String path, Map<String, Object> param) throws RuntimeException {
        URI uri = buildURI(path, param);
        RequestEntity<?> request = createRequest(path, RequestEntity.delete(uri))
                .header("request-id", path.replaceAll(".*/", "").replaceAll("\\?.*", "")).build();
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
        String mahjongUser = mahjongHeader();
        header.header(X_MAHJONG_USER, mahjongUser);
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
        String mahjongUser = mahjongHeader();
        // キーの設定
        header.header(X_MAHJONG_USER, mahjongUser);
        header.header("request-id", path.replaceAll(".*/", ""));
        return header;
    }

    private String mahjongHeader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MahjongAuthenticationHeader mahjongHeader = new MahjongAuthenticationHeader();
        mahjongHeader.setSub(applicationProperties.getSub());
        mahjongHeader.setIss(applicationProperties.getIss());
        mahjongHeader.setLoginId(applicationProperties.getLoginId());
        if (Objects.nonNull(authentication) && authentication.getPrincipal() instanceof MahjongUser) {
            String loginId = ((MahjongUser) authentication.getPrincipal()).getUsername();
            mahjongHeader.setLoginId(loginId);
        } else {
            if (!StringUtils.isEmpty(userContext.userId())) {
                mahjongHeader.setLoginId(userContext.userId());
            }
        }
        String mahjongUser = "";
        try {
            mahjongUser = new String(Base64.encodeBase64(om.writeValueAsBytes(mahjongHeader)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mahjongUser;
    }

}
