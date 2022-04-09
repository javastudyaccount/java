package jp.btsol.mahjong.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.LoginId;
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
    @Autowired
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
     */
    public PlayerService(ApplicationProperties applicationProperties, //
            MahjongRestTemplate mahjongRestTemplate) {
        this.applicationProperties = applicationProperties;
        this.mahjongRestTemplate = mahjongRestTemplate;
    }

    /**
     * get player list
     * 
     * @return List<Player>
     */
    public List<Player> getPlayers() {

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
        Player player = mahjongRestTemplate.post(url, playerRegistration, Player.class);

        return player;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        LoginId loginIdModel = new LoginId(loginId);
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

//        // rawDataのパスワードは渡すことができないので、暗号化
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        // UserDetailsはインタフェースなのでUserクラスのコンストラクタで生成したユーザオブジェクトをキャスト
//        UserDetails userDetails = (UserDetails) new User(playerAuthentication.getLoginId(),
//                encoder.encode(playerAuthentication.getPassword()), grantList);

        UserDetails userDetails = (UserDetails) new User(playerAuthentication.getLoginId(),
                playerAuthentication.getPassword(), grantList);

//        UserDetails userDetails = (UserDetails) new User(playerAuthentication.getLoginId(),
//                "{noop}" + playerAuthentication.getPassword(), grantList);
        return userDetails;
    }
}
