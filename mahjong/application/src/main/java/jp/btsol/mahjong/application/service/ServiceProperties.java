package jp.btsol.mahjong.application.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * service properties
 * 
 * @author B&T Solutions Inc.
 *
 */
@ConfigurationProperties("service")
@Data
public class ServiceProperties {

    /**
     * A message for the service.
     */
    private String message;
}