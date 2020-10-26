package minesweeper.domain;

import minesweeper.utils.CellStatus;

import java.io.Serializable;

public class Square implements Serializable {
    public CellStatus display;
    public int value;
}
