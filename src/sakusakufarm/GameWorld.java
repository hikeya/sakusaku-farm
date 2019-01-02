package com.hik.sakusakufarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hik.sakusakufarm.Assets.CharaID;
import com.hik.sakusakufarm.Assets.TileID;

public class GameWorld {
    private int mapWidthCnt = 0; // マップの横チップ数
    private int mapHeightCnt = 0; // マップの縦チップ数
    private Camera camera = new Camera(); // スクロールカメラ
    private TileTip tiles[][]; // 第1レイヤー
    private TileTip objects[][]; // 第2レイヤー
    private GameEvent gameEvents[][]; // イベントレイヤー
    private Character player;
    private int money;
    public boolean isGameClear;

    // マップレイヤー番号
    private static final byte LAYER1 = 1;
    private static final byte LAYER2_BACK = 2;
    private static final byte LAYER2_FRONT = 3;

    // コンストラクタ
    public GameWorld(FileIO files) {
        loadMap(files, SaveData.getMapNum());
        player = new Character(CharaID.CH1, SaveData.getPlayerX(), SaveData.getPlayerY(), SaveData.getPlayerDir());
        camera.setMapCnt(mapWidthCnt, mapHeightCnt);
    }

    // 更新処理
    public void update(byte btnDir, float deltaTime) {
        // 上下左右ボタンの場合
        if (btnDir >= Const.SOUTH && btnDir <= Const.NORTH) {
            // 歩行中でなければ歩行処理を開始する
            if (!player.isWalking)
                playerWalkStart(btnDir);
        }
        player.update(deltaTime); // 歩行アニメーション更新
        camera.standby(player.x, player.y); // カメラ位置設定
    }

    // プレイヤー歩行開始処理
    private void playerWalkStart(byte btnDir) {
        player.fake(btnDir); // 方向転換

        int x = player.x / Const.TIP_SIZE;
        int y = player.y / Const.TIP_SIZE;
        // 移動方向に1マス移動を試みる
        switch (btnDir) {
        case Const.NORTH:
            y--;
            break;
        case Const.EAST:
            x++;
            break;
        case Const.WEST:
            x--;
            break;
        case Const.SOUTH:
            y++;
            break;
        }

        // 移動先がマップ範囲内の場合
        if (x >= 0 && x < mapWidthCnt && y >= 0 && y < mapHeightCnt) {
            TileID tileId = tiles[y][x].tileId;
            int modeX = tiles[y][x].x - 1;
            int modeY = tiles[y][x].y - 1;
            // 第1レイヤー上の移動先マスが移動可能の場合
            if ((tileId == null)
                    || (tileId.mode[modeY][modeX] == Const.MAP_MOVE_OK)
                    || (tileId.mode[modeY][modeX] == Const.MAP_MOVE_BACK)) {
                tileId = objects[y][x].tileId;
                modeX = objects[y][x].x - 1;
                modeY = objects[y][x].y - 1;
                // 第2レイヤー上の移動先マスが移動可能の場合
                if ((tileId == null)
                        || (tileId.mode[modeY][modeX] == Const.MAP_MOVE_OK)
                        || (tileId.mode[modeY][modeX] == Const.MAP_MOVE_BACK)) {
                    player.walk(btnDir, 1); // 1マス歩く
                }
            }
        }
    }

    // 描画処理
    public void present(Graphics g, float deltaTime) {
        // 第1レイヤー描画
        drawMapLayer(g, tiles, LAYER1);
        // 第2レイヤー（キャラ背面）描画
        drawMapLayer(g, objects, LAYER2_BACK);
        // プレイヤー描画
        player.present(g, deltaTime, camera.p_drawX, camera.p_drawY);
        // 第2レイヤー（キャラ前面）描画
        drawMapLayer(g, objects, LAYER2_FRONT);
    }

    // マップレイヤー描画
    private void drawMapLayer(Graphics g, TileTip tileTip[][], byte layerNum) {
        // 配列位置
        int arrayX = camera.arrayX;
        int arrayY = camera.arrayY;
        // 描画する位置
        int drawX = camera.drawX;
        int drawY = camera.drawY;

        // マップの描画
        while (true) {
            if (drawY >= Const.HEIGHT)
                break;
            while (true) {
                if (drawX >= Const.WIDTH) {
                    arrayX = camera.arrayX;
                    drawX = camera.drawX;
                    break;
                }
                TileID tileId = tileTip[arrayY][arrayX].tileId;
                if (tileId != null) {
                    if (layerNum == LAYER1) {
                        // 第1レイヤー描画
                        tileTip[arrayY][arrayX].present(g, drawX, drawY);
                    } else {
                        int modeX = tileTip[arrayY][arrayX].x - 1;
                        int modeY = tileTip[arrayY][arrayX].y - 1;
                        if (layerNum == LAYER2_BACK) {
                            if (tileId.mode[modeY][modeX] != Const.MAP_MOVE_BACK) {
                                // 第2レイヤー（キャラ背面）描画
                                tileTip[arrayY][arrayX].present(g, drawX, drawY);
                            }
                        } else if (layerNum == LAYER2_FRONT) {
                            if (tileId.mode[modeY][modeX] == Const.MAP_MOVE_BACK) {
                                // 第2レイヤー（キャラ前面）描画
                                tileTip[arrayY][arrayX].present(g, drawX, drawY);
                            }
                        }
                    }
                }
                arrayX++;
                drawX += Const.TIP_SIZE;
            }
            arrayY++;
            drawY += Const.TIP_SIZE;
        }
    }

    // チップ同士の接触判定
    private boolean isOverlapTips(int x1, int y1, int x2, int y2) {
        if ((x1 + Const.TIP_SIZE - 1 >= x2) && (x1 < x2 + Const.TIP_SIZE)
                && (y1 + Const.TIP_SIZE - 1 >= y2) && (y1 < y2 + Const.TIP_SIZE))
            return true;
        return false;
    }

    // ゲームデータ読み込み
    private void loadMap(FileIO files, int mapNum) {
        BufferedReader in = null;
        String mapSizeWk[] = new String[2];
        try {
            // マップのサイズ
            in = new BufferedReader(new InputStreamReader(files.readAsset(Const.ASSETS_MAPDATA + "map" + mapNum
                    + "_size" + Const.EXT_DATA)));
            mapSizeWk = in.readLine().split(",", 0);
            mapWidthCnt = Integer.parseInt(mapSizeWk[0]);
            mapHeightCnt = Integer.parseInt(mapSizeWk[1]);
            tiles = new TileTip[mapHeightCnt][mapWidthCnt];
            objects = new TileTip[mapHeightCnt][mapWidthCnt];
            in.close();

            // マップデータ読み込み
            readMapData(files, Const.ASSETS_MAPDATA + "map" + mapNum + "_tiles" + Const.EXT_DATA, in, mapWidthCnt,
                    mapHeightCnt, tiles);
            readMapData(files, Const.ASSETS_MAPDATA + "map" + mapNum + "_objects" + Const.EXT_DATA, in, mapWidthCnt,
                    mapHeightCnt, objects);

        } catch (IOException e) {
            throw new RuntimeException("マップデータが読み込めません。");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("IOException", "ファイルIOを閉じるのに失敗しました。");
            }
        }
    }

    // マップデータ読み込み
    private void readMapData(FileIO files, String fileName, BufferedReader in
            , int mapWidthCnt, int mapHeightCnt, TileTip setData[][]) throws IOException {
        in = new BufferedReader(new InputStreamReader(files.readAsset(fileName)));
        for (int y = 0; y < mapHeightCnt; y++) {
            String mapDataWk1[] = in.readLine().split("\\|", 0);
            for (int x = 0; x < mapWidthCnt; x++) {
                String mapDataWk2[] = mapDataWk1[x].split(",", 0);
                setData[y][x] = new TileTip(TileID.getEnum("TILE" + mapDataWk2[0])
                        , Integer.parseInt(mapDataWk2[1])
                        , Integer.parseInt(mapDataWk2[2]));
            }
        }
        in.close();
    }
}
