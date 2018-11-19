package com.hik.sakusakufarm;

public class TitleScrollTips extends Task {
    private static final int DURING     = 80;                           // チップ配置間隔(px)
    private static final int WIDTH_BIAS = (Const.HEIGHT-Const.WIDTH)/2; // 画面横サイズ調整値(px)
    private static final int TIPS_CNT   = Const.HEIGHT/DURING+1;        // 配置チップ数
    private TitleTip[][] tips = new TitleTip[TIPS_CNT][TIPS_CNT];       // タイトルスクロールチップ集

    // チップ
    class TitleTip {
        protected Bitmap bmp; // 画像
        protected int x;      // x座標
        protected int y;      // y座標

        // コンストラクタ
        public TitleTip() {
            bmp = null;
            x   = 0;
            y   = 0;
        }
        public TitleTip(Bitmap bmp, int x, int y) {
            this.bmp = bmp;
            this.x   = x;
            this.y   = y;
        }
    }

    // コンストラクタ
    public TitleScrollTips(Context cont) {
        // スクロールチップ初期配置
        Bitmap  bmp[]  = new Bitmap[3];
        boolean setFlg = true;

        for (int i=0; i < 3; i++) {
            bmp[i] = Utility.readBitmap(cont, "t_scroll"+(i+1));
        }

        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                // チップを交互に配置していく
                if (setFlg) {
                    tips[y][x] = new TitleTip(bmp[Utility.rand(3)], (x+1)*DURING, y*DURING);
                } else {
                    tips[y][x] = null;
                }
                setFlg = !setFlg;
            }
        }
    }

    // 更新処理
    @Override
    public boolean onUpdate() {
        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                try {
                    // 左枠を超えたら右上まで移動（x座標とy座標を入れ替える）
                    if (tips[y][x].x+tips[y][x].bmp.getWidth() <= DURING) {
                        tips[y][x].x = tips[y][x].y;
                        tips[y][x].y = 0;
                    // 下枠を超えたら右上まで移動（x座標とy座標を入れ替える）
                    } else if (tips[y][x].y >= (Const.HEIGHT+DURING)) {
                        tips[y][x].y = tips[y][x].x;
                        tips[y][x].x = Const.HEIGHT+DURING;
                    }
                    // 左下に1px移動
                    tips[y][x].x--;
                    tips[y][x].y++;
                } catch (NullPointerException e) {}
            }
        }
        return true;
    }

    // 描画処理
    @Override
    public void onDrawView(Graphics gr) {
        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                try {
                    // 描画範囲外であれば描画しない
                    if (tips[y][x].x+tips[y][x].bmp.getWidth()-(WIDTH_BIAS+DURING) <= 0) continue;
                    if (tips[y][x].x >= Const.WIDTH+WIDTH_BIAS+DURING)                   continue;
                    if (tips[y][x].y+tips[y][x].bmp.getHeight()-DURING <= 0)             continue;
                    if (tips[y][x].y >= Const.HEIGHT+DURING)                             continue;
                    // チップ描画
                    gr.drawBitmap(tips[y][x].bmp, tips[y][x].x-WIDTH_BIAS-DURING, tips[y][x].y-DURING);
                } catch (NullPointerException e) {}
            }
        }
    }
}
