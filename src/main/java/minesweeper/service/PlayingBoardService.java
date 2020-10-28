package minesweeper.service;

import minesweeper.domain.*;
import minesweeper.utils.CellStatus;
import minesweeper.utils.PlayStatus;

import java.io.IOException;
import java.time.LocalDateTime;

public enum PlayingBoardService {
    INSTANCE;
    CalculatorService calculatorService = CalculatorService.INSTANCE;
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
        playingBoard.board = calculatorService.setMines(playingBoard.board, mines);
        playingBoard.playStatus = PlayStatus.INIT;
        playingBoard.id = getPlayingBoardId(inititalizationData.user);

        persistenceService.saveGame(inititalizationData.user, playingBoard);

        return playingBoard;
    }

    public PlayingBoard discoverCell(PlayData playData) throws IOException {
        //TODO: Get playingBoard form MCC
        PlayingBoard playingBoard = getGame(playData.id);
        Cell selectedCell = playData.selectedCell;

        Square[][] board = playingBoard.board;
        int selectedRow = selectedCell.row;
        int selectedColumn = selectedCell.column;

        setCellAsDiscovered(playingBoard, selectedCell);

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
        updateGame(playData.id, playingBoard);
        return playingBoard;
    }

    private void updateGame(String boardId, PlayingBoard playingBoard) throws IOException {
        persistenceService.updateGame(boardId, playingBoard);
    }

    public PlayingBoard getGame(String id) throws IOException {
        return persistenceService.getGame(id);
    }


    private String getPlayingBoardId(String user) {
        String actualDateTime = LocalDateTime.now().toString();
        return user+actualDateTime;
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
            return;
        }
        calculatorService.discoverAdjacentCells(playingBoard.board, selectedCell.row, selectedCell.column);
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
                board[r][col].value = 0;
                board[r][col].row = r;
                board[r][col].column = col;
                board[r][col].display = CellStatus.BLANK;
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
                else {
                    board[row][col].display = CellStatus.DISCOVERED;
                }
            }
        }
    }

    private boolean cellHasMine(int cell) {
        return cell == 9;
    }
}

