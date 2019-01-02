package com.hik.sakusakufarm;

public class GameEvent {
    private Pixmap pix = null;
    private byte direction = 0;
    private byte mode = 0;
    private short eventNum = 0;

    // コンストラクタ
    public GameEvent(Pixmap pix, byte direction, byte mode, short eventNum) {
        this.pix = pix;
        this.direction = direction;
        this.mode = mode;
        this.eventNum = eventNum;
    }
}
