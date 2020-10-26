package minesweeper.service;

import minesweeper.domain.Square;
import minesweeper.utils.CellStatus;

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

        calculateAdjacents(boardWithMines, randomRow, randomColumn);
    }

    private void calculateAdjacents(Square[][] boardWithMines, int randomRow, int randomColumn) {
        ArrayList<Square> adjacents = getAdjacents(boardWithMines, randomRow, randomColumn);

        for(Square square : adjacents) {
            if(!hasMine(square.value)){
                boardWithMines[square.row][square.column].value++;
            }
        }
    }


    private ArrayList<Square> getAdjacents(Square[][] boardWithMines, int centralRow, int centralColumn) {
        ArrayList<Square> adjacents = new ArrayList<>();

        for(int row = centralRow -1; row < centralRow + 2; row++) {
            if(isIndexOutOfLimit(boardWithMines.length, row)) {
                continue;
            }
            for (int col = centralColumn -1; col < centralColumn + 2; col++) {
                if(isIndexOutOfLimit(boardWithMines[0].length, col)) {
                    continue;
                }

                boolean adjacentIsCentralSquare = centralColumn == col && centralRow == row;

                if(adjacentIsCentralSquare){
                    continue;
                }
                adjacents.add(boardWithMines[row][col]);
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

    public void discoverAdjacentCells(Square[][] playingBoard, int row, int column) {
        ArrayList<Square> adjacents = getAdjacents(playingBoard, row, column);
        for(Square square : adjacents) {
            if(square.value == 0 && square.display==CellStatus.BLANK) {
                square.display = CellStatus.DISCOVERED;
                discoverAdjacentCells(playingBoard, square.row, square.column);
            }
        }
    }
}
