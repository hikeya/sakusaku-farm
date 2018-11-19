package com.hik.sakusakufarm;

import com.hik.sakusakufarm.Assets.CharaID;

// キャラクター
public class Character {
    // キャラID
    private CharaID charaId;
    // 座標
    public int x, y;
    // 向いている方向
    private byte dir;
    // デルタ時間
    private float tickTime = 0;
    // 更新間隔
    private static final float TICK = 0.05f;
    // 歩行中フラグ
    public boolean isWalking = false;
    // 移動するマス数
    private int moveTipCnt = 0;
    // 移動したコマ数
    private int walkedCnt = 0;
    // 足の向き
    private int legX = Const.TIP_SIZE;
    // 1マス移動当たりのコマ数
    private static final short WALK_SLICE_CNT = 4;
    // 1コマ当たりの移動距離
    private static final short WALK_DISTANCE = Const.TIP_SIZE / WALK_SLICE_CNT;

    // コンストラクタ
    public Character(CharaID charaId, int x, int y, byte dir) {
        this.charaId = charaId;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    // 方向転換
    public void fake(byte dir) {
        this.dir = dir;
    }

    // 歩く
    public void walk(byte dir, int moveTipCnt) {
        this.dir = dir;
        this.moveTipCnt = moveTipCnt;
        isWalking = true;
    }

    // 更新処理
    public void update(float deltaTime) {
        // 歩行中
        if (isWalking) {
            tickTime += deltaTime;
            while (tickTime > TICK) {
                tickTime -= TICK;
                switch (dir) {
                case Const.NORTH:
                    y = y - WALK_DISTANCE;
                    break;
                case Const.EAST:
                    x = x + WALK_DISTANCE;
                    break;
                case Const.SOUTH:
                    y = y + WALK_DISTANCE;
                    break;
                case Const.WEST:
                    x = x - WALK_DISTANCE;
                    break;
                }
                // 指定コマ数歩いたら終了
                if (++walkedCnt >= WALK_SLICE_CNT * moveTipCnt) {
                    legX = Const.TIP_SIZE; // 直立
                    walkedCnt = 0;
                    isWalking = false;
                    break;
                }
                if (walkedCnt / moveTipCnt <= WALK_SLICE_CNT / 2)
                    legX = 0; // 右足前
                else if (walkedCnt / moveTipCnt > WALK_SLICE_CNT / 2)
                    legX = Const.TIP_SIZE * 2; // 左足前
            }
        }
    }

    // 描画処理
    public void present(Graphics g, float deltaTime, int targetX, int targetY) {
        g.drawPixmap(charaId.pix
                , targetX
                , targetY
                , legX
                , (dir - 1) * Const.TIP_SIZE
                , Const.TIP_SIZE
                , Const.TIP_SIZE);
    }
}
