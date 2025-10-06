package io;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import model.GameState;


public class GameLoader {
    private static final Gson GSON = new Gson();


    public static void loadInto(GameState state, String path) throws IOException {
        try (FileReader r = new FileReader(path)){
            GameSaver.SaveData d = GSON.fromJson(r, GameSaver.SaveData.class);
            state.player.pos.set(d.px, d.py);
            state.score = d.score; state.lives = d.lives; state.timer = d.timer;
        }
    }
}
