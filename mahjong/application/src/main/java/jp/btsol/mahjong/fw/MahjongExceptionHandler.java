package jp.btsol.mahjong.fw;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.btsol.mahjong.entity.ErrorDataEntity;

@RestControllerAdvice
public class MahjongExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorDataEntity errorDetails = new ErrorDataEntity();
        errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetails.setErrorDetail(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
