package jp.btsol.mahjong.application.fw.exception;

/**
 * データが重複エラーがあった場合にスローする例外。
 */
public class DuplicateKeyException extends RuntimeException {
    /**
     * デフォルトシリアルバージョンID
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * DuplicateKeyException
     * 
     * @param message String
     */
    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
