package com.hik.sakusakufarm;

public class TitleStartButton extends Task {
    private Bitmap  bmp; // 画像
    private Rect    dst; // 描画範囲

    // コンストラクタ
    public TitleStartButton(Context cont) {
        bmp = Utility.readBitmap(cont, "t_st");
    }

    // 更新処理
    @Override
    public boolean onUpdate() {
        return super.onUpdate(); // 要は何もなしない
    }

    // 描画処理
    @Override
    public void onDrawView(Graphics gr) {
        // タイトル文字描画
        gr.drawBitmap( bmp
                      ,gr.getOriginX()+(Const.WIDTH-bmp.getWidth())/2
                      ,Const.HEIGHT-gr.getOriginY()-(bmp.getHeight()+10)*2); //+10は余白
        // 描画した範囲の取得
        dst = gr.getDstRect();
    }

    // 描画した範囲を取得
    public Rect getDstRect() {
        return dst;
    }
}
