package minesweeper;
import com.google.gson.Gson;
import minesweeper.controller.MinesweeperController;
import org.slf4j.MDC;
import spark.Spark;

import java.awt.*;

import static spark.Spark.*;

public class Main {
    private static final MinesweeperController minesweeperController = MinesweeperController.INSTANCE;

    public static void main(String[] args) {
        System.out.println("Application running at http://localhost:4567/ping");

        get("/ping", (req, res) -> "pong");

        Gson gson = new Gson();

        post("/create_playing_board", minesweeperController::createPlayBoard, gson::toJson);

        post("/play", minesweeperController::discoverCell, gson::toJson);

        post("/save_game", minesweeperController::saveGame, gson::toJson);

        post("/resume_game", minesweeperController::resumeGame, gson::toJson);

        after((request, response) -> {
            response.header("Content-Type", "application/json");
        });
    }
}
