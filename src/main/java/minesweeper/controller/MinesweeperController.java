package minesweeper.controller;

import com.google.gson.Gson;
import minesweeper.domain.PlayingBoard;
import minesweeper.service.PlayingBoardService;
import spark.Request;
import spark.Response;

public enum MinesweeperController {
    INSTANCE;
    PlayingBoardService playingBoardService = PlayingBoardService.INSTANCE;

    public String getPlayingBoard(final Request request, final Response response) {
        int columns = 3;
        int rows = 3;
        int mines = 4;

        PlayingBoard playingBoard = playingBoardService.getPlayingBoard(rows, columns, mines);

        Gson gson = new Gson();
        return gson.toJson(playingBoard);
    }
}
