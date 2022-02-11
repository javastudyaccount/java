package jp.btsol.mahjong.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Home service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@EnableConfigurationProperties(ApplicationProperties.class)
public class HomeService {
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Contrunctor
     * 
     * @param applicationProperties ApplicationProperties application properties
     */
    public HomeService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * get home message
     * 
     * @return String
     */
    public String getHomeMessage() {
        RestTemplate rest = new RestTemplate();

        final String endpoint = applicationProperties.getUri();

        final String url = endpoint;

        ResponseEntity<String> response = rest.getForEntity(url, String.class);

        String message = response.getBody();

        return decode(message);

    }

    /**
     * unescape string encoded with unicode
     * 
     * @param string unicode
     * @return String
     */
    private static String decode(String string) {
        return StringEscapeUtils.unescapeJava(string);
    }
}
