package minesweeper.service;

import minesweeper.domain.PlayingBoard;

public enum PlayingBoardService {
    INSTANCE;

    public PlayingBoard getPlayingBoard(int rows, int columns, int mines) {
        initPlayingBoard();
        setMines();
        return getPlayingBoardToShow(rows,columns,mines);
    }

    private PlayingBoard getPlayingBoardToShow(int rows, int columns, int mines) {
        PlayingBoard board = new PlayingBoard();
        board.elements = new String[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board.elements[r][col] = "blank";
            }
        }
        return board;
    }

    private void initPlayingBoard() {
    }

    private void setMines() {
    }
}

