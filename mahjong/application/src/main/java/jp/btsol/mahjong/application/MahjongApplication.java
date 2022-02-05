package jp.btsol.mahjong.application;

import jp.btsol.mahjong.service.MahjongService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "jp.btsol.mahjong")
@RestController
public class MahjongApplication {

  private final MahjongService myService;

  public MahjongApplication(MahjongService myService) {
    this.myService = myService;
  }

  @GetMapping("/")
  public String home() {
    return myService.message();
  }

  public static void main(String[] args) {
    SpringApplication.run(MahjongApplication.class, args);
  }
}