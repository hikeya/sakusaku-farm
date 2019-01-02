package com.hik.sakusakufarm;

import java.io.IOException;

public class SaveLoadErrorView {
    private String txt1;
    private String txt2;
    private String txt3;
    private DrawPointInfo XY_view;
    private DrawPointInfo XY_txt1;
    private DrawPointInfo XY_txt2;
    private DrawPointInfo XY_txt3;
    private DrawPointInfo XY_back;
    public boolean isEnable = false;
    private static final int basicFontSize = 18;
    private static final int massageFontSize = 12;

    // コンストラクタ
    public SaveLoadErrorView() {
        // ラベル設定
        txt1 = "エラー";
        txt2 = null;
        txt3 = "戻る";
    }

    // 描画開始位置設定
    public void setStartPoint(Graphics g, int x, int y, IOException e) {
        g.setTextSize(basicFontSize);
        XY_view = new DrawPointInfo(x, y);
        XY_txt1 = new DrawPointInfo(x + (Assets.Pix.Title.t_viewScore.getWidth() - g.measureText(txt1)) / 2, y + 50);
        g.setTextSize(massageFontSize);
        txt2 = e.getMessage();
        XY_txt2 = new DrawPointInfo(x + (Assets.Pix.Title.t_viewScore.getWidth() - g.measureText(txt2)) / 2,
                XY_txt1.y + 30);
        g.setTextSize(basicFontSize);
        XY_txt3 = new DrawPointInfo(x + (Assets.Pix.Title.t_viewScore.getWidth() - g.measureText(txt3)) / 2,
                XY_txt2.y + 40);
        XY_back = new DrawPointInfo(XY_txt3.x
                , XY_txt3.y + (int) g.getFontMetrics().top
                , g.measureText(txt3)
                , -(int) g.getFontMetrics().top + (int) g.getFontMetrics().bottom);
    }

    // 描画処理
    public void present(Graphics g, float deltaTime) {
        g.setTextSize(basicFontSize);
        g.drawPixmap(Assets.Pix.Title.t_viewScore, XY_view.x, XY_view.y);
        g.setColor(Color.BLACK);
        g.drawText(txt1, XY_txt1.x, XY_txt1.y);
        g.setTextSize(massageFontSize);
        g.drawText(txt2, XY_txt2.x, XY_txt2.y);
        g.setTextSize(basicFontSize);
        // 戻るボタン
        g.drawRect(XY_back.x, XY_back.y, XY_back.w, XY_back.h, Color.YELLOW);
        g.setColor(Color.BLUE);
        g.drawText(txt3, XY_txt3.x, XY_txt3.y);
    }

    public int getWidth() {
        return Assets.Pix.Title.t_viewScore.getWidth();
    }

    public int getHeight() {
        return Assets.Pix.Title.t_viewScore.getHeight();
    }

    // 戻るボタン押下判定
    public boolean isTouchBack(TouchEvent e) {
        return Utility.isBounds(e, XY_back.x, XY_back.y, XY_back.w, XY_back.h);
    }
}
