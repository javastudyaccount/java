/**
 * 
 */
package jp.btsol.mahjong.web.fw.handler;

import org.springframework.web.client.HttpStatusCodeException;

/**
 * 
 * Rest通信のエラーをカスタマイズするためのハンドラ
 * 
 * @author B&T Solutions Inc.
 *
 */
public interface MahjongRestErrorHandler {

    /**
     * 例外をハンドルします。
     * 
     * @param statusCode ステータスコード
     * @param e          例外
     * @throws RuntimeException 業務例外
     */
    void handle(int statusCode, HttpStatusCodeException e) throws RuntimeException;

}
