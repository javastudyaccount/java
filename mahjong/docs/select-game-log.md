select
    player.player_id,
    player.nickname,
    room_player.room_id,
    game_player.game_id,
    game_player.direction,
    game_log.log,
    game_log.action
from
    player
    left join room_player on player.player_id = room_player.player_id
    left join game_player on player.player_id = game_player.player_id
    and game_player.game_id = '4'
    left join (
        select
            distinct player_id,
            game_id,
            log,
            action
        from
            game_log
        where
            (player_id, game_id, updated_timestamp) in (
                select
                    player_id,
                    game_id,
                    max(updated_timestamp) updated_timestamp
                from
                    game_log
                where
                    game_log.game_id = '4'
                    and deleted_flg = 0
                group by
                    player_id,
                    game_id
            )
    ) game_log on game_log.player_id = player.player_id
    and game_log.game_id = game_player.game_id
where
    room_player.room_id = '4'
order by
    player_id