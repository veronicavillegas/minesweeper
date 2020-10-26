package minesweeper.service;

import minesweeper.domain.Cell;
import minesweeper.domain.InititalizationData;
import minesweeper.domain.PlayingBoard;
import minesweeper.domain.Square;
import minesweeper.utils.CellStatus;
import minesweeper.utils.PlayStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public enum PlayingBoardService {
    INSTANCE;
    CalculatorMinesService calculatorMinesService = CalculatorMinesService.INSTANCE;
    PersistenceService persistenceService = PersistenceService.INSTANCE;

    /*
    * Initilize the play board.
    * */
    public PlayingBoard createPlayBoard(InititalizationData inititalizationData) throws IOException {
        int rows = inititalizationData.rows;
        int columns = inititalizationData.columns;
        int mines = inititalizationData.mines;

        PlayingBoard playingBoard = new PlayingBoard();
        playingBoard.board = initBoard(rows, columns);
        playingBoard.board = calculatorMinesService.setMines(playingBoard.board, mines);
        playingBoard.playStatus = PlayStatus.INIT;
        playingBoard.id = getPlayingBoardId(inititalizationData.user);

        saveGame(inititalizationData.user, playingBoard);

        return playingBoard;
    }

    private String getPlayingBoardId(String user) {
        String actualDateTime = LocalDateTime.now().toString();
        return user+actualDateTime;
    }

    private void saveGame(String user, PlayingBoard playingBoard) throws IOException {
        ArrayList<String> gamesIds = new ArrayList<>();
        gamesIds.add(playingBoard.id);

        persistenceService.save(user, gamesIds);
        persistenceService.save(playingBoard.id, playingBoard);
    }

    public PlayingBoard discoverCell(PlayingBoard playingBoard, Cell selectedCell) {
        //TODO: Get playingBoard form MCC

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
                if((board[row][col].display == CellStatus.BLANK || board[row][col].display == CellStatus.MARKED)
                        && board[row][col].value != 9) {
                    allCellsWereDiscovered = false;
                    break;
                }
            }
        }
        return allCellsWereDiscovered;
    }

    private void setCellAsDiscovered(Square[][] board, int selectedRow, int selectedColumn) {
        board[selectedRow][selectedColumn].display = CellStatus.DISCOVERED;
    }

    private void updateDisplay(PlayingBoard playingBoard, Cell selectedCell) {
        playingBoard.playStatus = PlayStatus.CONTINUE;

        if (isSelectedCellAdjacentToMine(playingBoard.board, selectedCell.row, selectedCell.column)) {
            setCellAsDiscovered(playingBoard, selectedCell);
            return;
        }
        calculatorMinesService.discoverAdjacentCells(playingBoard.board, selectedCell.row, selectedCell.column);
    }

    private void setCellAsDiscovered(PlayingBoard playingBoard, Cell selectedCell) {
        playingBoard.board[selectedCell.row][selectedCell.column].display = CellStatus.DISCOVERED;
    }

    private boolean isSelectedCellAdjacentToMine(Square[][] playingBoard, int row, int column) {
        int selectedSquareValue = playingBoard[row][column].value;

        if(selectedSquareValue == 0) {
            return false;
        }
        return true;
    }

    private Square[][] initBoard(int rows, int columns) {
        Square[][] board = new Square[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int col = 0; col < columns; col++) {
                board[r][col] = new Square();
                board[r][col].display = CellStatus.BLANK;
                board[r][col].value = 0;
                board[r][col].row = r;
                board[r][col].column = col;
            }
        }
        return board;
    }

    private void revealAllMinesInDisplayBoard(PlayingBoard playingBoard) {
        Square[][] board = playingBoard.board;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if(cellHasMine(board[row][col].value)) {
                    board[row][col].display = CellStatus.MINE;
                }
            }
        }
    }

    private boolean cellHasMine(int cell) {
        return cell == 9;
    }
}

