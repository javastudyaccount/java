package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RoomName {
    /**
     * roomName String
     */
    @NotBlank
    @Size(max = 50)
    private String roomName;
}
