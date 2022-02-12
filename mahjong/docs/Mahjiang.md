 [mahjong4j github](https://github.com/mahjong4j/mahjong4j.git)
 
## Beginner
Make mvn project with multiple modules
1. Google searh "mnv springboot create multiproject"
2. [Creating a Multi Module Project](https://spring.io/guides/gs/multi-module/)
3. $ `mkdir mahjong`
4. $ `cd mahjong`
1. $ `mkdir library`
2. $ `mkdir application`
1. $ `mkdir -p application/src/main/java/jp/btsol/mahjong/application`
2. $ `mkdir -p library/src/main/java/jp/btsol/mahjong/service/`
1. $ `mkdir -p library/src/test/java/jp/btsol/mahjong/service/`
3. $ `mvn install && mvn spring-boot:run -pl application`
1. http://localhost:8088/
 ![](image/Mahjiang/1644042802684.png)
1. $ `git init`
2. $ `git add .`
1. $ `git commit -m init`

mvn package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

[Spring Bootとは? Spring BootでWebアプリ開発を始めるために必要な知識を紹介](https://i-common-tech.jp/column/940)
    [Spring BootでThymeleafを使ってhello worldを表示させる方法には？](https://i-common-tech.jp/column/940#Spring+Boot%E3%81%A7Thymeleaf%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6hello+world%E3%82%92%E8%A1%A8%E7%A4%BA%E3%81%95%E3%81%9B%E3%82%8B%E6%96%B9%E6%B3%95%E3%81%AB%E3%81%AF%EF%BC%9F)

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

[Mahjong](https://en.wikipedia.org/wiki/Mahjong)
![](image/Mahjiang/1644111265138.png)
![](image/Mahjiang/1644111180029.png)
![](image/Mahjiang/1644111030505.png)
![](image/Mahjiang/1644111367900.png)

## Mixin:
[Adding Custom Properties Using Jackson MixIns](https://medvector.github.io/programming/jackson/jackson-trick/)

[Java : Jackson による JSON デシリアライズ時の型解決方法](https://www.techscore.com/blog/2016/06/17/java-jackson-polymorphic-deserialization/)

https://audio-previews.elements.envatousercontent.com/files/294752294/preview.mp3?response-content-disposition=attachment%3B+filename%3D%22HZVG6TV-mahjong-game-1.mp3%22

## Projects
![](image/Mahjiang/1644579560635.png)

|Project|Function|
|--|--|
|web|front end|
|application|api|
|library|common library|

```plantuml
@startuml

[web]
package "api"{
    [application]-HTTP
    [library]
}
[web] --> [library] : use
[application] --> [library] : use
[web] ..> HTTP
@enduml
```

Java
    Springboot(Framework) Spring Wicket Struts 
Database Oracle MySQL Postgresql
    Hibernate
    Mybatis
RestAPI    
Thymeleaf Wicket JSP 
HTML
CSS
JS

### How to run
1. Install SpringToolSuite4
1. Start SpringToolSuite4

    ![](image/Mahjiang/1644647950208.png)
1. Select workspace
   ![](image/Mahjiang/1644648159187.png)
1. Import project
   ![](image/Mahjiang/1644648227541.png)
3. Import existing maven project
   ![](image/Mahjiang/1644648290361.png)
1. Select mahjong project
   ![](image/Mahjiang/1644648339201.png)
1. Open project explorer
   ![](image/Mahjiang/1644649057023.png)
1. Three modules imported
   ![](image/Mahjiang/1644649093770.png)   
1. Import web as project
   ![](image/Mahjiang/1644649124894.png)   
   ![](image/Mahjiang/1644649146156.png)
2. Run application
   ![](image/Mahjiang/1644648497777.png)
3. Application started at port 8088
   ![](image/Mahjiang/1644648637371.png)
1. Test a api
   http://localhost:8088/hands
   ![](image/Mahjiang/1644673441779.png)   
2. Run web
   ![](image/Mahjiang/1644649194603.png)
3. Web started at port 8089
   ![](image/Mahjiang/1644649247875.png)   
1. Test a web page
   http://localhost:8089/hands
   ![](image/Mahjiang/1644673492424.png)
   [Shuffling video](media/shuffling.mp4)
   
### Mahjong room
#### Create new room
#### Invite player
#### Enter room as player
#### Invite visitor
#### Enter room as visitor

#### List all rooms
#### Private room

### Play Mahjong
#### Sitting
#### Shuffling
#### [掷色子](https://codesandbox.io/s/zhisezi-pk3lo) to decide Zhuangjia(Dealer, East player)
#### Decide where to pick tiles
#### Pick tiles
#### Play
#### Pay
#### Record wins and loses

### Start web and application 
`cd /c/app/java/mahjong`
`chcp.com 932`
`mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true`

`cd /c/app/java/mahjong/web`
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8189"`

`cd /c/app/java/mahjong/application`
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8188"`

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
