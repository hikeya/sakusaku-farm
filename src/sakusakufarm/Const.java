package com.hik.sakusakufarm;

// 共通定義
public interface Const {
    // チップサイズ
    public static final short TIP_SIZE = 32;
    public static final short TIP_WIDTH_CNT = 9;
    public static final short TIP_HEIGHT_CNT = 15;

    // 画面サイズ定数
    public static final short WIDTH = TIP_SIZE * TIP_WIDTH_CNT;
    public static final short HEIGHT = TIP_SIZE * TIP_HEIGHT_CNT;

    // ファイル拡張子
    public static final String EXT_PIXMAP = ".png";
    public static final String EXT_DATA = ".dat";
    public static final String EXT_AUDIO = ".mp3";

    // アセット格納先
    public static final String PATH_DELIMITER = "/";
    public static final String ASSETS_PIXMAP = "pixmap" + PATH_DELIMITER;
    public static final String ASSETS_TITLE = ASSETS_PIXMAP + "title" + PATH_DELIMITER;
    public static final String ASSETS_CHARACTER = ASSETS_PIXMAP + "character" + PATH_DELIMITER;
    public static final String ASSETS_TILES = ASSETS_PIXMAP + "tiles" + PATH_DELIMITER;
    public static final String ASSETS_UI = ASSETS_PIXMAP + "ui" + PATH_DELIMITER;
    public static final String ASSETS_DATA = "data" + PATH_DELIMITER;
    public static final String ASSETS_MAPDATA = ASSETS_DATA + "map" + PATH_DELIMITER;
    public static final String ASSETS_TILEDATA = ASSETS_DATA + "tilemode" + PATH_DELIMITER;
    public static final String ASSETS_BGM = "bgm" + PATH_DELIMITER;
    public static final String ASSETS_SE = "se" + PATH_DELIMITER;

    // マップチップ通行制御
    public static final byte MAP_MOVE_OK = 0;
    public static final byte MAP_MOVE_NO = 1;
    public static final byte MAP_MOVE_BACK = 2;

    // 方向
    public static final byte SOUTH = 1;
    public static final byte WEST = 2;
    public static final byte EAST = 3;
    public static final byte NORTH = 4;
    public static final byte CENTER = 5;
}
