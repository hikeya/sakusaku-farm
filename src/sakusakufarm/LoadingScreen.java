package com.hik.sakusakufarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.hik.sakusakufarm.Assets.CharaID;
import com.hik.sakusakufarm.Assets.TileID;

public class LoadingScreen extends Screen {

    // コンストラクタ
    public LoadingScreen(Game game) {
        super(game);
    }

    // 更新処理
    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        try {
            // タイトル画像読み込み
            for (Field field : Assets.Pix.Title.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(Assets.Pix.Title.class
                        , g.newPixmap(Const.ASSETS_TITLE
                                + field.getName()
                                + Const.EXT_PIXMAP
                                , PixmapFormat.ARGB4444));
            }
            // UI読み込み
            for (Field field : Assets.Pix.Ui.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(Assets.Pix.Ui.class
                        , g.newPixmap(Const.ASSETS_UI
                                + field.getName()
                                + Const.EXT_PIXMAP
                                , PixmapFormat.ARGB4444));
            }
            // BGM読み込み
            for (Field field : Assets.Bgm.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(Assets.Bgm.class
                        , game.getAudio().newMusic(Const.ASSETS_BGM + field.getName() + Const.EXT_AUDIO));

            }
            // 効果音読み込み
            for (Field field : Assets.Se.class.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(Assets.Se.class
                        , game.getAudio().newSound(Const.ASSETS_SE + field.getName() + Const.EXT_AUDIO));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("アセットクラスの属性にアクセスできません。");
        }

        // キャラチップ読み込み
        CharaID[] charaIdArray = CharaID.values();
        for (CharaID charaId : charaIdArray) {
            charaId.pix = g.newPixmap(Const.ASSETS_CHARACTER + charaId.fileName + Const.EXT_PIXMAP,
                    PixmapFormat.ARGB4444);
        }

        // タイルチップ読み込み
        TileID[] tileIdArray = TileID.values();
        for (TileID tileId : tileIdArray) {
            tileId.pix = g.newPixmap(Const.ASSETS_TILES + tileId.fileName + Const.EXT_PIXMAP, PixmapFormat.ARGB4444);
            loadTileMode(tileId);
        }

        // セーブファイル読み込み
        Settings.load(game.getFileIO());

        // タイトル画面読み込み
        game.setScreen(new TitleScreen(game));
    }

    // タイルモードファイル読み込み
    private void loadTileMode(TileID tileId) {
        int width = tileId.pix.getWidth() / Const.TIP_SIZE;
        int height = tileId.pix.getHeight() / Const.TIP_SIZE;
        tileId.mode = new byte[height][width];
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            game.getFileIO().readAsset(Const.ASSETS_TILEDATA + tileId.fileName + Const.EXT_DATA)
                    )
                    );
            for (int y = 0; y < height; y++) {
                String tileModeData[] = in.readLine().split(",", 0);
                for (int x = 0; x < width; x++) {
                    tileId.mode[y][x] = Byte.parseByte(tileModeData[x]);
                }
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("アセット内のデータファイル:'" + tileId.fileName + "'を読み込めません。");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void present(float deltaTime) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
