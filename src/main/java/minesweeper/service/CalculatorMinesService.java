package minesweeper.service;

import java.util.Random;

public enum CalculatorMinesService {
    INSTANCE;

    /*
    * Set mines into playboard
    **/
    public int[][] setMines(int[][] board, int mines) {
        int[][] boardWithMines = board;

        int countOfMines = mines;

        while (countOfMines > 0) {
            addMine(board.length, board[0].length, boardWithMines);
            countOfMines--;
        }
        return boardWithMines;
    }

    /*
    * Calculate random position to set mine. Also it will calculate the new value of adyacent positions to new the mine.*/
    private void addMine(int rows, int columns, int[][] boardWithMines) {
        int randomRow;
        int randomColumn;

        do{
            randomRow = new Random().nextInt(rows);
            randomColumn = new Random().nextInt(columns);
        } while (boardWithMines[randomRow][randomColumn] == 9 );

        boardWithMines[randomRow][randomColumn] = 9;

        calculateAdyacents(boardWithMines, randomRow, randomColumn);
    }

    private void calculateAdyacents(int[][] boardWithMines, int mineRow, int mineColumn) {
        for(int row = mineRow -1; row < mineRow + 2; row++) {
            if(isIndexOutOfLimit(boardWithMines.length, row)) {
                continue;
            }
            for (int col = mineColumn -1; col < mineColumn + 2; col++) {
                if(isIndexOutOfLimit(boardWithMines[0].length, col)) {
                    continue;
                }

                int adyacentCell = boardWithMines[row][col];

                if(!hasMine(adyacentCell)){
                    boardWithMines[row][col] = ++adyacentCell;
                }
            }
        }
    }

    private boolean hasMine(int cell) {
        return cell == 9;
    }

    private boolean isIndexOutOfLimit(int length, int index) {
        return index + 1 > length  || index < 0;
    }
}
