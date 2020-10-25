package minesweeper.service;

import minesweeper.domain.PlayData;
import minesweeper.domain.InititalizationData;
import minesweeper.domain.PlayingBoard;

public enum PlayingBoardService {
    INSTANCE;
    CalculatorMinesService calculatorMinesService = CalculatorMinesService.INSTANCE;

    /*
    * Initilize the play board.
    * */
    public PlayingBoard createPlayBoard(InititalizationData inititalizationData) {
        int rows = inititalizationData.rows;
        int columns = inititalizationData.columns;
        int mines = inititalizationData.mines;

        PlayingBoard playingBoard = new PlayingBoard();
        playingBoard.display = initBoardToShow(rows, columns);
        playingBoard.board = initPlayingBoard(rows, columns);
        playingBoard.board = calculatorMinesService.setMines(playingBoard.board, mines);

        return playingBoard;
    }

    private String[][] initBoardToShow(int rows, int columns) {
        String[][] board = new String[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board[r][col] = "blank";
            }
        }
        return board;
    }

    private int[][] initPlayingBoard(int rows, int columns) {
        int[][] board = new int[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board[r][col] = 0;
            }
        }
        return board;
    }

    public PlayingBoard discoverCell(PlayData playData) {
        PlayingBoard playingBoard = playData.playingBoard;

        return playingBoard;
    }
}

