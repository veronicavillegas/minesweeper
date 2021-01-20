package minesweeper.service.interfaces;

import minesweeper.domain.InititalizationData;
import minesweeper.domain.PlayData;
import minesweeper.domain.PlayingBoard;

import java.io.IOException;

public interface PlayingBoardService {
    /*
    * Initilize the play board.
    * */
    PlayingBoard createPlayBoard(InititalizationData inititalizationData) throws IOException;

    PlayingBoard playGame(PlayData playData) throws IOException;

    PlayingBoard getGame(String id) throws IOException;
}
