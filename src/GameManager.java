package com.hik.sakusakufarm;

import java.util.Date;
import java.util.LinkedList;

import com.hik.sakusakufarm.Const.Scene;

// ゲームマネージャー
public class GameManager {
    private LinkedList<Task> taskList = new LinkedList<Task>(); // 当クラスで管理するタスクリスト
    private Context cont;      // 親のコンテキスト
    private Graphics gr;       // グラフィックス
    private Const.Scene scene; // カレントシーン

    // コンストラクタ
    public GameManager(Activity act, SurfaceHolder hold) {
        // 親のコンテキストを保持
        cont = act;

        // 画面サイズ指定
        Display disp = act.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        disp.getSize(p);
        int dh = Const.WIDTH*p.y/p.x;

        // グラフィックス生成
        gr = new Graphics(Const.WIDTH, dh, hold);
        gr.setOrigin(0, (dh-Const.HEIGHT)/2);

        // 初期シーン設定
        scene = Scene.TITLE;
        initTitle();
    }

    // タイトルシーン遷移時
    public void initTitle() {
        taskList.clear();
        taskList.add(new TitleBackground(cont));
        taskList.add(new TitleScrollTips(cont));
        taskList.add(new TitleName(cont));
        taskList.add(new TitleStartButton(cont));
        taskList.add(new TitleEndButton(cont));
    }

    // ゲームプレイシーン遷移時
    public void initPlay() {
        taskList.clear();
        taskList.add(new TitleBackground(cont));
    }

    // 画面タッチによる入力処理
    public void inputTouchEvent(MotionEvent e, int x, int y) {
        // 押下イベント以外なら無視
        if (e.getAction() != MotionEvent.ACTION_DOWN) return;
        System.out.println("comp3: "+new Date());
        // シーン制御
        switch (scene) {
            // タイトルシーン
            case TITLE:
                TitleStartButton st_btn = (TitleStartButton)taskList.get(3);
                // ゲームを始めるボタンが押された場合
                if (st_btn.getDstRect().contains(x, y)) {
                    scene = Const.Scene.PLAY;
                    initPlay();
                    break;
                }
                // EXITボタンが押された場合
                TitleEndButton ed_btn = (TitleEndButton)taskList.get(4);
                if (ed_btn.getDstRect().contains(x, y)) {
                    Activity act = (Activity)(cont);
                    act.finish(); // アプリケーション終了
                }
                break;
            // ゲームプレイシーン
            case PLAY:
                break;
            // ゲームオーバーシーン
            case GAMEOVER:
                break;
        }
    }

    // 更新処理
    public boolean onUpdate() {
        for (int i = 0; i < taskList.size(); i++) {
            // 更新して失敗なら
            if (taskList.get(i).onUpdate() == false) {
                taskList.remove(i); // そのタスクを消す
                i--;
            }
        }
        return true;
    }

    // 描画処理
    public void onDrawView() {
        gr.lock();
        for (Task task : taskList) {
            task.onDrawView(gr);
        }
        gr.unlock();
    }
}
