package com.hik.sakusakufarm;

import java.util.Date;

// ゲームビュー
public class SakusakuFarmView extends SurfaceView
    implements SurfaceHolder.Callback, Runnable {
    // ゲームマネージャアクション内容
    private static final int UPDATE = 1;
    private static final int DRAW   = 2;
    private static final int TOUCH  = 3;

    private SurfaceHolder holder;     // サーフェイスホルダー
    private GameManager   gm;         // ゲームマネージャー
    private Thread        thread;     // スレッド
    private boolean       thread_flg; // スレッド継続フラグ
    private MotionEvent   e;          // タッチイベント情報
    private int           x,y;        // タッチ座標

    // コンストラクタ
    public SakusakuFarmView(Activity act) {
        super(act);

        // サーフェイスホルダー生成
        holder = getHolder();
        holder.addCallback(this);

        // ゲームマネージャー生成
        gm = new GameManager(act, holder);
    }

    // サーフェイスの生成
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread_flg = true;
        thread.start();
    }

    // サーフェイスの終了
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread_flg = false; // スレッドループ終了
        while (true) {
            try {
                thread.join(); // スレッドが完全に終了するまで待つ
                break;
            } catch (InterruptedException e) {}
        }
    }

    // サーフェイスの変更
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {}

    // マルチスレッドの処理
    @Override
    public void run() {
        while(thread_flg) {
            actionGameManager(UPDATE); // 更新処理
            actionGameManager(DRAW);   // 描画処理
        }
    }

    // タッチ時に呼ばれる
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        this.e = e;
        x = (int)(e.getX()*Const.WIDTH /getWidth());
        y = (int)(e.getY()*Const.HEIGHT/getHeight());
        System.out.println("comp1: "+new Date());

        // ボタン押下なら入力処理へ
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            actionGameManager(TOUCH); // タッチ入力処理
        }
        return true;
    }

    // ゲームマネージャを動かす
    synchronized private void actionGameManager(int i_const) {
        switch (i_const) {
            case UPDATE:
                gm.onUpdate();
                break;
            case DRAW:
                gm.onDrawView();
                break;
            case TOUCH:
                System.out.println("comp2: "+new Date());
                gm.inputTouchEvent(e, x, y);
                System.out.println("comp4: "+new Date());
                break;
        }
    }
}
