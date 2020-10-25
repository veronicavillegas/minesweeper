package minesweeper.controller;

import com.google.gson.Gson;
import minesweeper.domain.PlayData;
import minesweeper.domain.InititalizationData;
import minesweeper.response.StatusResponse;
import minesweeper.response.PlayingBoardResponse;
import minesweeper.service.PlayingBoardService;
import spark.Request;
import spark.Response;

public enum MinesweeperController {
    INSTANCE;
    PlayingBoardService playingBoardService = PlayingBoardService.INSTANCE;

    public PlayingBoardResponse createPlayBoard(final Request request, final Response response) {
        PlayingBoardResponse playingBoardResponse = new PlayingBoardResponse();

        try{
            InititalizationData inititalizationData = new Gson().fromJson(request.body(), InititalizationData.class);
            playingBoardResponse.playingBoard = playingBoardService.createPlayBoard(inititalizationData);
            playingBoardResponse.statusResponse = getStatusResponse(201, "OK");
        } catch (Exception ex) {
            //TODO: Create interface Response to inherit.
            playingBoardResponse.statusResponse = getStatusResponse(500, ex.getMessage());
        }

        return playingBoardResponse;
    }

    public PlayingBoardResponse discoverCell(final Request request, final Response response) {
        PlayingBoardResponse playingBoardResponse = new PlayingBoardResponse();

        try{
            PlayData playData = new Gson().fromJson(request.body(), PlayData.class);
            playingBoardResponse.playingBoard = playingBoardService.discoverCell(playData.playingBoard, playData.selectedCell);
            playingBoardResponse.statusResponse = getStatusResponse(201, "OK");
        } catch (Exception ex) {
            playingBoardResponse.statusResponse = getStatusResponse(500, ex.getMessage());
        }

        return playingBoardResponse;
    }

    public StatusResponse saveGame(final Request request, final Response response) {
        return new StatusResponse();
    }

    public PlayingBoardResponse resumeGame(final Request request, final Response response) {
        return new PlayingBoardResponse();
    }

    private StatusResponse getStatusResponse(int status, String message) {
        StatusResponse statusResponse = new StatusResponse();
        statusResponse.status = status;
        statusResponse.message = message;
        return statusResponse;
    }
}
