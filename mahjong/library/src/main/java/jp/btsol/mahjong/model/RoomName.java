package jp.btsol.mahjong.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomName {
    /**
     * roomName String
     */
    @NotBlank
    @Size(max = 50)
    private String roomName;
}
