package org.mahjong4j.hands;

import org.mahjong4j.tile.Tile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * 面子に関するインターフェイスです
 * 順子・刻子・槓子・対子を扱います
 *
 * @author yu1ro
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "_type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Kantsu.class, name = "_type"),
  @JsonSubTypes.Type(value = Kotsu.class, name = "_type"),
  @JsonSubTypes.Type(value = Shuntsu.class, name = "_type"),
  @JsonSubTypes.Type(value = Toitsu.class, name = "_type"),
})
public abstract class Mentsu {
    protected Tile identifierTile;

    /**
     * 面子として成立している場合true
     * 面子として成立していない場合false
     */
    protected boolean isMentsu;

    /**
     * 明X子の場合はtrue
     * 暗X子の場合はfalse
     */
    protected boolean isOpen;

    /**
     * 順子の場合は2番目の牌です
     *
     * @return どの牌で面子となっているか
     */
    public Tile getTile() {
        return identifierTile;
    }

    /**
     * 面子として成立している場合true
     * 面子として成立していない場合false
     *
     * @return 面子として成立しているか
     */
    public boolean isMentsu() {
        return isMentsu;
    }

    /**
     * 食い下がり判定用です
     * 鳴いている場合はtrueですが、
     * 暗槓の場合はfalseです。
     *
     * @return 鳴いているか
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 符計算用
     *
     * @return 各面子での加算符
     */
//    @JsonIgnore
    public abstract int getFu();
}
