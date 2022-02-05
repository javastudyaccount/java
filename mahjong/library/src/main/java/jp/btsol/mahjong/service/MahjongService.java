package jp.btsol.mahjong.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(ServiceProperties.class)
public class MahjongService {

  private final ServiceProperties serviceProperties;

  public MahjongService(ServiceProperties serviceProperties) {
    this.serviceProperties = serviceProperties;
  }

  public String message() {
    return this.serviceProperties.getMessages();
  }
}