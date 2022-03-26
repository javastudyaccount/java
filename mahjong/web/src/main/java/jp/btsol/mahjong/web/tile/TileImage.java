package jp.btsol.mahjong.web.tile;

import java.util.Arrays;

import org.mahjong4j.tile.Tile;

/**
 * tile image
 * 
 * @author B&T Soutions Inc.
 *
 */
public enum TileImage {
    /**
     * MAN1
     */
    M1(Tile.M1, "Characters1-75px-MJw1-.svg.png"),
    /**
     * MAN2
     */
    M2(Tile.M2, "Characters2-75px-MJw2-.svg.png"),
    /**
     * MAN3
     */
    M3(Tile.M3, "Characters3-75px-MJw3-.svg.png"),
    /**
     * MAN4
     */
    M4(Tile.M4, "Characters4-75px-MJw4-.svg.png"),
    /**
     * MAN5
     */
    M5(Tile.M5, "Characters5-75px-MJw5-.svg.png"),
    /**
     * MAN6
     */
    M6(Tile.M6, "Characters6-75px-MJw6-.svg.png"),
    /**
     * MAN7
     */
    M7(Tile.M7, "Characters7-75px-MJw7-.svg.png"),
    /**
     * MAN8
     */
    M8(Tile.M8, "Characters8-75px-MJw8-.svg.png"),
    /**
     * MAN9
     */
    M9(Tile.M9, "Characters9-75px-MJw9-.svg.png"),
    /**
     * PIN1
     */
    P1(Tile.P1, "Dots1-75px-MJt1-.svg.png"),
    /**
     * PIN2
     */
    P2(Tile.P2, "Dots2-75px-MJt2-.svg.png"),
    /**
     * PIN3
     */
    P3(Tile.P3, "Dots3-75px-MJt3-.svg.png"),
    /**
     * PIN4
     */
    P4(Tile.P4, "Dots4-75px-MJt4-.svg.png"),
    /**
     * PIN5
     */
    P5(Tile.P5, "Dots5-75px-MJt5-.svg.png"),
    /**
     * PIN6
     */
    P6(Tile.P6, "Dots6-75px-MJt6-.svg.png"),
    /**
     * PIN7
     */
    P7(Tile.P7, "Dots7-75px-MJt7-.svg.png"),
    /**
     * PIN8
     */
    P8(Tile.P8, "Dots8-75px-MJt8-.svg.png"),
    /**
     * PIN9
     */
    P9(Tile.P9, "Dots9-75px-MJt9-.svg.png"),
    /**
     * SOH1
     */
    S1(Tile.S1, "Bamboo1-75px-MJs1-.svg.png"),
    /**
     * SOH2
     */
    S2(Tile.S2, "Bamboo2-75px-MJs2-.svg.png"),
    /**
     * SOH3
     */
    S3(Tile.S3, "Bamboo3-75px-MJs3-.svg.png"),
    /**
     * SOH4
     */
    S4(Tile.S4, "Bamboo4-75px-MJs4-.svg.png"),
    /**
     * SOH5
     */
    S5(Tile.S5, "Bamboo5-75px-MJs5-.svg.png"),
    /**
     * SOH6
     */
    S6(Tile.S6, "Bamboo6-75px-MJs6-.svg.png"),
    /**
     * SOH7
     */
    S7(Tile.S7, "Bamboo7-75px-MJs7-.svg.png"),
    /**
     * SOH8
     */
    S8(Tile.S8, "Bamboo8-75px-MJs8-.svg.png"),
    /**
     * SOH9
     */
    S9(Tile.S9, "Bamboo9-75px-MJs9-.svg.png"),
    /**
     * TON
     */
    TON(Tile.TON, "East-75px-MJf1-.svg.png"), // 東
    /**
     * NAN
     */
    NAN(Tile.NAN, "South-75px-MJf2-.svg.png"), // 南
    /**
     * SHA
     */
    SHA(Tile.SHA, "West-75px-MJf3-.svg.png"), // 西
    /**
     * PEI
     */
    PEI(Tile.PEI, "North-75px-MJf4-.svg.png"), // 北
    /**
     * HAK
     */
    HAK(Tile.HAK, "White-75px-MJd3-.svg.png"), // 白
    /**
     * HAT
     */
    HAT(Tile.HAT, "Green-75px-MJd2-.svg.png"), // 発
    /**
     * CHN
     */
    CHN(Tile.CHN, "Red-75px-MJd1-.svg.png"); // 中

    /**
     * tile
     */
    private Tile tile;
    /**
     * image
     */
    private String image;

    /**
     * Constructor
     * 
     * @param tile  Tile
     * @param image String
     */
    TileImage(Tile tile, String image) {
        this.tile = tile;
        this.image = image;
    }

    /**
     * get Tile
     * 
     * @return Tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * set Tile
     * 
     * @param tile Tile
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * get image
     * 
     * @return String
     */
    public String getImage() {
        return image;
    }

    /**
     * set image
     * 
     * @param image String
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * get TileImage from Tile
     * 
     * @param c Tile
     * @return TileImage
     */
    public static TileImage valueOf(Tile c) {
        return TileImage.values()[c.getCode()];
    }

    /**
     * get images for tiles
     * 
     * @return String[] array of images for tiles
     */
    public static String[] images() {
        return Arrays.stream(TileImage.values()).map(i -> i.getImage()).toArray(String[]::new);
    }
}
