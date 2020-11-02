package minesweeper;
import com.google.gson.Gson;
import minesweeper.controller.MinesweeperController;

import java.io.IOException;

import static spark.Spark.*;

public class Main {
    private static final MinesweeperController minesweeperController = MinesweeperController.INSTANCE;

    public static void main(String[] args) throws IOException {
        port(getHerokuAssignedPort());

        get("/ping", (req, res) -> "pong");

        Gson gson = new Gson();

        post("/create_playing_board", minesweeperController::createPlayBoard, gson::toJson);

        post("/play", minesweeperController::playGame, gson::toJson);

        post("/save_memcached_heroku", minesweeperController::saveMemcachedHeroku, gson::toJson);

        after((request, response) -> {
            response.header("Content-Type", "application/json");
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
