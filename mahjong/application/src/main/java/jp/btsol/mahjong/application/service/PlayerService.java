package jp.btsol.mahjong.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Player;
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
     * insert player
     * 
     * @param nickname String
     * @return Player
     */
    public Player createNewPlayer(String nickname) {
        if (Objects.isNull(nickname)) {
            throw new RuntimeException("Nickname can not be null.");
        }
        Player player = new Player();
        player.setNickname(nickname);
        Validator.validate(player);

        int playerId = 0;
        try {
            playerId = baseRepository.insert(player);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());
            DuplicateKeyException dke = new DuplicateKeyException("Player's nickname exists.", e);
            dke.setPath("/player/new");
            throw dke;
        }
        return baseRepository.findById(playerId, Player.class);
    }
}
