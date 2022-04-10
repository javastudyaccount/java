package jp.btsol.mahjong.application;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.btsol.mahjong.application.service.MahjongNoticeService;
import jp.btsol.mahjong.model.Message;

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
    private final MahjongNoticeService mahjongNoticeService;

    /**
     * Constructor
     * 
     * @param mahjongNoticeService MahjongNoticeService
     */
    public MahjongApplication(MahjongNoticeService mahjongNoticeService) {
        this.mahjongNoticeService = mahjongNoticeService;
    }

    /**
     * home
     * 
     * @return String
     */
    @GetMapping("/")
    public List<Message> messages() {
        return mahjongNoticeService.messages();
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