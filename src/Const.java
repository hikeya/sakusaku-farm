package com.hik.sakusakufarm;

public interface Const {
    // 画面サイズ定数
    final static int
        WIDTH  = 224, // 画面幅   （マップチップ6枚分）
        HEIGHT = 320; // 画面高さ（マップチップ10枚分）

    // ゲームタイトル
    final static String TITLE_NAME = "サクサク農園";

    // シーン管理
    enum Scene {
        TITLE,   // タイトル
        PLAY,    // ゲーム中
        GAMEOVER // ゲームオーバー
    }
}
