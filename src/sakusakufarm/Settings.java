package com.hik.sakusakufarm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// 共通設定
public class Settings {
    private static int highscore = 0;

    // 外部ファイル読み込み
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".sakusakufarm")));
            VolumeController.setVolumeEnable(Boolean.parseBoolean(in.readLine()));
            highscore = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            Log.i("IOInfomation", "設定ファイルが見つかりません。");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("IOException", "ファイルIOを閉じるのに失敗しました。");
            }
        }
    }

    // 外部ファイル書き込み
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".sakusakufarm")));
            out.write(Boolean.toString(VolumeController.isVolumeEnable()));
            out.write(Integer.toString(highscore));
        } catch (IOException e) {
            Log.e("IOException", "設定ファイルの書き込みに失敗しました。");
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                Log.e("IOException", "ファイルIOを閉じるのに失敗しました。");
            }
        }
    }

    // ハイスコア読込
    public static int getHighScore() {
        return highscore;
    }

    // ハイスコア更新
    public static void setHighScore(int score) {
        highscore = score;
    }
}
