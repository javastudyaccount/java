package jp.btsol.mahjong.fw;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.btsol.mahjong.entity.ErrorDataEntity;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MahjongExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request) {
        log.info(String.format("BadRequestException %s", exception.getLocalizedMessage()));
        HttpHeaders headers = new HttpHeaders();

        return super.handleExceptionInternal(exception, createErrorResponseBody(exception, request), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    // レスポンスのボディ部を作成
    private ErrorDataEntity createErrorResponseBody(BadRequestException exception, WebRequest request) {
        ErrorDataEntity errorData = new ErrorDataEntity();
        errorData.setErrorDetail(exception.getLocalizedMessage());
        errorData.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errorData;
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorDataEntity errorDetails = new ErrorDataEntity();
        errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetails.setErrorDetail(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
