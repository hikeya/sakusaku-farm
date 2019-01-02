package com.hik.sakusakufarm;

import com.hik.sakusakufarm.Assets.CharaID;

public class CPUCharacter extends Character {
    // 歩行モード
    public enum WalkMode {
        STOP, // 静止
        WALK, // 歩く
        STEP // 足踏み
    }

    WalkMode walkMode = WalkMode.STOP;

    // コンストラクタ
    public CPUCharacter(CharaID charaId, int x, int y, byte dir, WalkMode walkMode) {
        super(charaId, x, y, dir);
        this.walkMode = walkMode;
    }

    // プレイヤーが話しかける
    public void call(int playerX, int playerY) {
        if (playerX == x) {
            if (playerY + Const.TIP_SIZE == y)
                fake(Const.NORTH);
            if (playerY == y + Const.TIP_SIZE)
                fake(Const.SOUTH);
        }
        if (playerY == y) {
            if (playerY + Const.TIP_SIZE == y)
                fake(Const.NORTH);
            if (playerY == y + Const.TIP_SIZE)
                fake(Const.SOUTH);
        }
    }
}
