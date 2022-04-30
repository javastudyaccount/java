package jp.btsol.mahjong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MahjongMessage {
    /**
     * name String
     */
    private String name;
    /**
     * message String
     */
    private String message;
}
