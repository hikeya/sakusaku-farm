package com.hik.sakusakufarm;

import java.util.Random;

// 役に立つクラス
public class Utility {

    // スリープ
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {}
    }

    // 乱数の取得
    private static Random rand = new Random();
    public static int rand(int num) {
        return (rand.nextInt()>>>1)%num;
    }

    // タッチ範囲判定
    public static boolean isBounds(TouchEvent e, int x, int y, int width, int height) {
        if (e.x >= x && e.x < x + width &&
            e.y >= y && e.y < y + height)
            return true;
        else
            return false;
    }
}
