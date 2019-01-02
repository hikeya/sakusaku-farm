package com.hik.sakusakufarm;

import java.util.Random;

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

    // ビットマップの読み込み
    public static Bitmap readBitmap(Context context, String name) {
        int resID = context
                   .getResources()
                   .getIdentifier( name
                                  ,"drawable"
                                  ,context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }
}
