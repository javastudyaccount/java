package jp.btsol.mahjong.service;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.fw.MahjongRestTemplate;

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
     * MahjongRestTemplate
     */
    private final MahjongRestTemplate restTemplate;

    /**
     * Contrunctor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param restTemplate          MahjongRestTemplate
     */
    public HomeService(ApplicationProperties applicationProperties, MahjongRestTemplate restTemplate) {
        this.applicationProperties = applicationProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * get home message
     * 
     * @return String
     */
    public String getHomeMessage() {
        String path = applicationProperties.getUri() + applicationProperties.getPath().getHome();
        return restTemplate.get(path, String.class);
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
