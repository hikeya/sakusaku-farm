package com.hik.sakusakufarm;

import java.io.IOException;
import java.util.List;

// タイトル画面
public class TitleScreen extends Screen {
    private DrawPointInfo logo; // タイトルロゴ
    private DrawPointInfo btnStart; // はじめるボタン
    private DrawPointInfo btnRestart; // つづきからボタン
    private DrawPointInfo btnScore; // ハイスコアボタン
    private DrawPointInfo btnVol; // 音量ボタン
    private ScrollTipSheet tips; // スクロールチップ
    private HighScoreView highScoreView; // ハイスコアビュー
    private SaveLoadErrorView saveLoadErrorView; // セーブデータ読み込みエラービュー

    // コンストラクタ
    public TitleScreen(Game game) {
        super(game);
        Graphics g = game.getGraphics();
        Assets.Bgm.titleBGM.setLooping(true);

        // スクロールチップ
        tips = new ScrollTipSheet();

        // タイトルロゴ
        logo = new DrawPointInfo((Const.WIDTH - ((AndroidPixmap) Assets.Pix.Title.t_logo).getWidth()) / 2, 20);

        // はじめるボタン
        btnStart = new DrawPointInfo((Const.WIDTH - ((AndroidPixmap) Assets.Pix.Title.t_btnStart).getWidth()) / 2
                , Const.HEIGHT - (((AndroidPixmap) Assets.Pix.Title.t_btnStart).getHeight() + 15) * 3 - 65);

        // つづきからボタン
        btnRestart = new DrawPointInfo((Const.WIDTH - ((AndroidPixmap) Assets.Pix.Title.t_btnRestart).getWidth()) / 2
                , Const.HEIGHT - (((AndroidPixmap) Assets.Pix.Title.t_btnStart).getHeight() + 15) * 2 - 65);

        // ハイスコアボタン
        btnScore = new DrawPointInfo((Const.WIDTH - ((AndroidPixmap) Assets.Pix.Title.t_btnHighscore).getWidth()) / 2
                , Const.HEIGHT - (((AndroidPixmap) Assets.Pix.Title.t_btnHighscore).getHeight() + 15) - 65);

        // 音量ボタン
        btnVol = new DrawPointInfo(10, Const.HEIGHT - (((AndroidPixmap) Assets.Pix.Title.t_vol_on).getHeight() + 20));

        // ハイスコアビュー
        highScoreView = new HighScoreView();
        highScoreView.setStartPoint(g
                , (Const.WIDTH - highScoreView.getWidth()) / 2
                , (Const.HEIGHT - highScoreView.getHeight()) / 2);

        // セーブデータ読み込みエラービュー
        saveLoadErrorView = new SaveLoadErrorView();
    }

    // 更新処理
    @Override
    public void update(float deltaTime) {
        // タッチイベント取得
        List<TouchEvent> tcEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = tcEvents.size();

        // イベントによるアクション
        for (int i = 0; i < len; i++) {
            TouchEvent event = tcEvents.get(i);
            // 指が離された時
            if (event.type == TouchEvent.TOUCH_UP) {
                // ハイスコアビューおよびセーブデータ読み込みエラービューが非表示の時
                if (!highScoreView.isEnable && !saveLoadErrorView.isEnable) {
                    // 音量ボタン
                    if (Utility.isBounds(event, btnVol.x, btnVol.y
                            , ((AndroidPixmap) Assets.Pix.Title.t_vol_on).getWidth()
                            , ((AndroidPixmap) Assets.Pix.Title.t_vol_on).getHeight())) {
                        // OFF→ONにする
                        if (!VolumeController.isVolumeEnable()) {
                            VolumeController.setVolumeEnable(!VolumeController.isVolumeEnable());
                            Assets.Bgm.titleBGM.play();
                            // ON→OFFにする
                        } else {
                            Assets.Bgm.titleBGM.stop();
                            VolumeController.setVolumeEnable(!VolumeController.isVolumeEnable());
                        }

                        // はじめるボタン
                    } else if (Utility.isBounds(event, btnStart.x, btnStart.y
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnStart).getWidth()
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnStart).getHeight())) {
                        Assets.Se.decide.play(1);
                        // 牧場スクリーンに遷移
                        game.setScreen(new GameScreen(game));
                        return;

                        // つづきからボタン
                    } else if (Utility.isBounds(event, btnRestart.x, btnRestart.y
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnRestart).getWidth()
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnRestart).getHeight())) {
                        Assets.Se.decide.play(1);
                        // 牧場スクリーンに遷移
                        try {
                            SaveData.load(game.getFileIO());
                            game.setScreen(new GameScreen(game));
                            return;
                        } catch (IOException e) {
                            saveLoadErrorView.setStartPoint(game.getGraphics()
                                    , (Const.WIDTH - saveLoadErrorView.getWidth()) / 2
                                    , (Const.HEIGHT - saveLoadErrorView.getHeight()) / 2
                                    , e);
                            saveLoadErrorView.isEnable = true;
                        }

                        // ハイスコアボタン
                    } else if (Utility.isBounds(event, btnScore.x, btnScore.y
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnHighscore).getWidth()
                            , ((AndroidPixmap) Assets.Pix.Title.t_btnHighscore).getHeight())) {
                        Assets.Se.decide.play(1);
                        // ハイスコアビューの表示
                        highScoreView.isEnable = true;
                    }

                    // ハイスコアビュー表示時に戻るボタンが押された時
                } else if (highScoreView.isEnable && highScoreView.isTouchBack(event)) {
                    // ハイスコアビューを非表示
                    highScoreView.isEnable = false;
                    // セーブデータ読み込みエラービュー表示時に戻るボタンが押された時
                } else if (saveLoadErrorView.isEnable && saveLoadErrorView.isTouchBack(event)) {
                    // セーブデータ読み込みエラービューを非表示
                    saveLoadErrorView.isEnable = false;
                }
            }
        }

        // ハイスコアビューおよびセーブデータ読み込みエラービューが非表示の時
        if (!highScoreView.isEnable && !saveLoadErrorView.isEnable) {
            // スクロールチップ
            tips.update(deltaTime);
        }
    }

    // 描画処理
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        // 背景
        g.drawPixmap(Assets.Pix.Title.t_bg, 0, 0);
        // スクロールチップ
        tips.present(g, deltaTime);
        // タイトルロゴ
        g.drawPixmap(Assets.Pix.Title.t_logo, logo.x, logo.y);
        // はじめるボタン
        g.drawPixmap(Assets.Pix.Title.t_btnStart, btnStart.x, btnStart.y);
        // つづきからボタン
        g.drawPixmap(Assets.Pix.Title.t_btnRestart, btnRestart.x, btnRestart.y);
        // ハイスコアボタン
        g.drawPixmap(Assets.Pix.Title.t_btnHighscore, btnScore.x, btnScore.y);
        // 音量ボタン
        if (VolumeController.isVolumeEnable()) {
            g.drawPixmap(Assets.Pix.Title.t_vol_on, btnVol.x, btnVol.y);
        } else {
            g.drawPixmap(Assets.Pix.Title.t_vol_off, btnVol.x, btnVol.y);
        }
        // ハイスコアビュー
        if (highScoreView.isEnable) {
            g.drawGrayMask();
            highScoreView.present(g, deltaTime);
            // セーブデータ読み込みエラービュー
        } else if (saveLoadErrorView.isEnable) {
            g.drawGrayMask();
            saveLoadErrorView.present(g, deltaTime);
        }
    }

    // ゲーム中断時
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
        Assets.Bgm.titleBGM.stop();
    }

    // ゲーム再開時
    @Override
    public void resume() {
        Assets.Bgm.titleBGM.play();
    }

    @Override
    public void dispose() {
    }
}
