package jp.btsol.mahjong.application.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DataNotFoundException;
import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Passwd;
import jp.btsol.mahjong.entity.PersistentLogins;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.PlayerAuthentication;
import jp.btsol.mahjong.model.PlayerModel;
import jp.btsol.mahjong.model.PlayerRegistration;
import jp.btsol.mahjong.utils.validator.Validator;
import lombok.extern.slf4j.Slf4j;

/**
 * Room service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@Transactional
public class PlayerService {
    /**
     * userContext アプリユーザー情報コンテキスト
     */
    private final UserContext userContext;
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    /**
     * Constructor
     * 
     * @param baseRepository BaseRepository
     * @param userContext    UserContext
     */
    public PlayerService(BaseRepository baseRepository, //
            UserContext userContext) {
        this.baseRepository = baseRepository;
        this.userContext = userContext;
    }

    /**
     * get players
     * 
     * @return List<PlayerModel>
     */
    public List<PlayerModel> getPlayers() {
        return baseRepository.findForList(//
                "select player.player_id, player.nickname, room_player.room_id, room.room_name, game_player.game_id "//
                        + "from player "//
                        + "left join room_player "//
                        + "on player.player_id = room_player.player_id "//
                        + "left join game "//
                        + "on room_player.room_id = game.room_id "//
                        + "left join game_player "//
                        + "on game.game_id = game_player.game_id "//
                        + "and game_player.player_id = player.player_id "//
                        + "left join room " //
                        + "on room_player.room_id = room.room_id ",
                PlayerModel.class);
    }

    /**
     * get player
     * 
     * @param loginId String
     * @return Player
     */
    public Player getPlayer(String loginId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", loginId);
        return baseRepository.findForObject("select * from player where login_id = :loginId", param, Player.class);
    }

    /**
     * get player authentication
     * 
     * @param loginId String
     * @return PlayerAuthentication
     */
    public PlayerAuthentication getPlayerAuthentication(String loginId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("loginId", loginId);
        try {
            return baseRepository.findForObject(//
                    "select player.player_id, " + //
                            " player.login_id, nickname, password " + //
                            "from player " + //
                            "join passwd " + //
                            "on player.player_id = passwd.player_id " + //
                            "where player.deleted_flg = 0 and " + //
                            "passwd.deleted_flg = 0 and " + //
                            "player.login_id = :loginId", //
                    params, PlayerAuthentication.class);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getLocalizedMessage());
            DataNotFoundException dnf = new DataNotFoundException(String.format("Player %s does not exists.", loginId),
                    e);
            throw dnf;
        }
    }

    /**
     * insert player
     * 
     * @param playerRegistration PlayerRegistration
     * @return Player
     */
    public Player createPlayer(PlayerRegistration playerRegistration) {
        if (StringUtils.isEmpty(playerRegistration.getNickname())) {
            throw new RuntimeException("Nickname can not be empty.");
        }
        Player player = new Player();
        player.setLoginId(playerRegistration.getLoginId());
        player.setNickname(playerRegistration.getNickname());
        Validator.validateMaxLength(player);

        int playerId = 0;
        try {
            playerId = baseRepository.insertWithSurrogateKey(player);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());
            String dupKey = "loginId";
            if (e.getLocalizedMessage().contains("player_login_unique")) {
                dupKey = "loginId";
            } else {
                dupKey = "nickname";
            }
            DuplicateKeyException dke = new DuplicateKeyException("Player's " + dupKey + " exists.", e);
            throw dke;
        }
        Passwd passwd = new Passwd();
        passwd.setPlayerId(playerId);

        passwd.setPassword(playerRegistration.getPassword());
        baseRepository.insert(passwd);

        return baseRepository.findById(playerId, Player.class);
    }

    public void createToken(PersistentRememberMeToken token) {
        PersistentLogins login = new PersistentLogins();
        login.setLoginId(token.getUsername());
        login.setSeries(token.getSeries());
        login.setToken(token.getTokenValue());
        login.setLastUsed(new Timestamp(token.getDate().getTime()));
        baseRepository.insert(login);
    }

    public void updateToken(PersistentRememberMeToken token) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("series", token.getSeries());
        param.addValue("token", token.getTokenValue());
        param.addValue("lastUsed", new Timestamp(token.getDate().getTime()));
        baseRepository.update("update persistent_logins set token=:token, last_used=:lastUsed where series = :series",
                param);
    }

    public PersistentLogins getToken(String series) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("series", series);
        return baseRepository.findForObject("select * from persistent_logins where series=:series", param,
                PersistentLogins.class);
    }

    public void deleteToken(String loginId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("loginId", loginId);
        baseRepository.update("delete from persistent_logins where login_id=:loginId", param);
    }

    public void invite(long[] players) {
//        List<MapSqlParameterSource> paramsList = Stream.of(players)
//                .map(p -> new MapSqlParameterSource().addValue("inviteFrom", userContext.playerId())
//                        .addValue("inviteTo", p).addValue("requestId", baseRepository.getRequestId()))
//                .collect(Collectors.toList());
//        SqlParameterSource[] params = paramsList.toArray(SqlParameterSource[]::new);
//        baseRepository.batchUpdate(//
//                "insert into invite_player "//
//                        + "(invite_from, invite_to, created_user, updated_user) " //
//                        + "values(:inviteFrom, :inviteTo, :requestId, :requestId)", //
//                params);
        baseRepository.batchUpdate(//
                "insert into invite_player "//
                        + "(invite_from, invite_to, created_user, updated_user) " //
                        + "values(:inviteFrom, :inviteTo, :requestId, :requestId)", //
                Stream.of(players)
                        .map(p -> new MapSqlParameterSource().addValue("inviteFrom", userContext.playerId())
                                .addValue("inviteTo", p).addValue("requestId", baseRepository.getRequestId()))
                        .collect(Collectors.toList()).toArray(SqlParameterSource[]::new));
    }
}
