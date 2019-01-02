package com.hik.sakusakufarm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SaveData {
    private static int mapNum = 1;
    private static int playerX = Const.TIP_SIZE * 18;
    private static int playerY = Const.TIP_SIZE * 4;
    private static byte playerDir = Const.SOUTH;
    private static int moeny = 0;
    private static IOException notFoundSaveData = new IOException("セーブデータが見つかりません");

    // セーブデータ読み込み
    public static void load(FileIO files) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile("data.sakusakufarm")));
            mapNum = Integer.parseInt(in.readLine());
            playerX = Integer.parseInt(in.readLine());
            playerY = Integer.parseInt(in.readLine());
            playerDir = Byte.parseByte(in.readLine());
            moeny = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            throw notFoundSaveData;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("IOException", "ファイルIOを閉じるのに失敗しました。");
            }
        }
    }

    // セーブデータ書き込み
    public static void save(FileIO files) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile("data.sakusakufarm")));
            out.write(Integer.toString(mapNum));
            out.write(Integer.toString(playerX));
            out.write(Integer.toString(playerY));
            out.write(Byte.toString(playerDir));
            out.write(Integer.toString(moeny));
        } catch (IOException e) {
            throw new IOException("セーブデータが書き込めません");
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                Log.e("IOException", "ファイルIOを閉じるのに失敗しました。");
            }
        }
    }

    // getter
    public static int getMapNum() {
        return mapNum;
    }

    public static int getPlayerX() {
        return playerX;
    }

    public static int getPlayerY() {
        return playerY;
    }

    public static byte getPlayerDir() {
        return playerDir;
    }

    public static int getMoeny() {
        return moeny;
    }

    // setter
    public static void setMapNum(int mapNum) {
        SaveData.mapNum = mapNum;
    }

    public static void setPlayerX(int playerX) {
        SaveData.playerX = playerX;
    }

    public static void setPlayerY(int playerY) {
        SaveData.playerY = playerY;
    }

    public static void setPlayerDir(byte playerDir) {
        SaveData.playerDir = playerDir;
    }

    public static void setMoeny(int moeny) {
        SaveData.moeny = moeny;
    }
}
