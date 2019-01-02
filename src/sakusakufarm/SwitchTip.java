package com.hik.sakusakufarm;

public class SwitchTip extends DrawPointInfo {
    public enum State {
        ON,
        OFF
    }

    public State state;

    // コンストラクタ
    public SwitchTip(int x, int y, State state) {
        super(x, y);
        this.state = state;
    }

    // コンストラクタ
    public SwitchTip(int x, int y, int w, int h, State state) {
        super(x, y, w, h);
        this.state = state;
    }
}
