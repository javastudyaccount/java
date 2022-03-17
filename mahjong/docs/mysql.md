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

#### Connection

`$ docker exec -it /game /bin/bash`

`$ mysql -h localhost -P 3306 -u game -p`
Enter password: game
`mysql> use game`

`mysql> show tables;`

| Tables_in_game |
|----------------|
| game           |
| game_log       |
| game_player    |
| player         |
| room           |
