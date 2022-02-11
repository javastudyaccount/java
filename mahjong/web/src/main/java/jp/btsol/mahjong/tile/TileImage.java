package jp.btsol.mahjong.tile;

import org.mahjong4j.tile.Tile;

public enum TileImage {
    M1(Tile.M1, "Characters1-75px-MJw1-.svg.png"),
    M2(Tile.M2, "Characters2-75px-MJw2-.svg.png"),
    M3(Tile.M3, "Characters3-75px-MJw3-.svg.png"),
    M4(Tile.M4, "Characters4-75px-MJw4-.svg.png"),
    M5(Tile.M5, "Characters5-75px-MJw5-.svg.png"),
    M6(Tile.M6, "Characters6-75px-MJw6-.svg.png"),
    M7(Tile.M7, "Characters7-75px-MJw7-.svg.png"),
    M8(Tile.M8, "Characters8-75px-MJw8-.svg.png"),
    M9(Tile.M9, "Characters9-75px-MJw9-.svg.png"),

    P1(Tile.P1, "Dots1-75px-MJt1-.svg.png"),
    P2(Tile.P2, "Dots2-75px-MJt2-.svg.png"),
    P3(Tile.P3, "Dots3-75px-MJt3-.svg.png"),
    P4(Tile.P4, "Dots4-75px-MJt4-.svg.png"),
    P5(Tile.P5, "Dots5-75px-MJt5-.svg.png"),
    P6(Tile.P6, "Dots6-75px-MJt6-.svg.png"),
    P7(Tile.P7, "Dots7-75px-MJt7-.svg.png"),
    P8(Tile.P8, "Dots8-75px-MJt8-.svg.png"),
    P9(Tile.P9, "Dots9-75px-MJt9-.svg.png"),

    S1(Tile.S1, "Bamboo1-75px-MJs1-.svg.png"),
    S2(Tile.S2, "Bamboo2-75px-MJs2-.svg.png"),
    S3(Tile.S3, "Bamboo3-75px-MJs3-.svg.png"),
    S4(Tile.S4, "Bamboo4-75px-MJs4-.svg.png"),
    S5(Tile.S5, "Bamboo5-75px-MJs5-.svg.png"),
    S6(Tile.S6, "Bamboo6-75px-MJs6-.svg.png"),
    S7(Tile.S7, "Bamboo7-75px-MJs7-.svg.png"),
    S8(Tile.S8, "Bamboo8-75px-MJs8-.svg.png"),
    S9(Tile.S9, "Bamboo9-75px-MJs9-.svg.png"),

    TON(Tile.TON, "East-75px-MJf1-.svg.png"),//東
    NAN(Tile.NAN, "South-75px-MJf2-.svg.png"),//南
    SHA(Tile.SHA, "West-75px-MJf3-.svg.png"),//西
    PEI(Tile.PEI, "North-75px-MJf4-.svg.png"),//北

    HAK(Tile.HAK, "White-75px-MJd3-.svg.png"),//白
    HAT(Tile.HAT, "Green-75px-MJd2-.svg.png"),//発
    CHN(Tile.CHN, "Red-75px-MJd1-.svg.png");//中
    private Tile tile;
    private String image;
    TileImage(Tile tile, String image) {
        this.tile = tile;
        this.image = image;
    }
    public Tile getTile() {
		return tile;
	}
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public static TileImage valueOf(Tile c) {
        return TileImage.values()[c.getCode()];
    }
}
