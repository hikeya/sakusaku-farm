package com.hik.sakusakufarm;

public class DrawPointInfo {
    public int x, y, w, h;

    // コンストラクタ
    public DrawPointInfo(int x, int y) {
        this.x = x;
        this.y = y;
        w = 0;
        h = 0;
    }

    // コンストラクタ
    public DrawPointInfo(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
