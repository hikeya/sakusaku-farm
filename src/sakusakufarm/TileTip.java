package com.hik.sakusakufarm;

import com.hik.sakusakufarm.Assets.TileID;

public class TileTip {
    public TileID tileId; // タイルID
    public int x, y; // 画像内の座標

    // コンストラクタ
    public TileTip(TileID tileId, int x, int y) {
        this.tileId = tileId;
        this.x = x;
        this.y = y;
    }

    // 描画処理
    public void present(Graphics g, int targetX, int targetY) {
        g.drawPixmap(tileId.pix
                , targetX
                , targetY
                , Const.TIP_SIZE * (x - 1)
                , Const.TIP_SIZE * (y - 1)
                , Const.TIP_SIZE
                , Const.TIP_SIZE);
    }
}
