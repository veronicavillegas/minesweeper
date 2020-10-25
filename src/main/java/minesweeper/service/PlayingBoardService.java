package minesweeper.service;

import minesweeper.domain.PlayingBoard;

public enum PlayingBoardService {
    INSTANCE;
    CalculatorMinesService calculatorMinesService = CalculatorMinesService.INSTANCE;

    public PlayingBoard getPlayingBoard(int rows, int columns, int mines) {
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
}

