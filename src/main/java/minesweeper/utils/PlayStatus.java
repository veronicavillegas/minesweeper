package minesweeper.utils;

import java.io.Serializable;

public enum PlayStatus implements Serializable {
    INIT,
    GAME_OVER,
    CONTINUE,
    WIN,
}
