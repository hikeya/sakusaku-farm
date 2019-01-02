package com.hik.sakusakufarm;

// マップスクロールカメラ
public class Camera {
    // マップ全体のサイズ
    public int mapWidthCnt = 0;
    public int mapHeightCnt = 0;
    // 描画する最初のマップチップの配列上の位置
    public int arrayX = 0;
    public int arrayY = 0;
    // 描画する最初のマップチップの描画位置
    public int drawX = 0;
    public int drawY = 0;
    // プレイヤー描画位置
    public int p_drawX = 0;
    public int p_drawY = 0;

    // コンストラクタ
    public Camera() {
    }

    // マップ全体のサイズを設定
    public void setMapCnt(int mapWidthCnt, int mapHeightCnt) {
        this.mapWidthCnt = mapWidthCnt;
        this.mapHeightCnt = mapHeightCnt;
    }

    // スタンバイ
    public void standby(int playerX, int playerY) {
        // プレイヤーの位置取得
        int x = playerX;
        int y = playerY;
        x -= (Const.WIDTH - Const.TIP_SIZE) / 2;
        y -= (Const.HEIGHT - Const.TIP_SIZE) / 2;

        // x軸フレームアウト防止
        if (x < 0)
            x = 0;
        else if (x > mapWidthCnt * Const.TIP_SIZE - Const.WIDTH)
            x = mapWidthCnt * Const.TIP_SIZE - Const.WIDTH;
        // y軸フレームアウト防止
        if (y < 0)
            y = 0;
        else if (y > mapHeightCnt * Const.TIP_SIZE - Const.HEIGHT)
            y = mapHeightCnt * Const.TIP_SIZE - Const.HEIGHT;

        arrayX = x / Const.TIP_SIZE;
        arrayY = y / Const.TIP_SIZE;
        drawX = -(x % Const.TIP_SIZE);
        drawY = -(y % Const.TIP_SIZE);
        p_drawX = playerX - (arrayX * Const.TIP_SIZE + -drawX);
        p_drawY = playerY - (arrayY * Const.TIP_SIZE + -drawY);
    }
}
