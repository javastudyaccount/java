package jp.btsol.mahjong.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import jp.btsol.mahjong.application.service.MahjongService;

@SpringBootTest("service.message=Hello")
public class MahjongServiceTest {

  @Autowired
  private MahjongService myService;

  @Test
  public void contextLoads() {
    assertThat(myService.message()).isNotNull();
  }

  @SpringBootApplication
  static class TestConfiguration {
  }

}