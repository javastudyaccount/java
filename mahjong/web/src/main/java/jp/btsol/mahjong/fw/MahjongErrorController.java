package jp.btsol.mahjong.fw;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * アプリケーション全体のエラーコントローラー
 */
@Controller
public class MahjongErrorController implements ErrorController {
    /**
     * system-error page
     * 
     * @return String
     */
    @RequestMapping("/system-error")
    public String error() {
        return "system-error";
    }
}
