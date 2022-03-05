package jp.btsol.mahjong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.btsol.mahjong.entity.Player;
import jp.btsol.mahjong.repository.BaseRepository;
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
        return null;
    }

    /**
     * insert player
     * 
     * @param nickName  String
     * @param requestId String
     * @return Player
     */
    public Player createNewPlayer(String nickName, String requestId) {
        return null;
    }
}
