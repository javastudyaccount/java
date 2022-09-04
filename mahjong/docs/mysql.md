### MySQL
#### start ubuntu
ubuntu

#### Pull docker image(Only for first time)
`$ docker pull mysql`

#### Start a mysql server instance
`$ docker run -p 3306:3306 --name game -e MYSQL_ROOT_PASSWORD=game -d mysql:latest `

#### Docker CLI
`$ docker exec -it /game /bin/bash`

`$ mysql -h localhost -P 3306 -u root -p`
Enter password: game

#### List all docker containers
`$ docker ps -a`

#### How to continue a Docker container which has exited
```
$ docker start  `docker ps -q -l`
```

#### Create database
`mysql> create database game character set utf8;`

`mysql> use game`
Paste [ddl.sql](./ddl/ddl.sql)

#### Create user
`mysql> CREATE USER game IDENTIFIED BY 'game';`

`mysql> GRANT ALL ON *.* TO game;`

`mysql> GRANT ALL ON *.* TO game@localhost;`

`mysql> exit;`

#### Connect mysql

`$ docker exec -it /game /bin/bash`

`$ mysql -h localhost -P 3306 -u game -p`
Enter password: game
`mysql> use game`

`mysql> show tables;`

| Tables_in_game |
| -------------- |
| game           |
| game_log       |
| game_player    |
| player         |
| room           |


@enduml

### Restart docker 
`$ docker ps -a`
<pre>
CONTAINER ID   IMAGE          COMMAND                  CREATED        STATUS                       PORTS                                                  NAMES
8b28914fcd7f   mysql:latest   "docker-entrypoint.s…"   2 months ago   Exited (255) 4 minutes ago   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp   game
</pre>
`$ docker start 8b28914fcd7f`
<pre>
8b28914fcd7f
</pre>
### Restart docker in one command 
```
docker start  `docker ps -q -l`
```
then [Connect mysql](#connect-mysql)


```sql
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
    left join invite_player on invite_player.invite_from = '1'
    and invite_player.invite_to = player.player_id
    and invite_timestamp >= NOW() - INTERVAL 1 HOUR
```

### MySQLのタイムゾーンを日本時間にする
https://qiita.com/rowpure/items/dbedbe2b98e91a34d0d5
以下のコマンドを実行する

#### DBコンテナ作成＆起動（DBコンテナがなければ）
ホストOS% docker run --name ＜好きなDBコンテナ名＞ -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
#### DBコンテナに入る
ホストOS% docker exec -it ＜作成したDBコンテナ名＞ bash
#### タイムゾーンのテーブルを取得
DBコンテナ% mysql_tzinfo_to_sql /usr/share/zoneinfo/
#### タイムゾーンを日本時間にする設定ファイルを新規作成（conf.dディレクトリの配下であればファイル名は何でもOK）
DBコンテナ% echo "" >> /etc/mysql/conf.d/etc-mysql.cnf
DBコンテナ% sed -i -e "1i [mysqld]\n    default-time-zone='Asia/Tokyo'" /etc/mysql/conf.d/etc-mysql.cnf

DBコンテナ% exit
#### DBコンテナの再起動（MySQL単体の再起動ができないので）
ホストOS% docker restart ＜作成したDBコンテナ名＞

```sql
select nickname, room_name from invite_player
join player on player.player_id = invite_player.invite_from
join room_player on room_player.player_id = invite_player.invite_from
join room on room_player.room_id = room_player.room_id
where invite_to = 3
and invite_timestamp >= NOW() - INTERVAL 1 HOUR
```



```sql
select
    player.player_id,
    player.nickname,
    room_player.room_id,
    game_player.game_id,
    game_player.direction,
    game_log.log
from
    player
    left join room_player on player.player_id = room_player.player_id
    left join game_player on player.player_id = game_player.player_id
    and game_player.game_id = 4
    left join (
        select
            distinct player_id, game_id, log
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
    ) game_log on game_log.player_id = player.player_id and game_log.game_id = game_player.game_id
where
    room_player.room_id = '1'
order by
    player_id
```

```sql
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
    room_player.room_id = '1'
order by
    player_id
```