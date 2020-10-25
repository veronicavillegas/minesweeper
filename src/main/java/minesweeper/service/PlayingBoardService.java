package minesweeper.service;

import minesweeper.domain.Cell;
import minesweeper.domain.InititalizationData;
import minesweeper.domain.PlayingBoard;
import minesweeper.domain.Square;
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
        playingBoard.board = initBoard(rows, columns);
        playingBoard.board = calculatorMinesService.setMines(playingBoard.board, mines);
        playingBoard.playStatus = PlayStatus.INIT;

        return playingBoard;
    }

    public PlayingBoard discoverCell(PlayingBoard playingBoard, Cell selectedCell) {
        Square[][] board = playingBoard.board;
        int selectedRow = selectedCell.row;
        int selectedColumn = selectedCell.column;

        if(cellHasMine(board[selectedRow][selectedColumn].value)) {
            playingBoard.playStatus = PlayStatus.GAME_OVER;
            revealAllMinesInDisplayBoard(playingBoard);
            return playingBoard;
        }
        else if(allCellsWereDiscovered(playingBoard)) {
            playingBoard.playStatus = PlayStatus.WIN;
            setCellAsDiscovered(playingBoard.board, selectedRow, selectedColumn);
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
        Square[][] board = playingBoard.board;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if((board[row][col].display == CellStatus.BLANK.toString() || board[row][col].display == CellStatus.MARKED.toString())
                        && board[row][col].value != 9) {
                    allCellsWereDiscovered = false;
                    break;
                }
            }
        }
        return allCellsWereDiscovered;
    }

    private void setCellAsDiscovered(Square[][] board, int selectedRow, int selectedColumn) {
        board[selectedRow][selectedColumn].display = CellStatus.DISCOVERED.toString();
    }

    private void updateDisplay(PlayingBoard playingBoard, Cell selectedCell) {

    }

    private Square[][] initBoard(int rows, int columns) {
        Square[][] board = new Square[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board[r][col] = new Square();
                board[r][col].display = CellStatus.BLANK.toString();
                board[r][col].value = 0;
            }
        }
        return board;
    }

    private void revealAllMinesInDisplayBoard(PlayingBoard playingBoard) {
        Square[][] board = playingBoard.board;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if(cellHasMine(board[row][col].value)) {
                    board[row][col].display = CellStatus.MINE.toString();
                }
            }
        }
    }

    private boolean cellHasMine(int cell) {
        return cell == 9;
    }
}

