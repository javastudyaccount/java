package jp.btsol.mahjong.application.fw.exception;

/**
 * players more than 4 in a game
 */
public class TooManyPlayersException extends RuntimeException {
    /**
     * デフォルトシリアルバージョンID
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * TooManyPlayersException
     * 
     * @param message String
     */
    public TooManyPlayersException(String message) {
        super(message);
    }

    public TooManyPlayersException(String message, Throwable cause) {
        super(message, cause);
    }
}
