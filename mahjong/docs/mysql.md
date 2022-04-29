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
