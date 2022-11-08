



# Java study

1. ``git clone https://github.com/javastudyaccount/java.git``
   ![img](image/readme/1643452961806.png)
   ![img](image/readme/1643453118536.png)
   ![img](image/readme/1643453165583.png)
2. ``git add .``
3. ``git status``
   ![img](image/readme/1643453252345.png)
4. ``git commit -m comment``
   ![img](image/readme/1643453339273.png)

   ![img](image/readme/1643453466588.png)
   ![img](image/readme/1643453497552.png)

   ![img](image/readme/1643453565286.png)
5. ``git push``

   ![img](image/readme/1643453601368.png) `<br/>`
   ![](image/readme/1643453625436.png)`<br/>`
   javastudyaccount/javastudy123

   ![](image/readme/1643453685482.png)`<br/>`
   1344161724@qq.com
   c_ada59b6

   ![](image/readme/1643453708024.png)`<br/>`
   ![](image/readme/1643453750620.png)`<br/>`

   ![](image/readme/1643453767771.png)`<br/>`
6. ``git pull``
   ![](image/readme/1643454047390.png)

### How to split and combine files

https://superuser.com/questions/80081/how-to-split-and-combine-files

#### combine files

`copy /b example.ext.001+example.ext.002+example.ext.003+example.ext.004 example.ext`

#### Cygwin (basic install), Bash shell

`dd if=archive.tar bs=512M | xz -e9fc | split -b4000m - /destination/path/archive_split.`

`dd if=dbunit2.7.0.zip bs=512M | split -b5m - ./dbunit2.7.0_split.`

Omit the xz pipe block if your archive is already compressed.

#### To combine your archive together:

`cat archive_split.* > archive`

![1664025554079](image/readme/1664025554079.png)


select
   game_id,
   room_id,
   (
      select
         count(1)
      from
         game_player
      where
         game_player.game_id = :gameId
   ) countOfPlayers
from
   game
where
   game_id = :gameId


select
   *
from
   notice
where
   start_date <= :today
   and (
      end_date is null
      or end_date >= :today
   )
order by
   start_date


select
   player.player_id,
   player.nickname,
   room_player.room_id,
   room.room_name,
   game_player.game_id,
   invite_player.invite_to is not null as invited_flg
from
   player
   left join room_player on player.player_id = room_player.player_id
   left join game on room_player.room_id = game.room_id
   left join game_player on game.game_id = game_player.game_id
   and game_player.player_id = player.player_id
   left join room on room_player.room_id = room.room_id
   left join invite_player on invite_player.invite_from = :playerId
   and status = 'invited'
   and invite_player.invite_to = player.player_id
   and invite_timestamp >= NOW() - INTERVAL 1 HOUR

select
   *
from
   player
where
   login_id = :loginId

select
   player.player_id,
   player.login_id,
   nickname,
   password
from
   player
   join passwd on player.player_id = passwd.player_id
where
   player.deleted_flg = 0
   and passwd.deleted_flg = 0
   and player.login_id = :loginId


select
   *
from
   persistent_logins
where
   series = :series

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
   and game_player.game_id = :gameId
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
               game_log.game_id = :gameId
               and deleted_flg = 0
            group by
               player_id,
               game_id
         )
   ) game_log on game_log.player_id = player.player_id
   and game_log.game_id = game_player.game_id
where
   room_player.room_id = :roomId
order by
   player_id
