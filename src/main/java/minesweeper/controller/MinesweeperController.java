package minesweeper.controller;

import com.google.gson.Gson;
import minesweeper.domain.PlayData;
import minesweeper.domain.InititalizationData;
import minesweeper.response.Error;
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
            playingBoardResponse.status = "success";
        } catch (Exception ex) {
            //TODO: Create interface Response to inherit.
            setErrorResponse(playingBoardResponse, ex);
        }

        return playingBoardResponse;
    }

    public PlayingBoardResponse discoverCell(final Request request, final Response response) {
        PlayingBoardResponse playingBoardResponse = new PlayingBoardResponse();

        try{
            PlayData playData = new Gson().fromJson(request.body(), PlayData.class);
            playingBoardResponse.playingBoard = playingBoardService.discoverCell(playData);
            playingBoardResponse.status = "success";
        } catch (Exception ex) {
            //TODO: Create interface Response to inherit.
            setErrorResponse(playingBoardResponse, ex);
        }

        return playingBoardResponse;
    }

    private void setErrorResponse(PlayingBoardResponse playingBoardResponse, Exception ex) {
        playingBoardResponse.error = new Error();
        playingBoardResponse.error.status = "error";
        playingBoardResponse.error.message = ex.getMessage();
    }
}
