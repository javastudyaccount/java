package jp.btsol.mahjong.application.fw.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.btsol.mahjong.application.fw.exception.BadRequestException;
import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
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
    private ErrorDataEntity createErrorResponseBody(Exception exception, WebRequest request) {
        ErrorDataEntity errorData = new ErrorDataEntity();
        errorData.setErrorDetail(exception.getLocalizedMessage());
        errorData.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorData.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        return errorData;
    }

    @ExceptionHandler({PreAuthenticatedCredentialsNotFoundException.class})
    protected ResponseEntity<Object> handlePreAuthenticatedCredentialsNotFoundException(
            PreAuthenticatedCredentialsNotFoundException ex) {
        ErrorDataEntity errorDetails = new ErrorDataEntity();
        errorDetails.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorDetails.setErrorDetail(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    protected ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorDataEntity errorDetails = new ErrorDataEntity();
        errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetails.setErrorDetail(ex.getLocalizedMessage());
        errorDetails.setPath(ex.getPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorDataEntity errorDetails = new ErrorDataEntity();
        errorDetails.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetails.setErrorDetail(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler({NoHandlerFoundException.class})
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
//        ErrorDataEntity errorDetails = new ErrorDataEntity();
//        errorDetails.setErrorCode(HttpStatus.NOT_FOUND.toString());
//        errorDetails.setErrorDetail(ex.getLocalizedMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorDetail.setErrorDetail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.NOT_FOUND.toString());
//        errorDetail.setErrorDetail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}
