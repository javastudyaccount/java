package jp.btsol.mahjong.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationProperties {

  /**
   * A url for the application.
   */
  private String uri;
  /**
   * path hands
   */
  private String hands;
  
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

	public String getHands() {
		return hands;
	}
	
	public void setHands(String hands) {
		this.hands = hands;
	}
}