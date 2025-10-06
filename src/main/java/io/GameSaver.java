package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import model.GameState;


public class GameSaver {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public static void save(GameState state, String path) throws IOException {
        try (FileWriter w = new FileWriter(path)){
            SaveData d = SaveData.from(state);
            GSON.toJson(d, w);
        }
    }


    static class SaveData {
        double px, py; int score, lives; double timer;
        static SaveData from(GameState s){
            SaveData d = new SaveData();
            d.px = s.player.pos.x; d.py = s.player.pos.y; d.score = s.score; d.lives = s.lives; d.timer = s.timer; return d;
        }

    }
}
