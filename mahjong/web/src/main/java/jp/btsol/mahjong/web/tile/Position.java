package jp.btsol.mahjong.web.tile;

import java.awt.Point;

import lombok.Data;

/**
 * Position for tile
 * 
 * @author B&T Solutions Inc.
 *
 */
@Data
public class Position {
    /**
     * center point
     */
    private Point center;
    /**
     * angle
     */
    private double angle;
}
