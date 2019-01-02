package com.hik.sakusakufarm;

public class Assets {
    // 画像
    public static class Pix {
        // タイトル画像
        public static class Title {
            public static Pixmap t_bg;
            public static Pixmap t_logo;
            public static Pixmap t_btnStart;
            public static Pixmap t_btnRestart;
            public static Pixmap t_btnHighscore;
            public static Pixmap t_scrollTip1;
            public static Pixmap t_scrollTip2;
            public static Pixmap t_scrollTip3;
            public static Pixmap t_vol_on;
            public static Pixmap t_vol_off;
            public static Pixmap t_viewScore;
        }

        // UI
        public static class Ui {
            public static Pixmap btn_down_neutral;
            public static Pixmap btn_down_press;
            public static Pixmap btn_home_neutral;
            public static Pixmap btn_home_press;
            public static Pixmap btn_left_neutral;
            public static Pixmap btn_left_press;
            public static Pixmap btn_right_neutral;
            public static Pixmap btn_right_press;
            public static Pixmap btn_up_neutral;
            public static Pixmap btn_up_press;
        }
    }

    // キャラチップ
    public static enum CharaID {
        CH1("chara01"),
        CH2("chara02"),
        CH3("chara03"),
        CH4("chara04");

        public String fileName;
        public Pixmap pix;

        private CharaID(String fileName) {
            this.fileName = fileName;
        }

        // 文字列からenum定数を取得
        public static CharaID getEnum(String str) {
            CharaID[] enumArray = CharaID.values();
            for (CharaID enumStr : enumArray) {
                if (str.equals(enumStr.toString()))
                    return enumStr;
            }
            return null;
        }
    }

    // タイルチップ
    public static enum TileID {
        TILE1("grass01"),
        TILE2("grass02"),
        TILE3("grass03"),
        TILE4("ground01"),
        TILE5("ground02"),
        TILE6("ground03"),
        TILE7("ground04"),
        TILE8("himawari"),
        TILE9("mura"),
        TILE10("town"),
        TILE11("homeparts"),
        TILE12("car"),
        TILE13("fairy"),
        TILE14("shop"),
        TILE15("vagetable"),
        TILE16("wood");

        public String fileName;
        public Pixmap pix;
        public byte mode[][]; // 0:通行可、1:通行不可、2:背後通行可

        private TileID(String fileName) {
            this.fileName = fileName;
        }

        // 文字列からenum定数を取得
        public static TileID getEnum(String str) {
            TileID[] enumArray = TileID.values();
            for (TileID enumStr : enumArray) {
                if (str.equals(enumStr.toString()))
                    return enumStr;
            }
            return null;
        }
    }

    // BGM
    public static class Bgm {
        public static Music titleBGM;
        public static Music farmBGM;
    }

    // SE
    public static class Se {
        public static Sound decide;
    }
}
