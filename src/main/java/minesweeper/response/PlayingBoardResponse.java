package minesweeper.response;

import minesweeper.domain.PlayingBoard;

public class PlayingBoardResponse {
    public String status = null;
    public Error error = new Error();
    public PlayingBoard playingBoard;
}
