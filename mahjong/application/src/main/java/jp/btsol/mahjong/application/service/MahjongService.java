package jp.btsol.mahjong.application.service;

import javax.transaction.Transactional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * MahjongService
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@EnableConfigurationProperties(ServiceProperties.class)
@Transactional
public class MahjongService {
    /**
     * ServiceProperties
     */
    private final ServiceProperties serviceProperties;

    /**
     * Constructor
     * 
     * @param serviceProperties ServiceProperties
     */
    public MahjongService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    /**
     * get message
     * 
     * @return String
     */
    public String message() {
        return this.serviceProperties.getMessage();
    }
}