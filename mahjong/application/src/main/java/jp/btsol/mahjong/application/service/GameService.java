package jp.btsol.mahjong.application.service;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.fw.exception.TooManyPlayersException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Game;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.model.GameModel;
import jp.btsol.mahjong.model.RoomModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Game service
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@Slf4j
@Transactional
public class GameService {
    /**
     * userContext アプリユーザー情報コンテキスト
     */
    private final UserContext userContext;
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    /**
     * roomService
     */
    private final RoomService roomService;

    public GameService(BaseRepository baseRepository, //
            UserContext userContext, //
            RoomService roomService) {
        this.baseRepository = baseRepository;
        this.userContext = userContext;
        this.roomService = roomService;
    }

    /**
     * insert game
     * 
     * @param roomId long
     * @return GameId
     */
    public GameId createGame(long roomId) {
        int gameId = 0;
        try {
            Game game = new Game();
            game.setRoomId(roomId);
            gameId = baseRepository.insertWithSurrogateKey(game);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());

            DuplicateKeyException dke = new DuplicateKeyException("Only one game in a room.", e);
            throw dke;
        }
//        MapSqlParameterSource param = new MapSqlParameterSource();
//        param.addValue("gameId", gameId);
//        param.addValue("playerId", userContext.playerId());
//        baseRepository.update("insert into game_player (game_id, player_id) values (:gameId, :playerId)", param);
        enterGame(gameId);
        return new GameId(gameId);
    }

    /**
     * enter game
     * 
     * @param gameId long
     */
    public void enterGame(long gameId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("gameId", gameId);
        param.addValue("playerId", userContext.playerId());
        param.addValue("requestId", baseRepository.getRequestId());
        baseRepository.update(//
                "insert into game_player " //
                        + " (game_id, player_id, created_user, updated_user) "//
                        + " values(:gameId, :playerId, :requestId, :requestId)",
                param);
        int count = baseRepository.queryForInt("select count(1) from game_player where game_id = :gameId", param);
        if (count > 4) {
            throw new TooManyPlayersException("The game already has 4 players.");
        }
    }

    /**
     * get game
     * 
     * @param gameId long
     * @return GameModel
     */
    public GameModel getGame(long gameId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("gameId", gameId);
        GameModel game = baseRepository.findForObject(//
                "select game_id, room_id, "//
                        + "(select count(1) from game_player where game_player.game_id = :gameId) countOfPlayers "
                        + "from game where game_id = :gameId", //
                param, //
                GameModel.class);
        RoomModel room = roomService.getRoom(game.getRoomId());
        game.setRoomModel(room);
        return game;
    }

}
