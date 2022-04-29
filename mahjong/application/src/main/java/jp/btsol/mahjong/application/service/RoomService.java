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
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("playerId", userContext.playerId());
        return baseRepository.findForList("select room.room_id, room.room_name, "//
                + "(my_room.room_id is not null) as entered from room "//
                + "left join (select room_id from room_player " //
                + "where player_id = :playerId) my_room "//
                + "on room.room_id = my_room.room_id order by room.room_id", param, RoomModel.class);
    }

    /**
     * get room
     * 
     * @param roomId long
     * @return RoomModel
     */
    public RoomModel getRoom(long roomId) {
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
                        + "on room.room_id = game.room_id " + "where room.room_id = :roomId "//
                        + "order by room.room_id",
                param, RoomModel.class);
        List<PlayerModel> players = getPlayers(roomId);
        roomModel.setPlayersInRoom(players);
        return roomModel;
    }

    /**
     * get players in room
     * 
     * @param roomId long
     * @return List<Player>
     */
    public List<PlayerModel> getPlayers(long roomId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("roomId", roomId);
        return baseRepository.findForList(//
                "select player.player_id, player.nickname, room_player.room_id, game_player.game_id "//
                        + "from player "//
                        + "left join room_player "//
                        + "on player.player_id = room_player.player_id "//
                        + "left join game_player "//
                        + "on player.player_id = game_player.player_id "//
                        + "where room_player.room_id = :roomId "//
                        + "order by player_id",
                param, PlayerModel.class);
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
