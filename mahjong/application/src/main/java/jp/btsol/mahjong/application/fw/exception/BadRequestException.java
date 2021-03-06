package jp.btsol.mahjong.application.fw.exception;

/**
 * リクエストデータに不備があった場合にスローする例外。
 */
public class BadRequestException extends RuntimeException {

    /**
     * デフォルトシリアルバージョンID
     * 
     */
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
