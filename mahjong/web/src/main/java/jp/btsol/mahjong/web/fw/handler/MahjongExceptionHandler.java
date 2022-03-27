package jp.btsol.mahjong.web.fw.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.entity.ErrorDataEntity;

/**
 * コントローラ全体でのExceptionハンドラー
 */
//@ControllerAdvice
public class MahjongExceptionHandler {
    /**
     * ObjectMapper
     */
    @Autowired
    private ObjectMapper om;

    /**
     * Exceptionハンドラー
     * 
     * @param redirectAttributes RedirectAttributes
     * @param ex                 Exception
     * @return String
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public String handleHttpServerErrorException(RedirectAttributes redirectAttributes, HttpServerErrorException ex) {
        try {
            ErrorDataEntity error = om.readValue(ex.getResponseBodyAsString(), ErrorDataEntity.class);
            redirectAttributes.addFlashAttribute("systemError", error.getErrorDetail());
            redirectAttributes.addFlashAttribute("status", error.getErrorCode());
            redirectAttributes.addFlashAttribute("timestamp", new Date());
            redirectAttributes.addFlashAttribute("path", error.getPath());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("systemError", e.getLocalizedMessage());
        }
        return "redirect:/system-error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected String handleMethodArgumentNotValid(RedirectAttributes redirectAttributes, HttpServerErrorException ex) {
        return "redirect:/system-error";
    }
}
