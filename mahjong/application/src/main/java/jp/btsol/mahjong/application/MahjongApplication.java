package jp.btsol.mahjong.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.MahjongService;

/**
 * Mahjong application
 * 
 * @author B&T Solutions Inc.
 *
 */
@SpringBootApplication(scanBasePackages = "jp.btsol.mahjong")
@RestController
public class MahjongApplication {
    /**
     * service
     */
    private final MahjongService mahjongService;

    /**
     * Constructor
     * 
     * @param mahjongService MahjongService
     */
    public MahjongApplication(MahjongService mahjongService) {
        this.mahjongService = mahjongService;
    }

    /**
     * home
     * 
     * @return String
     */
    @GetMapping("/")
    public String home() {
        return mahjongService.message();
    }

    /**
     * main(entry point)
     * 
     * @param args String[]
     */
    public static void main(String[] args) {
        SpringApplication.run(MahjongApplication.class, args);
    }
}