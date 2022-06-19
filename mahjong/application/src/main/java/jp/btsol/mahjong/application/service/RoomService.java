package jp.btsol.mahjong.application.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.fw.exception.DuplicateKeyException;
import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Room;
import jp.btsol.mahjong.entity.RoomPlayer;
import jp.btsol.mahjong.fw.UserContext;
import jp.btsol.mahjong.model.PlayerModel;
import jp.btsol.mahjong.model.RoomModel;
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
public class RoomService {
    /**
     * userContext アプリユーザー情報コンテキスト
     */
    private final UserContext userContext;
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    public RoomService(BaseRepository baseRepository, //
            UserContext userContext) {
        this.baseRepository = baseRepository;
        this.userContext = userContext;
    }

    /**
     * get rooms
     * 
     * @return List<RoomModel>
     */
    public List<RoomModel> getRooms() {
        // begin transaction

        // SQLのパラメータを作成する
        MapSqlParameterSource param = new MapSqlParameterSource();
        // パラメータplayerIdの値を設定する
        param.addValue("playerId", userContext.playerId());
        try {
            // SQL文を実行して、データを取得する
            return baseRepository.findForList("select room.room_id, room.room_name, "//
                    + "(my_room.room_id is not null) as entered from room "//
                    + "left join (select room_id from room_player " //
                    + "where player_id = :playerId) my_room "//
                    + "on room.room_id = my_room.room_id order by room.room_id", param, RoomModel.class);
        } catch (Exception e) {
            // rollback
            return null;
        }
        // commit
    }

    /**
     * get room
     * 
     * @param roomId long
     * @param gameId Long
     * @return RoomModel
     */
    public RoomModel getRoom(long roomId, Long gameId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("playerId", userContext.playerId());
        param.addValue("roomId", roomId);
        RoomModel roomModel = baseRepository.findForObject(//
                "select room.room_id, room.room_name, "//
                        + "(my_room.room_id is not null) as entered, "//
                        + "game.game_id " + "from room "//
                        + "left join (select room_id from room_player " //
                        + "            where player_id = :playerId) my_room "//
                        + "on room.room_id = my_room.room_id "//
                        + "left join game "//
                        + "on room.room_id = game.room_id "//
                        + "where room.room_id = :roomId "//
                        + "order by room.room_id",
                param, RoomModel.class);
        List<PlayerModel> players = getPlayers(roomId, gameId);
        roomModel.setPlayersInRoom(players);
        return roomModel;
    }

    /**
     * get players in room
     * 
     * @param roomId long
     * @param gameId Long
     * @return List<Player>
     */
    public List<PlayerModel> getPlayers(long roomId, Long gameId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("roomId", roomId);
        if (Objects.nonNull(gameId)) {
            param.addValue("gameId", gameId);
        }
//        String sql = "select player.player_id, "//
//                + "player.nickname, "//
//                + "room_player.room_id, "//
//                + "game_player.game_id, "//
//                + "game_player.direction "//
//        ;
//        if (Objects.nonNull(gameId)) {
//            sql += ", game_log.log ";
//        }
//        sql += "from player "//
//                + "left join room_player "//
//                + "on player.player_id = room_player.player_id "//
//                + "left join game_player "//
//                + "on player.player_id = game_player.player_id "//
//        ;
//        if (Objects.nonNull(gameId)) {
//
//            sql += "left join " //
//                    + "(select * from game_log where (player_id, game_id, updated_timestamp) in "
//                    + "(select player_id, game_id, max(updated_timestamp) updated_timestamp from game_log "//
//                    + "where game_log.game_id = :gameId "//
//                    + "and deleted_flg = 0 "//
//                    + "group by player_id, game_id " + ") "//
//                    + ") game_log "//
//                    + "on game_log.player_id = player.player_id ";
//        }
//
//        sql += "where room_player.room_id = :roomId "//
//                + "order by player_id";
        StringBuffer sql = new StringBuffer("");
        sql.append("select ");
        sql.append("    player.player_id, ");
        sql.append("    player.nickname, ");
        sql.append("    room_player.room_id, ");
        sql.append("    game_player.game_id, ");
        sql.append("    game_player.direction ");
        if (Objects.nonNull(gameId)) {
            sql.append("    ,game_log.log ");
            sql.append("    ,game_log.action ");
        }
        sql.append("from ");
        sql.append("    player ");
        sql.append("    left join room_player on player.player_id = room_player.player_id ");
        sql.append("    left join game_player on player.player_id = game_player.player_id ");
        if (Objects.nonNull(gameId)) {
            sql.append("    and game_player.game_id = :gameId ");
            sql.append("    left join ( ");
            sql.append("        select ");
            sql.append("            distinct player_id, game_id, log, action ");
            sql.append("        from ");
            sql.append("            game_log ");
            sql.append("        where ");
            sql.append("            (player_id, game_id, updated_timestamp) in ( ");
            sql.append("                select ");
            sql.append("                    player_id, ");
            sql.append("                    game_id, ");
            sql.append("                    max(updated_timestamp) updated_timestamp ");
            sql.append("                from ");
            sql.append("                    game_log ");
            sql.append("                where ");
            sql.append("                    game_log.game_id = :gameId ");
            sql.append("                    and deleted_flg = 0 ");
            sql.append("                group by ");
            sql.append("                    player_id, ");
            sql.append("                    game_id ");
            sql.append("            ) ");
            sql.append("    ) game_log on game_log.player_id = player.player_id ");
            sql.append("      and game_log.game_id = game_player.game_id ");
        }
        sql.append("where ");
        sql.append("    room_player.room_id = :roomId ");
        sql.append("order by ");
        sql.append("    player_id ");
        return baseRepository.findForList(//
                sql.toString(), param, PlayerModel.class);
    }

    /**
     * enter room
     * 
     * @param roomId    long
     * @param invitorId long
     */
    public void enterRoom(long roomId, Long invitorId) {
        log.info("player id {}", userContext.playerId());
        RoomPlayer roomPlayer = new RoomPlayer();
        roomPlayer.setRoomId(roomId);
        roomPlayer.setPlayerId(userContext.playerId());
        roomPlayer.setRoleId(0); // visitor
        try {
            baseRepository.insert(roomPlayer);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            DuplicateKeyException dke = new DuplicateKeyException("You are alreay entered another room.", e);
            throw dke;
        }
        if (Objects.nonNull(invitorId)) {
            // update invite
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("inviteFrom", invitorId);
            param.addValue("roomId", roomId);
            param.addValue("me", userContext.playerId());
            param.addValue("requestId", baseRepository.getRequestId());
            baseRepository.update(//
                    "update invite_player "//
                            + "set status=concat(concat('enter room ', :roomId), concat(' invited by ', :inviteFrom)) "//
                            + ", updated_user = :requestId " //
                            + "where invite_to = :me ",
                    param);
        }
    }

    /**
     * exit room
     * 
     * @param roomId long
     * 
     */
    public void exitRoom(long roomId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("roomId", roomId);
        param.addValue("playerId", userContext.playerId());
        baseRepository.update("delete from room_player where room_id = :roomId and player_id = :playerId", param);
    }

    /**
     * insert room
     * 
     * @param roomName String
     * @return Room
     */
    public Room createRoom(String roomName) {
        if (Objects.isNull(roomName)) {
            throw new RuntimeException("room name can not be null.");
        }
        Room room = new Room();
        room.setRoomName(roomName);
        Validator.validateMaxLength(room);

        int roomId = 0;
        try {
            roomId = baseRepository.insertWithSurrogateKey(room);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error(e.getLocalizedMessage());

            DuplicateKeyException dke = new DuplicateKeyException("Room name exists.", e);
            throw dke;
        }
        return baseRepository.findById(roomId, Room.class);
    }
}
