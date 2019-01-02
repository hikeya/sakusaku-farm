package com.hik.sakusakufarm;

// サクサク農園
public class SakusakuFarmGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
