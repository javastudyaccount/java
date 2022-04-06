package jp.btsol.mahjong.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DataNotFoundException;
import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Passwd;
import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.model.PlayerAuthentication;
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
     * baseRepository
     */
    private final BaseRepository baseRepository;

    public PlayerService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * get players
     * 
     * @return List<Player>
     */
    public List<Player> getPlayers() {
        return baseRepository.findForList("select * from player", Player.class);
    }

    /**
     * get player authentication
     * 
     * @param loginId String
     * @return PlayerAuthentication
     */
    public PlayerAuthentication getPlayerAuthentication(String loginId) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginId", loginId);
        try {
            return baseRepository.findForObject(//
                    "select " + //
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
    public Player createNewPlayer(PlayerRegistration playerRegistration) {
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
}
