package minesweeper.domain;

import minesweeper.utils.PlayStatus;

import java.io.Serializable;

public class PlayingBoard implements Serializable {
    public Square[][] board;
    public PlayStatus playStatus;
    public String id;
}
