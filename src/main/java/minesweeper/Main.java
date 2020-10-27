package minesweeper;
import com.google.gson.Gson;
import minesweeper.controller.MinesweeperController;

import java.io.IOException;

import static spark.Spark.*;

public class Main {
    private static final MinesweeperController minesweeperController = MinesweeperController.INSTANCE;

    public static void main(String[] args) throws IOException {
        get("/ping", (req, res) -> "pong");

        Gson gson = new Gson();

        post("/create_playing_board", minesweeperController::createPlayBoard, gson::toJson);

        post("/play", minesweeperController::playGame, gson::toJson);

        after((request, response) -> {
            response.header("Content-Type", "application/json");
        });
    }
}
