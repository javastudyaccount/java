package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.MahjongUser;
import jp.btsol.mahjong.model.PlayerAuthentication;
import jp.btsol.mahjong.model.PlayerRegistration;
import jp.btsol.mahjong.web.fw.MahjongRestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Player service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(ApplicationProperties.class)
public class PlayerService implements UserDetailsService {
    /**
     * passwordEncoder PasswordEncoder
     */
    private PasswordEncoder passwordEncoder;
    /**
     * Rest template
     */
    private final MahjongRestTemplate mahjongRestTemplate;
    /**
     * application properties
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Constructor
     * 
     * @param applicationProperties ApplicationProperties application properties
     * @param mahjongRestTemplate   MahjongRestTemplate
     * @param passwordEncoder       PasswordEncoder
     */
    public PlayerService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate, //
            PasswordEncoder passwordEncoder) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * get player list
     * 
     * @return List<Player>
     */
    public List<Player> getPlayers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getPlayers();

        List<Player> players = mahjongRestTemplate.get(url, ArrayList.class);

        return players;
    }

    /**
     * create new player
     * 
     * @param playerRegistration PlayerRegistration
     * @return Player
     */
    public Player createPlayer(PlayerRegistration playerRegistration) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreatePlayer();
        playerRegistration.setPassword(passwordEncoder.encode(playerRegistration.getPassword()));
        Player player = mahjongRestTemplate.post(url, playerRegistration, Player.class);

        return player;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getPlayerAuthentication() + "?loginId=" + loginId;
        PlayerAuthentication playerAuthentication = mahjongRestTemplate.get(url, PlayerAuthentication.class);

        if (playerAuthentication == null) {
            throw new UsernameNotFoundException("User" + loginId + "was not found in the database");
        }
        // 権限のリスト
        // AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
        // 権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);
        MahjongUser mahjongUser = new MahjongUser(playerAuthentication.getLoginId(), playerAuthentication.getPassword(),
                grantList);
        UserDetails userDetails = (UserDetails) mahjongUser;
        mahjongUser.setNickname(playerAuthentication.getNickname());
        return userDetails;
    }

    public void createToken(PersistentRememberMeToken token) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getCreateToken();
        mahjongRestTemplate.post(url, token);
    }

    public void updateToken(String series, String tokenValue, Date lastUsed) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getUpdateToken();
        PersistentRememberMeToken token = new PersistentRememberMeToken("", series, tokenValue, lastUsed);
        mahjongRestTemplate.put(url, token);
    }

    public PersistentRememberMeToken getTokenForSeries(String series) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getTokenForSeries();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("series", series);
        return mahjongRestTemplate.get(url, param, PersistentRememberMeToken.class);
    }

    public void removeUserTokens(String username) {
        final String endpoint = applicationProperties.getUri();

        final String url = endpoint + applicationProperties.getPath().getRemoveToken() + "?loginId=" + username;
        mahjongRestTemplate.delete(url);
    }
}
