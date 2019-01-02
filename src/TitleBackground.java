package com.hik.sakusakufarm;

public class TitleBackground extends Task {
    private Bitmap bmp; // 画像

    // コンストラクタ
    public TitleBackground(Context cont) {
        // シーン画像の読み込み
        bmp = Utility.readBitmap(cont, "t_bg");
    }

    // 更新処理
    @Override
    public boolean onUpdate() {
        return super.onUpdate(); // 要は何もしない
    }

    // 描画処理
    @Override
    public void onDrawView(Graphics gr) {
        // タイトル画像を全画面描画
        gr.drawBitmap(bmp, 0, 0, Const.WIDTH, Const.HEIGHT);
    }
}
