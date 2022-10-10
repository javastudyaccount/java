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
        // SQL文を実行して、データを取得する
        return baseRepository.findForList("select room.room_id, room.room_name, "//
                + "(my_room.room_id is not null) as entered from room "//
                + "left join (select room_player.room_id from room_player " //
                + "where room_player.player_id = :playerId) my_room "//
                + "on room.room_id = my_room.room_id order by room.room_id", param, RoomModel.class);
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
                " select room.room_id, room.room_name, "//
                        + " (my_room.room_id is not null) as entered, "//
                        + " game.game_id "//
                        + " from room "//
                        + " left join (select room_player.room_id from room_player " //
                        + "          where room_player.player_id = :playerId) my_room "//
                        + " on room.room_id = my_room.room_id "//
                        + " left join game "//
                        + " on room.room_id = game.room_id "//
                        + " where room.room_id = :roomId "//
                        + " order by room.room_id, room.room_name",
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
        StringBuffer sql = new StringBuffer("");
        sql.append("select ");
        sql.append("    p.player_id, ");
        sql.append("    p.nickname, ");
        sql.append("    room_player.room_id, ");
        sql.append("    game_player.game_id, ");
        sql.append("    game_player.direction ");
        if (Objects.nonNull(gameId)) {
            sql.append("    ,game_log.log ");
            sql.append("    ,game_log.action ");
        }
        sql.append("from ");
        sql.append("    player p");
        sql.append("    left join room_player on p.player_id = room_player.player_id ");
        sql.append("    left join game_player on p.player_id = game_player.player_id ");
        if (Objects.nonNull(gameId)) {
            sql.append("    and game_player.game_id = :gameId ");
            sql.append("    left join ( ");
            sql.append("        select ");
            sql.append("            distinct game_log.player_id, game_log.game_id, game_log.log, game_log.action ");
            sql.append("        from ");
            sql.append("            game_log ");
            sql.append("        where ");
            sql.append("            (game_log.player_id, game_log.game_id, game_log.updated_timestamp) in ( ");
            sql.append("                select ");
            sql.append("                    game_log.player_id, ");
            sql.append("                    game_log.game_id, ");
            sql.append("                    max(game_log.updated_timestamp) updated_timestamp ");
            sql.append("                from ");
            sql.append("                    game_log ");
            sql.append("                where ");
            sql.append("                    game_log.game_id = :gameId ");
            sql.append("                    and game_log.deleted_flg = 0 ");
            sql.append("                group by ");
            sql.append("                    game_log.player_id, ");
            sql.append("                    game_log.game_id ");
            sql.append("            ) ");
            sql.append("    ) game_log on game_log.player_id = p.player_id ");
            sql.append("      and game_log.game_id = game_player.game_id ");
        }
        sql.append("where ");
        sql.append("    room_player.room_id = :roomId ");
        sql.append("order by ");
        sql.append("    p.player_id ");
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

    public void testSQL() {
        StringBuffer sql = new StringBuffer();
        sql.append("    with tmp as ( ");
        sql.append("    select ");
        sql.append("        dr.user_id, ");
        sql.append("        au.user_name, ");
        sql.append("        dr.record_date, ");
        sql.append("        dr.steps, ");
        sql.append("        au.company_cd ");
        sql.append("    from ");
        sql.append("        daily_record dr ");
        sql.append("        inner join app_user au on dr.user_id = au.user_id ");
        sql.append("        and au.is_deleted = false ");
        sql.append("    where ");
        sql.append("        dr.record_date between '2022-06-01' ");
        sql.append("        and '2022-06-30' ");
        sql.append("        and au.company_cd = 'C100012FX' ");
        sql.append("        and dr.steps is not null ");
        sql.append("        and dr.is_deleted = false ");
        sql.append("), aaa as (select * from daily_record) ");
        sql.append("select ");
        sql.append("    tmp.user_id as お客様番号, ");
        sql.append("    tmp.user_name as 社員氏名, ");
        sql.append("    tmp.record_date as ウォーキングチャレンジ記録日, ");
        sql.append("    tmp.steps as ウォーキングチャレンジ歩数記録, ");
        sql.append("    tmph.point_grant_item_name as 付与項目, ");
        sql.append("    substr(tmph.grant_date, 0, 9) as ポイント付与日, ");
        sql.append("    tmph.point as ポイント付与数 ");
        sql.append("from ");
        sql.append("    tmp ");
        sql.append("    left join t_myhl_point_history tmph on tmph.client_id = tmp.company_cd ");
        sql.append("    and tmph.user_id = tmp.user_id ");
        sql.append("    and TO_CHAR(tmp.record_date + 15, 'yyyyMMdd') = substr(tmph.grant_date, 0, 9) ");
        sql.append("    and tmph.point_grant_category_id = '01' ");
        sql.append("    and tmph.point_grant_item_id >= 1 ");
        sql.append("    and tmph.point_grant_item_id <= 10 ");
        sql.append("    and tmph.period_div = '01' ");
        sql.append("    and tmph.delete_flg = 0 ");
        sql.append("union ");
        sql.append("all ");
        sql.append("select ");
        sql.append("    tmph2.user_id as お客様番号, ");
        sql.append("    au.user_name as 社員氏名, ");
        sql.append("    null as ウォーキングチャレンジ記録日, ");
        sql.append("    null as ウォーキングチャレンジ歩数記録, ");
        sql.append("    tmph2.point_grant_item_name as 付与項目, ");
        sql.append("    substr(tmph2.grant_date, 0, 9) as ポイント付与日, ");
        sql.append("    tmph2.point as ポイント付与数 ");
        sql.append("from ");
        sql.append("    t_myhl_point_history tmph2 ");
        sql.append("    inner join app_user au on tmph2.user_id = au.user_id ");
        sql.append("    and au.is_deleted = false ");
        sql.append("where ");
        sql.append("    exists ( ");
        sql.append("        select ");
        sql.append("            1 ");
        sql.append("        from ");
        sql.append("            tmp ");
        sql.append("        where ");
        sql.append("            tmph2.user_id = tmp.user_id ");
        sql.append("            and tmph2.client_id = tmp.company_cd ");
        sql.append("    ) ");
        sql.append("    and ( ");
        sql.append("        ( ");
        sql.append("            tmph2.period_div = '02' ");
        sql.append("            and substr(tmph2.grant_date, 0, 9) >= '20220620' ");
        sql.append("            and substr(tmph2.grant_date, 0, 9) <= '20220718' ");
        sql.append("        ) ");
        sql.append("        or ( ");
        sql.append("            tmph2.period_div = '03' ");
        sql.append("            and substr(tmph2.grant_date, 0, 9) = '20220801' ");
        sql.append("        ) ");
        sql.append("    ) ");
        sql.append("    and tmph2.point_grant_item_id >= 11 ");
        sql.append("    and tmph2.point_grant_item_id <= 41 ");
        sql.append("    and tmph2.delete_flg = 0 ");
        sql.append("order by ");
        sql.append("    1, ");
        sql.append("    3, ");
        sql.append("    7; ");
        baseRepository.findForList(//
                sql.toString(), null, null);
    }

    public void testSQL2() {
        StringBuffer sql = new StringBuffer();
        sql.append(" ");
        sql.append(" SELECT ");
        sql.append("    tmp.user_id AS お客様番号");
        sql.append("    , ");
        sql.append("    tmp.user_name AS 社員氏名");
        sql.append("    , ");
        sql.append("    tmp.record_date AS ウォーキングチャレンジ記録日");
        sql.append("    , ");
        sql.append("    tmp.steps AS ウォーキングチャレンジ歩数記録");
        sql.append("    , ");
        sql.append("    tmph.point_grant_item_name AS 付与項目");
        sql.append("    , ");
        sql.append("    substr(tmph.grant_date, 0, 9) AS ポイント付与日");
        sql.append("    , ");
        sql.append("    tmph.point AS ポイント付与数");
        sql.append(" FROM ");
        sql.append("    (");
        sql.append("    SELECT ");
        sql.append("        dr.user_id");
        sql.append("        , ");
        sql.append("        au.user_name");
        sql.append("        , ");
        sql.append("        dr.record_date");
        sql.append("        , ");
        sql.append("        dr.steps");
        sql.append("        , ");
        sql.append("        au.company_cd");
        sql.append("    FROM ");
        sql.append("        daily_record dr");
        sql.append("    INNER JOIN app_user au ");
        sql.append("        ON ");
        sql.append("        dr.user_id = au.user_id");
        sql.append("        AND");
        sql.append("        au.is_deleted = false");
        sql.append("    WHERE ");
        sql.append("        dr.record_date BETWEEN '2022-06-01' AND '2022-06-30'");
        sql.append("        AND");
        sql.append("        au.company_cd = 'C100012FX'");
        sql.append("        AND");
        sql.append("        dr.steps IS NOT NULL");
        sql.append("        AND");
        sql.append("        dr.is_deleted = false");
        sql.append(" ) as tmp");
        sql.append(" LEFT JOIN t_myhl_point_history tmph ");
        sql.append("    ON ");
        sql.append("    tmph.client_id = tmp.company_cd");
        sql.append("    AND");
        sql.append("    tmph.user_id = tmp.user_id");
        sql.append("    AND");
        sql.append("    TO_CHAR(tmp.record_date + 15, 'yyyyMMdd') = substr(tmph.grant_date, 0, 9)");
        sql.append("    AND");
        sql.append("    tmph.point_grant_category_id = '01'");
        sql.append("    AND");
        sql.append("    tmph.point_grant_item_id >= 1");
        sql.append("    AND");
        sql.append("    tmph.point_grant_item_id <= 10");
        sql.append("    AND");
        sql.append("    tmph.period_div = '01'");
        sql.append("    AND");
        sql.append("    tmph.delete_flg = 0");
        sql.append(" ORDER BY ");
        sql.append(" 1, 3, 7");

        baseRepository.findForList(//
                sql.toString(), null, null);
    }
}
