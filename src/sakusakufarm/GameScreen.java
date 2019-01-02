package com.hik.sakusakufarm;

import java.util.List;

import com.hik.sakusakufarm.SwitchTip.State;

// ゲーム画面
public class GameScreen extends Screen {
    enum GameState {
        RUNNING,
        ITEM,
        SAVE,
        SETTING,
        GAMECLEAR
    }

    private GameState state = GameState.RUNNING;
    private GameWorld gameWorld; // ゲームプレイ中メインクラス
    private KeyPad keyPad = new KeyPad();

    // コンストラクタ
    public GameScreen(Game game) {
        super(game);
        Assets.Bgm.farmBGM.setLooping(true);
        gameWorld = new GameWorld(game.getFileIO());
        keyPad.setStartPoint((Const.WIDTH - keyPad.getWidth()) / 2
                , Const.HEIGHT - keyPad.getHeight() - 10);
    }

    // 更新処理
    @Override
    public void update(float deltaTime) {
        // タッチイベント取得
        List<TouchEvent> tcEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        // 状態別更新処理
        switch (state) {
        case RUNNING:
            updateRunning(tcEvents, deltaTime);
            break;
        case ITEM:
            updateItem(tcEvents);
            break;
        case SAVE:
            updateSave(tcEvents);
            break;
        case SETTING:
            updateSetting(tcEvents);
            break;
        case GAMECLEAR:
            updateGameClear(tcEvents);
            break;
        }
    }

    private void updateRunning(List<TouchEvent> tcEvents, float deltaTime) {
        int len = tcEvents.size();
        // ボタン押下取得処理
        for (int i = 0; i < len; i++) {
            TouchEvent event = tcEvents.get(i);

            // キーパッドリセット
            keyPad.resetSwitch();

            // 指が押されている時
            if (event.type == TouchEvent.TOUCH_DRAGGED) {
                // アイテムボタン

                // セーブボタン

                // 設定ボタン

                // キーパッド確認
                if (keyPad.isPressing(event, Const.NORTH)) {
                    keyPad.changeSwitch(Const.NORTH, State.ON);
                } else if (keyPad.isPressing(event, Const.WEST)) {
                    keyPad.changeSwitch(Const.WEST, State.ON);
                } else if (keyPad.isPressing(event, Const.CENTER)) {
                    keyPad.changeSwitch(Const.CENTER, State.ON);
                } else if (keyPad.isPressing(event, Const.EAST)) {
                    keyPad.changeSwitch(Const.EAST, State.ON);
                } else if (keyPad.isPressing(event, Const.SOUTH)) {
                    keyPad.changeSwitch(Const.SOUTH, State.ON);
                }
            }
        }

        // ゲームの世界を更新
        gameWorld.update(keyPad.getEnableButton(), deltaTime);
        if (gameWorld.isGameClear) {

            // クリア音鳴らす

            state = GameState.GAMECLEAR;
        }
    }

    private void updateItem(List<TouchEvent> tcEvents) {

    }

    private void updateSave(List<TouchEvent> tcEvents) {

    }

    private void updateSetting(List<TouchEvent> tcEvents) {

    }

    private void updateGameClear(List<TouchEvent> tcEvents) {

    }

    // 描画処理
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        // ゲームの世界を描画
        gameWorld.present(g, deltaTime);

        // 状態別UI描画
        switch (state) {
        case RUNNING:
            presentRunningUI(g);
            break;
        case ITEM:
            presentItemUI();
            break;
        case SAVE:
            presentSaveUI();
            break;
        case SETTING:
            presentSettingUI();
            break;
        case GAMECLEAR:
            presentGameClearUI();
            break;
        }
    }

    private void presentRunningUI(Graphics g) {
        keyPad.present(g);
    }

    private void presentItemUI() {

    }

    private void presentSaveUI() {

    }

    private void presentSettingUI() {

    }

    private void presentGameClearUI() {

    }

    // ゲーム中断時
    @Override
    public void pause() {
        Assets.Bgm.farmBGM.stop();
    }

    // ゲーム再開時
    @Override
    public void resume() {
        Assets.Bgm.farmBGM.play();
    }

    @Override
    public void dispose() {
    }
}
