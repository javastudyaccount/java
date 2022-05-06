package jp.btsol.mahjong.fw;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * システム内で共通して参照するユーザー情報のユーティリティクラス。
 * <p>
 * インスタンス変数のスレッドローカルにユーザーIDを格納します。
 */
@Component
@Scope("singleton")
public class UserContext {

    /**
     * XXX スレッドのグローバル変数。
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
    /**
     * HOLDER_PLAYER_ID
     */
    private static final ThreadLocal<Long> HOLDER_PLAYER_ID = new ThreadLocal<>();

    /**
     * 認証に成功していればユーザ情報のキーを返します。 認証していなければ空文字列を返します。
     *
     * @return ユーザ情報のキー
     */
    public String userId() {
        String userId = HOLDER.get();

        if (userId == null) {
            return "";
        }

        return userId.toString();
    }

    /**
     * 認証に成功していればユーザ情報のキーを返します。 認証していなければ空文字列を返します。
     *
     * @return ユーザ情報のキー
     */
    public long playerId() {
        Long playerId = HOLDER_PLAYER_ID.get();

        if (playerId == null) {
            return -1;
        }

        return playerId;
    }

    /**
     * ユーザ情報のキーを登録します。
     *
     * @param userId ユーザ情報のキー
     */
    public void userId(@NotNull String userId) {
        UserContext.HOLDER.set(userId);
    }

    /**
     * ユーザ情報のキーを登録します。
     *
     * @param playerId ユーザ情報のキー
     */
    public void playerId(Long playerId) {
        UserContext.HOLDER_PLAYER_ID.set(playerId);
    }
}
