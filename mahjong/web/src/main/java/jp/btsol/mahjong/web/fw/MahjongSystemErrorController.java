package jp.btsol.mahjong.web.fw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * アプリケーション全体のエラーコントローラー //
 */
@Controller
public class MahjongSystemErrorController {

    /**
     * HTML レスポンス用の ModelAndView オブジェクトを返す。
     *
     * @return String template name
     */
    @GetMapping("/system-error")
    public String mySystemErrorHtml() {
        return "system-error";
    }
}
