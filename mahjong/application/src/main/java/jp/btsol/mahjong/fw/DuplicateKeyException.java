package jp.btsol.mahjong.fw;

/**
 * データが重複エラーがあった場合にスローする例外。
 */
public class DuplicateKeyException extends RuntimeException {
    /**
     * デフォルトシリアルバージョンID
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
