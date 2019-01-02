package com.hik.sakusakufarm;

public class TitleName extends Task {
    private Bitmap  bmp;        // 画像
    private int     tScrollIdx; // タイトル文字スクロール（Y座標）
    private boolean tScrollFlg; // タイトル文字スクロール（真:下にスクロール, 偽:上にスクロール）

    // コンストラクタ
    public TitleName(Context cont) {
        bmp = Utility.readBitmap(cont, "t_name");
        tScrollIdx = 0;
        tScrollFlg = true;
    }

    // 更新処理
    @Override
    public boolean onUpdate() {
        // タイトル文字スクロール
        if (tScrollFlg) tScrollIdx ++;
        else tScrollIdx --;
        if (tScrollIdx == 5 || tScrollIdx == 0) tScrollFlg = !tScrollFlg;

        return true;
    }

    // 描画処理
    @Override
    public void onDrawView(Graphics gr) {
        // タイトル文字描画
        gr.drawBitmap( bmp
                      ,gr.getOriginX()+(Const.WIDTH-bmp.getWidth())/2
                      ,gr.getOriginY()+10+tScrollIdx ); //+10は余白
    }
}
