package minesweeper.service;

import minesweeper.domain.Cell;
import minesweeper.domain.InititalizationData;
import minesweeper.domain.PlayingBoard;
import minesweeper.utils.CellStatus;
import minesweeper.utils.PlayStatus;

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
        playingBoard.playStatus = PlayStatus.INIT;

        return playingBoard;
    }

    public PlayingBoard discoverCell(PlayingBoard playingBoard, Cell selectedCell) {
        int[][] board = playingBoard.board;
        int selectedRow = selectedCell.row;
        int selectedColumn = selectedCell.column;

        if(cellHasMine(board[selectedRow][selectedColumn])) {
            playingBoard.playStatus = PlayStatus.GAME_OVER;
            revealAllMinesInDisplayBoard(playingBoard);
            return playingBoard;
        }
        else if(allCellsWereDiscovered(playingBoard)) {
            playingBoard.playStatus = PlayStatus.WINNER;
            setCellAsDiscovered(playingBoard.display, selectedRow, selectedColumn);
            revealAllMinesInDisplayBoard(playingBoard);
            return playingBoard;
        } else {
            //Mostrar en el display board las adyacentes con 0.
            playingBoard.playStatus = PlayStatus.CONTINUE;
            updateDisplay(playingBoard, selectedCell);
        }
        return playingBoard;
    }

    private boolean allCellsWereDiscovered(PlayingBoard playingBoard) {
        boolean allCellsWereDiscovered = true;
        String[][] display = playingBoard.display;

        for (int row = 0; row < display.length; row++) {
            for (int col = 0; col < display[0].length; col++) {
                if((display[row][col] == CellStatus.BLANK.toString() || display[row][col] == CellStatus.MARKED.toString()) && playingBoard.board[row][col] != 9) {
                    allCellsWereDiscovered = false;
                    break;
                }
            }
        }
        return allCellsWereDiscovered;
    }

    private void setCellAsDiscovered(String[][] display, int selectedRow, int selectedColumn) {
        display[selectedRow][selectedColumn] = CellStatus.DISCOVERED.toString();
    }

    private void updateDisplay(PlayingBoard playingBoard, Cell selectedCell) {

    }

    private String[][] initBoardToShow(int rows, int columns) {
        String[][] board = new String[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board[r][col] = CellStatus.BLANK.toString();
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

    private void revealAllMinesInDisplayBoard(PlayingBoard playingBoard) {
        String[][] display = playingBoard.display;
        int[][] board = playingBoard.board;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if(cellHasMine(board[row][col])) {
                    display[row][col] = CellStatus.MINE.toString();
                }
            }
        }
    }

    private boolean cellHasMine(int cell) {
        return cell == 9;
    }
}

