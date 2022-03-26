package jp.btsol.mahjong.entity;

import org.springframework.validation.BindingResult;

import lombok.Data;

@Data
public class ErrorDataEntity {
    /**
     * エラーコード.
     *
     */
    private String errorCode;

    /**
     * エラー詳細.
     *
     */
    private String errorDetail;

    /**
     * path
     */
    private String path;
    /**
     * bindingResult
     */
    private BindingResult bindingResult;
}
