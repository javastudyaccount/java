[mahjong4j github](https://github.com/mahjong4j/mahjong4j.git)
[Java study](https://github.com/javastudyaccount/java)

## Beginner

Make mvn project with multiple modules

1. Google searh "mnv springboot create multiproject"
2. [Creating a Multi Module Project](https://spring.io/guides/gs/multi-module/)
3. $ `mkdir mahjong`
4. $ `cd mahjong`
5. $ `mkdir library`
6. $ `mkdir application`
7. $ `mkdir -p application/src/main/java/jp/btsol/mahjong/application`
8. $ `mkdir -p library/src/main/java/jp/btsol/mahjong/service/`
9. $ `mkdir -p library/src/test/java/jp/btsol/mahjong/service/`
10. $ `mvn install && mvn spring-boot:run -pl application`
11. http://localhost:8088/
    ![](image/Mahjiang/1644042802684.png)
12. $ `git init`
13. $ `git add .`
14. $ `git commit -m init`

`mvn package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true`

`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8188" -Dmaven.test.skip=true -Dmaven.javadoc.skip=true`

[Mahjong](https://en.wikipedia.org/wiki/Mahjong)
![](image/Mahjiang/1644111265138.png)

![](image/Mahjiang/1644111180029.png)
![](image/Mahjiang/1644111030505.png)
![](image/Mahjiang/1644111367900.png)

[how-to-run.md](./how-to-run.md)

## TODO
### Home page
- [x] Show all users in sidebar
- [ ] Scroll users in sidebar
### Mahjong room

- [x]  Create new room

- [x]  Invite player

- [x]  Enter room as player

- [ ]  Invite visitor

- [ ]  Enter room as visitor

- [x]  List all rooms

- [ ]  Private room

### Play Mahjong

- [x]  Sitting

- [x]  Shuffling

- [x]  [掷色子](https://www.goocode.net/demo/dice/index.php) to decide Zhuangjia(Dealer, East player)

- [x]  Decide where to pick tiles

- [ ]  Pick tiles

- [ ]  Play

- [ ]  Pay

- [ ]  Record wins and loses

[简单的麻将ai算法](https://doc.xuwenliang.com/docs/ai/1495)

### 麻将基本术语中英文对照表

| 中文             | 英文                                   | 解释                           |
| ---------------- | -------------------------------------- | ------------------------------ |
| 麻将(麻雀)       | Mahjongg / Mahjong                     |                                |
| 张               | Tile                                   | 一张麻将牌的牌面               |
| 花               | Flower                                 | 春、夏、秋、冬、梅、兰、竹、菊 |
| 字牌(役牌)       | Honor                                  | 东、南、西、北、中、发、白     |
| 花色(配牌)       | Suit                                   | 筒、条、万                     |
| 数字             | Rank                                   | 一~九                          |
| 箭牌             | Dragon                                 | 中、发、白                     |
| 风牌             | Wind                                   | 东、南、西、北                 |
| 筒牌             | Dot                                    | 一筒~九筒                      |
| 条牌             | Bamboo                                 | 一条~九条                      |
| 万牌             | Character                              | 一万~九万                      |
| 春、夏、秋、冬   | Spring / Summer / Autumn / Winter      |                                |
| 梅、兰、竹、菊   | Plum / Orchid / Bamboo / Chrysanthemum |                                |
| 中、发、白       | Red / Green / White Dragon             |                                |
| 东、南、西、北   | East / South / West / North Wind       |                                |
| 门风(自风)       | Dealer's Wind                          |                                |
| 场风(圈风)       | Prevailing Wind                        |                                |
| 庄家             | Dealer / Banker                        |                                |
| 发牌             | Deal                                   |                                |
| 骰子             | Die (pl. Dice)                         |                                |
| 洗牌             | Shuffle                                |                                |
| 牌墙             | Wall                                   |                                |
| 牌墩             | Stack                                  | 牌墙中的上下两张牌组成一墩     |
| 牌桌             | Board                                  |                                |
| 和(胡)牌         | Win                                    |                                |
| 和(胡)张         | Winning Tile                           |                                |
| 荒牌(流局)       | Draw                                   |                                |
| 摸牌             | Draw                                   |                                |
| 打牌             | Discard                                |                                |
| 听牌             | Ready Hand                             |                                |
| 混牌(百搭)       | Joker                                  |                                |
| 玩家             | Player                                 |                                |
| 一组             | Meld                                   | 一个顺子、刻子或杠子           |
| 将牌(雀头、对子) | Eyes / Pair                            |                                |
| 刻子             | Triplet                                |                                |
| 顺子             | Sequence                               |                                |
| 明杠、暗杠       | Exposed / Concealed Kong               |                                |
| 碰、杠、吃       | Pong / Kong / Chow                     |                                |
| 数番             | Scoring                                |                                |
| 番数             | Points                                 |                                |

![1662378410126](image/Mahjong/1662378410126.png)
### 役名一覧
http://www.jmsa.jp/www/rule1.html
[役の一覧.pdf](./役の一覧.pdf)
