package minesweeper.service;

import minesweeper.domain.PlayingBoard;

import java.io.IOException;

public enum GameSaverService {
    INSTANCE;

    PersistenceService persistenceService = PersistenceService.INSTANCE;

    public PlayingBoard getGame(String key) throws IOException {
        return (PlayingBoard)persistenceService.get(key);
    }

    public void saveGame(String key, PlayingBoard playingBoard) throws IOException {
        persistenceService.save(key, playingBoard);
    }
}
