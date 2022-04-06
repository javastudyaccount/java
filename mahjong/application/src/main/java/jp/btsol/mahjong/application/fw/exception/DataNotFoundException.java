package jp.btsol.mahjong.application.fw.exception;

/**
 * データが見つからない場合にスローする例外。
 */
public class DataNotFoundException extends RuntimeException {
    /**
     * デフォルトシリアルバージョンID
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * DataNotFoundException
     * 
     * @param message String
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
