package com.hik.sakusakufarm;

// スクロールチップ
public class ScrollTipSheet {
    // デルタ時間
    private float tickTime = 0;
    // 更新間隔
    private static final float TICK = 0.1f;
    // チップ配置間隔(px)
    private static final int DURING = 80;
    // チップ移動間隔(px)
    private static final int MOVE_PX = 8; // チップ配置間隔(px)の約数である事
    // 画面横サイズ調整値(px)
    private static final int WIDTH_OFFSET = (Const.HEIGHT - Const.WIDTH) / 2;
    // 配置チップ数
    private static final int TIPS_CNT = Const.HEIGHT / DURING + 1;
    // タイトルチップ配置
    private ScrollTip[][] tips = new ScrollTip[TIPS_CNT][TIPS_CNT];

    // チップ
    class ScrollTip {
        protected Pixmap pix; //画像
        protected int x = 0; // x座標（画像の右下の位置）
        protected int y = 0; // y座標（画像の右下の位置）

        // コンストラクタ
        public ScrollTip(Pixmap pix, int x, int y) {
            this.pix = pix;
            this.x = x;
            this.y = y;
        }

        // 座標入れ替え
        public void reverseTip() {
            int wk = x;
            x = y;
            y = wk;
        }
    }

    // コンストラクタ
    public ScrollTipSheet() {
        boolean setFlg = true;
        // タイトルチップ初期配置
        for (int y = 0; y < TIPS_CNT; y++) {
            for (int x = 0; x < TIPS_CNT; x++) {
                // チップを交互に配置していく
                if (setFlg) {
                    Pixmap pix = null;
                    int tipNum = Utility.rand(3);
                    switch (tipNum) {
                    case 0:
                        pix = Assets.Pix.Title.t_scrollTip1;
                        break;
                    case 1:
                        pix = Assets.Pix.Title.t_scrollTip2;
                        break;
                    case 2:
                        pix = Assets.Pix.Title.t_scrollTip3;
                        break;
                    }
                    tips[y][x] = new ScrollTip(pix, (x + 1) * DURING, y * DURING);
                } else {
                    tips[y][x] = null;
                }
                setFlg = !setFlg;
            }
        }
    }

    // 更新処理
    public void update(float deltaTime) {
        tickTime += deltaTime;
        while (tickTime > TICK) {
            tickTime -= TICK;
            for (int y = 0; y < TIPS_CNT; y++) {
                for (int x = 0; x < TIPS_CNT; x++) {
                    try {
                        // 枠を超えたら右上に移動（x座標とy座標を入れ替える）
                        if (tips[y][x].x <= 0 || tips[y][x].y >= (Const.HEIGHT + DURING))
                            tips[y][x].reverseTip();
                        // 左下に移動
                        tips[y][x].x -= MOVE_PX;
                        tips[y][x].y += MOVE_PX;
                    } catch (NullPointerException e) {
                        /*空の配列は無視*/
                    }
                }
            }
        }
    }

    // 描画処理
    public void present(Graphics g, float deltaTime) {
        for (int y = 0; y < TIPS_CNT; y++) {
            for (int x = 0; x < TIPS_CNT; x++) {
                try {
                    // 描画範囲外であれば描画しない
                    if (tips[y][x].x - WIDTH_OFFSET <= 0)
                        continue;
                    if (tips[y][x].x >= Const.WIDTH + WIDTH_OFFSET + DURING)
                        continue;
                    // チップ描画
                    g.drawPixmap(tips[y][x].pix
                            , tips[y][x].x - DURING - WIDTH_OFFSET
                            , tips[y][x].y - DURING);
                } catch (NullPointerException e) {
                    /*空の配列は無視*/
                }
            }
        }
    }
}
