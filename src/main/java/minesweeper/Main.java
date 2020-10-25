package minesweeper;
import minesweeper.controller.MinesweeperController;

import static spark.Spark.*;

public class Main {
    private static final MinesweeperController minesweeperController = MinesweeperController.INSTANCE;

    public static void main(String[] args) {
        System.out.println("Application running at http://localhost:4567/ping");

        get("/ping", (req, res) -> "pong");

        get("/get_playing_board", minesweeperController::getPlayingBoard);

    }
}
