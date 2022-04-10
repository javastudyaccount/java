package jp.btsol.mahjong.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import jp.btsol.mahjong.application.service.MahjongNoticeService;

@SpringBootTest("service.message=Hello")
public class MahjongNoticeServiceTest {

  @Autowired
  private MahjongNoticeService myService;

  @Test
  public void contextLoads() {
    assertThat(myService.messages()).isNotNull();
  }

  @SpringBootApplication
  static class TestConfiguration {
  }

}