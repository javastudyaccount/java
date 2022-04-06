package jp.btsol.mahjong.application.fw.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.btsol.mahjong.application.fw.exception.BadRequestException;
import jp.btsol.mahjong.application.fw.exception.DataNotFoundException;
import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.entity.ErrorDataEntity;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorDetail.setErrorDetail(ex.getLocalizedMessage());
        errorDetail.setPath(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                .getServletPath());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    protected ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetail.setErrorDetail(ex.getLocalizedMessage());
        errorDetail.setPath(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                .getServletPath());
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataNotFoundException.class})
    protected ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetail.setErrorDetail(ex.getLocalizedMessage());
        errorDetail.setPath(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                .getServletPath());
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorDetail.setErrorDetail(ex.getLocalizedMessage());
        errorDetail.setPath(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                .getServletPath());
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorDetail.setErrorDetail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        errorDetail.setPath(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                .getServletPath());
        // path is "" for JUNIT
        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.NOT_FOUND.toString());
        errorDetail.setErrorDetail("Page not found.");
        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
        errorDetail.setErrorDetail("Method not supported.");
        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDataEntity errorDetail = new ErrorDataEntity();
        errorDetail.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        errorDetail.setErrorDetail("Message not readable.");
        errorDetail.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//        errorDetail.setBindingResult(ex.getBindingResult());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
