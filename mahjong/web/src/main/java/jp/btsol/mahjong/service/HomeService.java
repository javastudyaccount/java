package jp.btsol.mahjong.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class HomeService {
	public String getHomeMessage() {

        RestTemplate rest = new RestTemplate();

        final String endpoint = "http://localhost:8088";

        final String url = endpoint;

        // 直接Beanクラスにマップ出来るけど今回はめんどくさいのでStringで。
        ResponseEntity<String> response = rest.getForEntity(url, String.class);

        String message = response.getBody();

        return decode(message);

	}
    // いわゆる日本語の２バイト文字がunicodeエスケープされてるので解除。
    private static String decode(String string) {
        return StringEscapeUtils.unescapeJava(string);  
    }
}
