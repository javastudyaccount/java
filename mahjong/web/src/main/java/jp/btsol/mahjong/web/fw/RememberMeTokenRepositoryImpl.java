package jp.btsol.mahjong.web.fw;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.mahjong4j.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.btsol.mahjong.web.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RememberMeTokenRepositoryImpl implements PersistentTokenRepository {
    /**
     * COOKIE_NAME
     */
    private static final String COOKIE_NAME = TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;
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
//        PersistentRememberMeToken token = getUser();

        playerService.updateToken(series, tokenValue, lastUsed);
    }

    public PersistentRememberMeToken getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String rememberMeCookie = Utils.extractCookie(request, COOKIE_NAME);
        String[] cookieTokens = Utils.decodeCookie(rememberMeCookie);
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 + " tokens, but contained '"
                    + Arrays.asList(cookieTokens) + "'");
        }
        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        PersistentRememberMeToken token = getTokenForSeries(presentedSeries);
        if (token == null) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
        }
        return token;
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return playerService.getTokenForSeries(seriesId);
            // CHECKSTYLE:OFF
        } catch (Exception e) {
            // CHECKSTYLE:ON
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        playerService.removeUserTokens(username);
    }
}
