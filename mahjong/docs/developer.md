
### List all branches

    $ git branch -a
    * development
    mvn
    remotes/origin/20220205_xiangyang_homework
    remotes/origin/20220205_xiangyang_homework1
    remotes/origin/20220205_xinwei_homework
    remotes/origin/HEAD -> origin/development
    remotes/origin/development
    remotes/origin/main
    remotes/origin/mvn

    systemi@PC526 MINGW64 /c/app/java (development)

### Delete local branch

    $ git branch --delete mvn
    Deleted branch mvn (was 73b94d9).

    systemi@PC526 MINGW64 /c/app/java (development)

### Prune remote branch

    $ git fetch --prune
    From https://github.com/javastudyaccount/java
    - [deleted]         (none)     -> origin/mvn

    systemi@PC526 MINGW64 /c/app/java (development)

### Delete remote branch

    $ git push --delete origin 20220205_xiangyang_homework

### List all branches again

    $ git branch -a
    * development
    remotes/origin/20220205_xiangyang_homework
    remotes/origin/20220205_xiangyang_homework1
    remotes/origin/20220205_xinwei_homework
    remotes/origin/HEAD -> origin/development
    remotes/origin/development
    remotes/origin/main

    systemi@PC526 MINGW64 /c/app/java (development)


### Create DDL from database

using pgAdmin

- Right-click on your database (or schema).
- Choose "backup"
- Under "Format" choose "plain"
- Under "Dump Options #1" choose "Only schema"
- Under "Objects" choose the tables you want.
- Then click "backup". The output should be a plain text file with the create table statements.


### Docker

    Windows 10 ビルド 18917 以降であること
    コマンドプロンプトから確認

    1. コマンドプロンプトを開く
    2. verコマンドを実行
    3. 出てきたバージョンが18917以上ならOK！

- [WSL install](https://docs.microsoft.com/zh-cn/windows/wsl/install)
  `PS> wsl --install`
- [Docker install](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository)

  - Set up the repository
    - `PS> wsl`
    - `$  sudo apt-get update`
    - `$ sudo apt-get install ca-certificates curl gnupg lsb-release`
    - `$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg`
    - `$  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \ $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null`
  - Install Docker Engine
    - `$ sudo apt-get update`
    - `$ sudo apt-get install docker-ce docker-ce-cli containerd.io`
  - Manually start docker
    - `$ sudo /etc/init.d/docker start`
  - Verify that Docker Engine
    - `$ sudo docker run hello-world`
- Set Docker Daemon auto start

  - `$ echo "# Start dockerd" >> ~/.bashrc`
  - `$ echo "sudo /etc/init.d/docker start" >> ~/.bashrc`
- Set default user as root

  - `PS> ubuntu config --default-user root`


[Spring Bootとは? Spring BootでWebアプリ開発を始めるために必要な知識を紹介](https://i-common-tech.jp/column/940)

   [Spring BootでThymeleafを使ってhello worldを表示させる方法には？](https://i-common-tech.jp/column/940#Spring+Boot%E3%81%A7Thymeleaf%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6hello+world%E3%82%92%E8%A1%A8%E7%A4%BA%E3%81%95%E3%81%9B%E3%82%8B%E6%96%B9%E6%B3%95%E3%81%AB%E3%81%AF%EF%BC%9F)

### Start dababase
`ubuntu`
systemi

`$ docker start  `docker ps -q -l``

`$ docker exec -it /game /bin/bash`

`mysql -h localhost -P 3306 -u game -p`
game

## [逆にWebAPIを呼び出してみよう編](https://zenn.dev/sugaryo/books/spring-boot-run-up/viewer/api_call)

  [RestTemplateラッパー三点セット](https://zenn.dev/sugaryo/books/spring-boot-run-up/viewer/api_call#%E2%96%A0resttemplate%E3%83%A9%E3%83%83%E3%83%91%E3%83%BC%E4%B8%89%E7%82%B9%E3%82%BB%E3%83%83%E3%83%88)

## Advanced

[組み込み Web サーバー](https://spring.pleiades.io/spring-boot/docs/2.1.4.RELEASE/reference/html/howto-embedded-web-servers.html)
    HTTP ポートを変更する
    ランダムな未割り当ての HTTP ポートを使用する
    実行時に HTTP ポートを発見する
    HTTP レスポンス圧縮を有効にする
    SSL を構成する
    HTTP/2 を構成する
    サーブレット、フィルター、またはリスナーをアプリケーションに追加する
        Spring Bean を使用して、サーブレット、フィルター、またはリスナーを追加する
        クラスパススキャンを使用してサーブレット、フィルター、リスナーを追加する
    アクセスログを構成する
    フロントエンドプロキシサーバーの背後で実行する
    @ServerEndpoint を使用して WebSocket エンドポイントを作成する

## Projects

![](image/Mahjiang/1644579560635.png)

| Project     | Function       |
| ----------- | -------------- |
| web         | front end      |
| application | api            |
| library     | common library |
| database    | jpa            |

```plantuml
@startuml

package "web"{
   [Springboot]
   [html]
   [Thymeleaf]
   [JQuery]
   [css]
}

[vue.js]
[iPhone]
[Android]
package "api"{
    [application]-HTTP
}
[library]
package "database"{
    [MySQL]
}
web --> [library] : use
[vue.js] ..> HTTP : use
[iPhone] ..> HTTP : use
[Android] ..> HTTP : use
[application] --> [library] : use
web ..> HTTP
api --> database : use
@enduml
```

[Spring MVC(+Spring Boot)上でのリクエスト共通処理の実装方法を理解する](https://qiita.com/kazuki43zoo/items/757b557c05f548c6c5db)
![](image/Mahjiang/1644734013436.png)

![](image/Mahjiang/1644734088691.png)

## Mixin:

[Adding Custom Properties Using Jackson MixIns](https://medvector.github.io/programming/jackson/jackson-trick/)

[Java : Jackson による JSON デシリアライズ時の型解決方法](https://www.techscore.com/blog/2016/06/17/java-jackson-polymorphic-deserialization/)

- Java

  - Springboot(Framework) Spring Wicket Struts
- Database (Oracle, MySQL, Postgresql)

  - JDBC
  - JPA
  - Hibernate
  - Mybatis
  - JdbcTemplate
  - jooq

  [Java &amp; Databases: An Overview of Libraries &amp; APIs](https://www.marcobehler.com/guides/java-databases)
- RestAPI
- Thymeleaf, Wicket, JSP, Apache Velocity
- HTML
- CSS
- JS


### API Test

#### Curl

curl -v -H "request-id:requestid" -H "Content-Type:application/json" -H "api-key:AAAAAAA" -H "x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0="  http://localhost:8088/v1/room/all

```shell
curl  -X POST -v -H "request-id:requestid" -H "Content-Type:application/json" -H "api-key:AAAAAAA" -d "test name" http://localhost:8088/v1/room/new 
```

```json
< HTTP/1.1 200
< Content-Type: application/json

{
  "roomId" : 22,
  "roomName" : "{\"roonName\":\"test name\"}",
  "deletedFlg" : false,
  "createdTimestamp" : "2022-02-20T07:38:39.000+00:00",
  "createdUser" : "default",
  "updatedTimestamp" : "2022-02-20T07:38:39.000+00:00",
  "updatedUser" : "default"
}
```

#### Advanced REST client

![](image/Mahjiang/1645345034221.png)
![](image/Mahjiang/1645343275274.png)
![](image/Mahjiang/1645343297819.png)

[Is there a method built in spring MockMVC to get json content as Object?](https://stackoverflow.com/questions/51873620/is-there-a-method-built-in-spring-mockmvc-to-get-json-content-as-object)

MVC:

- M: Model
- V: View
- C: Controller
  ![](image/Mahjong/1646465569844.png)
  Address bar:
  URL: `http://   localhost: 8089  /tiles`
  `[protocol]://[hostname]:[port][path]`
  ![](image/Mahjong/1646465658183.png)

  path: @GetMapping("/tiles")
  ![](image/Mahjong/1646465705801.png)
  html: tiles.html
  ![](image/Mahjong/1646465877062.png)
- /
- /hands
- /rooms
- /tiles/arranged
- /tiles
- /tiles/random
- /tiles/shuffled

```sql
mysql> insert into room (room_name, deleted_flg, created_timestamp, created_user, updated_timestamp, updated_user) values ('test 82', 0, current_timestamp, 'test', current_timestamp, 'test');
```

```sql
mysql> select * from room;
```

```sql
mysql> insert into room (room_name, deleted_flg, created_timestamp, created_user, updated_timestamp, updated_user) values ('東京', 0, current_timestamp, 'test', current_timestamp, 'test');
```

![](image/Mahjong/1646538361565.png)

### Rest Cient

GET http://localhost:8088/v1/room/all HTTP/1.1
x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=

{"iss":"iss", "sub":"sub", "username":"username", "bizGroup":"bizGroup", "customParam":"customParam"}

POST http://localhost:8088/v1/shuffled HTTP/1.1
content-type: application/json
request-id: test-id
x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=

{"gameId": 1}

POST http://localhost:8088/v1/room/new HTTP/1.1
content-type: application/json
request-id: test-id
x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=

{"roomName": "testRoom23"}

POST http://localhost:8088/v1/room/new HTTP/1.1
content-type: application/json
request-id: request-id

"test room name"

POST http://localhost:8088/v1/room/new HTTP/1.1
content-type: application/json
request-id: request-id

東京ドーム

POST http://localhost:8088/v1/room/new HTTP/1.1
content-type: application/json
request-id: request-id

東京ドーム `<br/>`第一号

Schedule:

```mermaid
gantt
   title register player and list all players
   dateFormat YYYY-MM-DD
   axisFormat %m/%d
   
   section player api
      list all players: list_all_players, 2022-03-06, 1d
      create new player: create_new_player, after list_all_players, 1d
      junit for list: junit_for_list, after create_new_player, 2d
      junit for create: junit_for_create, after junit_for_list, 2d
  
   section game api
      list all games for a room: list_all_games_for_a_room, 2022-03-06, 1d
      create new game for room: create_new_game_for_room, after list_all_games_for_a_room, 1d
      junit for list: junit_for_list, after create_new_game_for_room, 2d
      junit for create: junit_for_create, after junit_for_list, 2d
```

registerObjectMappersForType

### Majhong game

- [X] Player registration
  - [X] API
  - [X] Web
- [X] Open a room
- [ ] Begin a game
- [ ] Play a game

### github

1344161724@qq.com/c_ada59b6

javastudyaccount/javastudy123

### mvn test

# Run all the unit test classes.

$ mvn test

# Run a single test class.

$ mvn -Dtest=TestApp1 test

# Run multiple test classes.

$ mvn -Dtest=TestApp1,TestApp2 test

# Run a single test method from a test class.

$ mvn -Dtest=TestApp1#methodname test

# Run all test methods that match pattern 'testHello*' from a test class.

$ mvn -Dtest=TestApp1#testHello* test

# Run all test methods match pattern 'testHello*' and 'testMagic*' from a test class.

$ mvn -Dtest=TestApp1#testHello*+testMagic* test

$ mvn clean test -Djacoco.skip=true


[How to reload templates without restarting the spring boot application?](https://github.com/thymeleaf/thymeleaf/issues/614)


[mysql.md](./mysql.md)

[er.md](./er.md)


```mermaid
graph LR
start(enter game) 
a{players == 4} 
b[waiting players]
c[ready to sit]
d{sitted?}

e{sitted == 4}
f[waiting sitted]
g[diced?]
i{diced == 4}
h[ready to dice]
j[waiting to dice]
k{dealer dicided?}
k1{is max?}
k2[wait others redice]
l[redice]
m[shuffle]
n[dealer dice]
o[player at starting wall dice]
p[dealer draw]

start --> a
a --no--> b
b --enter game--> a
a --yes--> d
d --yes--> e
e --no--> f
f --sitted--> e
d --no--> c
c --sitted--> d
e --yes--> g
g --no--> h
g ---yes--> i
h --diced--> g
i --no--> j
j --diced--> i
i --yes--> k

k --no--> k1
k1 --no--> k2
k2 --> k
k1 --yes--> l
l --diced--> k
k --yes--> m
m --> n
n --> o
o --> p
```
