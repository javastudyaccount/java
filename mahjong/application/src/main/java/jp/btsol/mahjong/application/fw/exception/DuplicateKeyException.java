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
     * path
     */
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
