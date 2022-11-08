package jp.btsol.mahjong.application.service;

import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.fw.exception.TooManyPlayersException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Game;
import jp.btsol.mahjong.entity.GameLog;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.GameId;
import jp.btsol.mahjong.model.GameModel;
import jp.btsol.mahjong.model.MahjongGameMessage;
import jp.btsol.mahjong.model.PlayerModel;
import jp.btsol.mahjong.model.RoomModel;
import jp.btsol.mahjong.utils.validator.Validator;
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
                "select game.game_id, game.room_id, "//
                        + "(select count(1) from game_player where game_player.game_id = :gameId) countOfPlayers "
                        + "from game where game.game_id = :gameId", //
                param, //
                GameModel.class);
        RoomModel room = roomService.getRoom(game.getRoomId(), gameId);
        game.setRoomModel(room);

//        List<PlayerModel> players = roomService.getPlayers(game.getRoomId(), gameId);
//        long readyCount = players.stream().filter(player -> "ready for grabing a seat".equals(player.getAction()) //
//                || player.getAction().startsWith("grab seat")).count();
//        long sitCount = players.stream().filter(player -> player.getAction().startsWith("grab seat")).count();
//        long rolledCount = players.stream().filter(player -> player.getAction().contains("dice dealer")).count();
//        String myStatus = players.stream().filter(player -> player.getPlayerId() == userContext.playerId()).findFirst()
//                .get().getAction();
//        if (rolledCount == 4) {
//            game.setGameStatus("Wait dealer to dice.");
//        } else if (rolledCount > 0) {
//            if (myStatus.contains("dice dealer")) {
//                game.setGameStatus("Waiting others to dice for deciding dealer.");
//            } else {
//                game.setGameStatus("Ready to dice for deciding dealer.");
//            }
//        } else {
//            if (sitCount == 4) {
//                game.setGameStatus("Ready to dice for deciding dealer.");
//            } else {
//                if (readyCount == 4) {
//                    if (myStatus.startsWith("grab seat")) {
//                        game.setGameStatus("Waiting others sit.");
//                    } else {
//                        game.setGameStatus("Ready to click direciton for grabing seat.");
//                    }
//                } else {
//                    game.setGameStatus("Waiting to grab a seat.");
//                }
//            }
//        }
        return game;
    }

    /**
     * ready to grab seat
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2GrabSeat(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        gameLog.setLog(message.getMessage());

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);
        messageRet.setMessage(gameLog.getLog());

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);

        long readyCount = players.stream().filter(player -> "ready for grabing a seat".equals(player.getAction()))
                .count();
        if (readyCount == 4) {
            messageRet.setGameStatus("Ready for grabing seat.");
        }
        return messageRet;
    }

    /**
     * grab seat
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage grabSeat(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        gameLog.setLog(message.getMessage());

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        String seat = message.getAction().replace("grab seat ", "");
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("gameId", message.getGameId());
        param.addValue("seat", seat);
        param.addValue("playerId", userContext.playerId());
        int updated = baseRepository.update(
                "update game_player set direction = :seat where game_id = :gameId and player_id = :playerId", param);
        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);
        messageRet.setMessage(gameLog.getLog());
        if (updated != 1) {
//            messageRet.setGameStatus("ERROR: failed to grab seat.");
//            return messageRet;
            throw new RuntimeException("Failed to grab seat.");
        }

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);

        long readyCount = players.stream().filter(player -> player.getAction().startsWith("grab seat")).count();
        if (readyCount == 4) {
            messageRet.setGameStatus("Ready for deciding dealer.");
        } else {
            messageRet.setGameStatus("Waiting others sit.");
        }
        return messageRet;
    }

    /**
     * dice dealer
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage diceDealer(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        Random r = new Random();
        int dice1 = r.nextInt(6) + 1;
        int dice2 = r.nextInt(6) + 1;
        String log = message.getNickname() + " rolled " + String.valueOf(dice1 + dice2);
        gameLog.setLog(log);

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);

//        long rolledCount = players.stream().filter(player -> player.getAction().contains("rolled")).count();
//        if (rolledCount < 4) {
//            messageRet.setGameStatus("Waiting others rolling dealer.");
//        }
        return messageRet;
    }

    /**
     * redice dealer
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage rediceDealer(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        Random r = new Random();
        int dice1 = r.nextInt(6) + 1;
        int dice2 = r.nextInt(6) + 1;
        String log = message.getNickname() + " rerolled " + String.valueOf(dice1 + dice2);
        gameLog.setLog(log);

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);
        return messageRet;
    }

    /**
     * redice
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2Redice(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        gameLog.setLog(message.getMessage());

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);
        return messageRet;
    }

    /**
     * redice waiting
     * 
     * @param message MahjongGameMessage
     * @return MahjongGameMessage
     */
    public MahjongGameMessage ready2RediceWaiting(MahjongGameMessage message) {
        GameLog gameLog = new GameLog();
        gameLog.setGameId(message.getGameId());
        gameLog.setPlayerId(message.getPlayerId());
        gameLog.setAction(message.getAction());
        gameLog.setLog(message.getMessage());

        Validator.validateMaxLength(gameLog);

        int gameLogId = 0;
        gameLogId = baseRepository.insertWithSurrogateKey(gameLog);

        gameLog = baseRepository.findById(gameLogId, GameLog.class);

        MahjongGameMessage messageRet = new ModelMapper().map(message, MahjongGameMessage.class);

        List<PlayerModel> players = roomService.getPlayers(message.getRoomId(), message.getGameId());
        messageRet.setPlayers(players);
        return messageRet;
    }
}
