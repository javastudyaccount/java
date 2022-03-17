package jp.btsol.mahjong.fw;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * コントローラ全体でのExceptionハンドラー
 */
@ControllerAdvice
public class MahjongExceptionHandler {
    /**
     * Exceptionハンドラー
     * 
     * @param redirectAttributes RedirectAttributes
     * @param ex                 Exception
     * @return String
     */
    @ExceptionHandler(Exception.class)
    public String handleWebClientResponseException(RedirectAttributes redirectAttributes, Exception ex) {
        redirectAttributes.addAttribute("error", ex.getLocalizedMessage());
        return "redirect:/system-error";
    }
}
