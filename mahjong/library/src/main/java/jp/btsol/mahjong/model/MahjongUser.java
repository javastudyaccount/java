package jp.btsol.mahjong.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MahjongUser extends User {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4317202309467104246L;
    /**
     * player ID
     */
    private long playerId;
    /**
     * nickname
     */
    private String nickname;

    public MahjongUser(long playerId, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
