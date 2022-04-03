package jp.btsol.mahjong.application.service;

import javax.transaction.Transactional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.model.Message;

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
    public Message message() {
        return new Message(this.serviceProperties.getMessage());
    }
}