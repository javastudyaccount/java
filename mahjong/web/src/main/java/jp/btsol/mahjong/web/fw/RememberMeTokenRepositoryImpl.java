package jp.btsol.mahjong.web.fw;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import jp.btsol.mahjong.web.service.PlayerService;

@Component
public class RememberMeTokenRepositoryImpl implements PersistentTokenRepository {
    /**
     * playerService PlayerService
     */
    @Autowired
    private PlayerService playerService;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        playerService.createToken(token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        playerService.updateToken(series, tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return playerService.getTokenForSeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        playerService.removeUserTokens(username);
    }

}
