package jp.btsol.mahjong.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@EnableConfigurationProperties(ApplicationProperties.class)
public class HomeService {
	private final ApplicationProperties applicationProperties;
	public HomeService(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	public String getHomeMessage() {
        RestTemplate rest = new RestTemplate();

        final String endpoint = applicationProperties.getUri();//"http://localhost:8088";

        final String url = endpoint;

        ResponseEntity<String> response = rest.getForEntity(url, String.class);

        String message = response.getBody();

        return decode(message);

	}
    // いわゆる日本語の２バイト文字がunicodeエスケープされてるので解除。
    private static String decode(String string) {
        return StringEscapeUtils.unescapeJava(string);  
    }
}
