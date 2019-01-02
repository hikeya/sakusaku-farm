package com.hik.sakusakufarm;

import java.util.List;

// タイトル画面
public class TitleScreen extends Screen {
    // タイトルロゴ用
    private boolean logoFlg;   // 真:下降, 偽:上昇

    // タイトルチップ用定数
    private static final int
        DURING     = 80                           // チップ配置間隔(px)
       ,WIDTH_BIAS = (Const.HEIGHT-Const.WIDTH)/2 // 画面横サイズ調整値(px)
       ,TIPS_CNT   = Const.HEIGHT/DURING+1;       // 配置チップ数

    // タイトルチップ配置
    private TitleTip[][] tips = new TitleTip[TIPS_CNT][TIPS_CNT];

    // 描画開始座標
    private int
         drawLogoX
        ,drawLogoY
        ,drawBtnStartX
        ,drawBtnStartY
        ,drawBtnRestartX
        ,drawBtnRestartY
        ,drawBtnHighX
        ,drawBtnHighY
        ,drawVolX
        ,drawVolY;

    // チップ
    class TitleTip {
        protected AndroidPixmap pix; // 画像
        protected int x;             // x座標（右側）
        protected int y;             // y座標（下側）

        // コンストラクタ
        public TitleTip() {
            pix = null;
            x   = 0;
            y   = 0;
        }
        public TitleTip(AndroidPixmap pix, int x, int y) {
            this.pix = pix;
            this.x   = x;
            this.y   = y;
        }
    }

    // コンストラクタ
    public TitleScreen(Game game) {
        super(game);
        // タイトルチップ初期配置
        AndroidPixmap pix[]  = new AndroidPixmap[3];
        boolean setFlg = true;

        // チップ素材の取得
        pix[0] = (AndroidPixmap)Assets.t_scrollTip1; // クローバー
        pix[1] = (AndroidPixmap)Assets.t_scrollTip2; // 青葉
        pix[2] = (AndroidPixmap)Assets.t_scrollTip3; // 枯葉

        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                // チップを交互に配置していく
                if (setFlg) {
                    tips[y][x] = new TitleTip(pix[Utility.rand(3)], (x+1)*DURING, y*DURING);
                } else {
                    tips[y][x] = null;
                }
                setFlg = !setFlg;
            }
        }

        // タイトルロゴスクロール初期化
        logoFlg   = true;

        // 描画開始座標
        drawLogoX       = (Const.WIDTH-((AndroidPixmap)Assets.t_logo).getWidth())/2;
        drawLogoY       = 20;
        drawBtnStartX   = (Const.WIDTH-((AndroidPixmap)Assets.t_btnStart).getWidth())/2;
        drawBtnStartY   = Const.HEIGHT-(((AndroidPixmap)Assets.t_btnStart).getHeight()+10)*3-75;
        drawBtnRestartX = (Const.WIDTH-((AndroidPixmap)Assets.t_btnRestart).getWidth())/2;
        drawBtnRestartY = Const.HEIGHT-(((AndroidPixmap)Assets.t_btnStart).getHeight()+10)*2-75;
        drawBtnHighX    = (Const.WIDTH-((AndroidPixmap)Assets.t_btnHighscore).getWidth())/2;
        drawBtnHighY    = Const.HEIGHT-(((AndroidPixmap)Assets.t_btnHighscore).getHeight()+10)-75;
        drawVolX        = 10;
        drawVolY        = Const.HEIGHT-(((AndroidPixmap)Assets.vol_on).getHeight()+20);
    }

    // 更新処理
    @Override
    public void update(float deltaTime) {
        // タッチイベント取得
        List<TouchEvent> tcEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = tcEvents.size();

        //イベントによるアクション
        for (int i=0; i < len; i++) {
            TouchEvent event = tcEvents.get(i);
            // 指が離された時
            if (event.type == TouchEvent.TOUCH_UP) {
               // 音量ボタン
               if(Utility.isBounds( event, drawVolX, drawVolY
                                  , ((AndroidPixmap)Assets.vol_on).getWidth()
                                  , ((AndroidPixmap)Assets.vol_on).getHeight())) {
                   // ON/OFF切り替え
                   Settings.soundEnabled = !Settings.soundEnabled;
                   if (Settings.soundEnabled)
                       Assets.decide.play(1);
               // はじめるボタン
               } else if (Utility.isBounds( event, drawBtnStartX, drawBtnStartY
                                          , ((AndroidPixmap)Assets.t_btnStart).getWidth()
                                          , ((AndroidPixmap)Assets.t_btnStart).getHeight())) {
                   // 牧場スクリーンに遷移
                   game.setScreen(new FarmScreen(game));
                   if (Settings.soundEnabled)
                       Assets.decide.play(1);
                   return;
               // つづきからボタン
               } else if (Utility.isBounds( event, drawBtnRestartX, drawBtnRestartY
                                          , ((AndroidPixmap)Assets.t_btnRestart).getWidth()
                                          , ((AndroidPixmap)Assets.t_btnRestart).getHeight())) {
                   // 牧場スクリーンに遷移
                   game.setScreen(new FarmScreen(game));
                   if (Settings.soundEnabled)
                       Assets.decide.play(1);
                   return;
               // ハイスコアボタン
               } else if (Utility.isBounds( event, drawBtnHighX, drawBtnHighY
                                          , ((AndroidPixmap)Assets.t_btnStart).getWidth()
                                          , ((AndroidPixmap)Assets.t_btnStart).getHeight())) {

                   // ハイスコアビューの表示

                   if (Settings.soundEnabled)
                       Assets.decide.play(1);
               }
            }
        }

        // タイトルチップ
        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                try {
                    // 左枠を超えたら右上まで移動（x座標とy座標を入れ替える）
                    if (tips[y][x].x <= 0) {
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
                } catch (NullPointerException e) { /*空の配列は無視*/ }
            }
        }

        // タイトル文字スクロール
        if (logoFlg) drawLogoY ++;
        else drawLogoY --;
        if (drawLogoY == 30 || drawLogoY == 20) logoFlg = !logoFlg;
    }

    // 描画処理
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        // 背景
        g.drawPixmapAuto(Assets.t_bg, 0, 0, Const.WIDTH, Const.HEIGHT);
        // スクロールチップ
        for (int y=0; y < TIPS_CNT; y++) {
            for (int x=0; x < TIPS_CNT; x++) {
                try {
                    // 描画範囲外であれば描画しない
                    if (tips[y][x].x - WIDTH_BIAS <= 0)                continue;
                    if (tips[y][x].x >= Const.WIDTH+WIDTH_BIAS+DURING) continue;
                    // チップ描画
                    g.drawPixmap( tips[y][x].pix
                                , tips[y][x].x-WIDTH_BIAS-DURING
                                , tips[y][x].y-DURING);
                } catch (NullPointerException e) { /*空の配列は無視*/ }
            }
        }
        // タイトルロゴ
        g.drawPixmap(Assets.t_logo, drawLogoX, drawLogoY);
        // はじめるボタン
        g.drawPixmap(Assets.t_btnStart, drawBtnStartX, drawBtnStartY);
        // つづきからボタン
        g.drawPixmap(Assets.t_btnRestart, drawBtnRestartX, drawBtnRestartY);
        // ハイスコアボタン
        g.drawPixmap(Assets.t_btnHighscore, drawBtnHighX, drawBtnHighY);
        // 音量ボタン
        if (Settings.soundEnabled) {
            g.drawPixmap(Assets.vol_on, drawVolX, drawVolY);
        } else {
            g.drawPixmap(Assets.vol_off, drawVolX, drawVolY);
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
