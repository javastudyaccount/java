package jp.btsol.mahjong.entity;

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
}
