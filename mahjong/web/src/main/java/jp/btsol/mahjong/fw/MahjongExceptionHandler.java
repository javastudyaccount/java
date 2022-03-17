package jp.btsol.mahjong.fw;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * コントローラ全体でのExceptionハンドラー
 */
@ControllerAdvice
public class MahjongExceptionHandler {
    /**
     * Exceptionハンドラー
     * 
     * @param ex Exception
     * @return String
     */
    @ExceptionHandler(Exception.class)
    public String handleWebClientResponseException(Exception ex) {
        return "redirect:/system-error";
    }
}
