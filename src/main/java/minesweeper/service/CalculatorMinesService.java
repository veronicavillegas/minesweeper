package minesweeper.service;

import minesweeper.domain.Square;

import java.util.ArrayList;
import java.util.Random;

public enum CalculatorMinesService {
    INSTANCE;

    /*
    * Set mines into playboard
    **/
    public Square[][] setMines(Square[][] board, int mines) {
        Square[][] boardWithMines = board;

        int countOfMines = mines;

        while (countOfMines > 0) {
            addMine(board.length, board[0].length, boardWithMines);
            countOfMines--;
        }
        return boardWithMines;
    }

    /*
    * Calculate random position to set mine. Also it will calculate the new value of adyacent positions to new the mine.*/
    private void addMine(int rows, int columns, Square[][] boardWithMines) {
        int randomRow;
        int randomColumn;

        do{
            randomRow = new Random().nextInt(rows);
            randomColumn = new Random().nextInt(columns);
        } while (boardWithMines[randomRow][randomColumn].value == 9 );

        boardWithMines[randomRow][randomColumn].value = 9;

        calculateAdyacents(boardWithMines, randomRow, randomColumn);
    }

    private ArrayList<Square> calculateAdyacents(Square[][] boardWithMines, int mineRow, int mineColumn) {
        ArrayList<Square> adjacents = new ArrayList<>();

        for(int row = mineRow -1; row < mineRow + 2; row++) {
            if(isIndexOutOfLimit(boardWithMines.length, row)) {
                continue;
            }
            for (int col = mineColumn -1; col < mineColumn + 2; col++) {
                if(isIndexOutOfLimit(boardWithMines[0].length, col)) {
                    continue;
                }
                adjacents.add(boardWithMines[row][col]);

                int adyacentCell = boardWithMines[row][col].value;

                if(!hasMine(adyacentCell)){
                    boardWithMines[row][col].value = ++adyacentCell;
                }
            }
        }
        return adjacents;
    }

    private boolean hasMine(int cell) {
        return cell == 9;
    }

    private boolean isIndexOutOfLimit(int length, int index) {
        return index + 1 > length  || index < 0;
    }

    private void discoverAdjacentCells(Square[][] playingBoard, int row, int column) {
        ArrayList<Square> adjacents = calculateAdyacents(playingBoard, row, column);
        for(Square square : adjacents) {
            if(square.value == 0) {
                discoverAdjacentCells(playingBoard, a);
            }
        }

    }
}
