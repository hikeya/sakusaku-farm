package com.hik.sakusakufarm;

import com.hik.sakusakufarm.SwitchTip.State;

public class KeyPad {
    private SwitchTip btnUp;
    private SwitchTip btnRight;
    private SwitchTip btnDown;
    private SwitchTip btnLeft;
    private SwitchTip btnHome;

    public KeyPad() {
    }

    // 描画開始位置設定
    public void setStartPoint(int x, int y) {
        btnUp = new SwitchTip(x + Assets.Pix.Ui.btn_left_neutral.getWidth(), y
                , Assets.Pix.Ui.btn_up_neutral.getWidth()
                , Assets.Pix.Ui.btn_up_neutral.getHeight()
                , State.OFF);
        btnLeft = new SwitchTip(x, y + Assets.Pix.Ui.btn_up_neutral.getHeight()
                , Assets.Pix.Ui.btn_left_neutral.getWidth()
                , Assets.Pix.Ui.btn_left_neutral.getHeight()
                , State.OFF);
        btnHome = new SwitchTip(x + Assets.Pix.Ui.btn_left_neutral.getWidth()
                , y + Assets.Pix.Ui.btn_up_neutral.getHeight()
                , Assets.Pix.Ui.btn_home_neutral.getWidth()
                , Assets.Pix.Ui.btn_home_neutral.getHeight()
                , State.OFF);
        btnRight = new SwitchTip(x + Assets.Pix.Ui.btn_left_neutral.getWidth()
                + Assets.Pix.Ui.btn_home_neutral.getWidth()
                , y + Assets.Pix.Ui.btn_up_neutral.getHeight()
                , Assets.Pix.Ui.btn_right_neutral.getWidth()
                , Assets.Pix.Ui.btn_right_neutral.getHeight()
                , State.OFF);
        btnDown = new SwitchTip(x + Assets.Pix.Ui.btn_left_neutral.getWidth()
                , y + Assets.Pix.Ui.btn_up_neutral.getHeight()
                        + Assets.Pix.Ui.btn_home_neutral.getHeight()
                , Assets.Pix.Ui.btn_down_neutral.getWidth()
                , Assets.Pix.Ui.btn_down_neutral.getHeight()
                , State.OFF);
    }

    // 描画処理
    public void present(Graphics g) {
        // 上ボタン
        if (btnUp.state == State.OFF)
            g.drawPixmap(Assets.Pix.Ui.btn_up_neutral, btnUp.x, btnUp.y);
        else
            g.drawPixmap(Assets.Pix.Ui.btn_up_press, btnUp.x, btnUp.y);
        // 左ボタン
        if (btnLeft.state == State.OFF)
            g.drawPixmap(Assets.Pix.Ui.btn_left_neutral, btnLeft.x, btnLeft.y);
        else
            g.drawPixmap(Assets.Pix.Ui.btn_left_press, btnLeft.x, btnLeft.y);
        // ホームボタン
        if (btnHome.state == State.OFF)
            g.drawPixmap(Assets.Pix.Ui.btn_home_neutral, btnHome.x, btnHome.y);
        else
            g.drawPixmap(Assets.Pix.Ui.btn_home_press, btnHome.x, btnHome.y);
        // 右ボタン
        if (btnRight.state == State.OFF)
            g.drawPixmap(Assets.Pix.Ui.btn_right_neutral, btnRight.x, btnRight.y);
        else
            g.drawPixmap(Assets.Pix.Ui.btn_right_press, btnRight.x, btnRight.y);
        // 下ボタン
        if (btnDown.state == State.OFF)
            g.drawPixmap(Assets.Pix.Ui.btn_down_neutral, btnDown.x, btnDown.y);
        else
            g.drawPixmap(Assets.Pix.Ui.btn_down_press, btnDown.x, btnDown.y);
    }

    public int getWidth() {
        return Assets.Pix.Ui.btn_left_neutral.getWidth()
                + Assets.Pix.Ui.btn_home_neutral.getWidth()
                + Assets.Pix.Ui.btn_right_neutral.getWidth();
    }

    public int getHeight() {
        return Assets.Pix.Ui.btn_up_neutral.getHeight()
                + Assets.Pix.Ui.btn_home_neutral.getHeight()
                + Assets.Pix.Ui.btn_down_neutral.getHeight();
    }

    // ボタン押下判定
    public boolean isPressing(TouchEvent e, int direction) {
        boolean isTouch = false;
        switch (direction) {
        case Const.NORTH:
            isTouch = Utility.isBounds(e, btnUp.x, btnUp.y, btnUp.w, btnUp.h);
            break;
        case Const.WEST:
            isTouch = Utility.isBounds(e, btnLeft.x, btnLeft.y, btnLeft.w, btnLeft.h);
            break;
        case Const.CENTER:
            isTouch = Utility.isBounds(e, btnHome.x, btnHome.y, btnHome.w, btnHome.h);
            break;
        case Const.EAST:
            isTouch = Utility.isBounds(e, btnRight.x, btnRight.y, btnRight.w, btnRight.h);
            break;
        case Const.SOUTH:
            isTouch = Utility.isBounds(e, btnDown.x, btnDown.y, btnDown.w, btnDown.h);
            break;
        }
        return isTouch;
    }

    // ボタンリセット
    public void resetSwitch() {
        btnUp.state = State.OFF;
        btnLeft.state = State.OFF;
        btnHome.state = State.OFF;
        btnRight.state = State.OFF;
        btnDown.state = State.OFF;
    }

    // ボタン切り替え
    public void changeSwitch(Byte direction, State state) {
        resetSwitch();
        switch (direction) {
        case Const.NORTH:
            btnUp.state = state;
            break;
        case Const.WEST:
            btnLeft.state = state;
            break;
        case Const.CENTER:
            btnHome.state = state;
            break;
        case Const.EAST:
            btnRight.state = state;
            break;
        case Const.SOUTH:
            btnDown.state = state;
            break;
        }
    }

    // 有効ボタン取得
    public byte getEnableButton() {
        Byte direction = 0;
        if (btnUp.state == State.ON)
            direction = Const.NORTH;
        else if (btnLeft.state == State.ON)
            direction = Const.WEST;
        else if (btnHome.state == State.ON)
            direction = Const.CENTER;
        else if (btnRight.state == State.ON)
            direction = Const.EAST;
        else if (btnDown.state == State.ON)
            direction = Const.SOUTH;
        return direction;
    }
}
