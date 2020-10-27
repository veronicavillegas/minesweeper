package minesweeper.service;

import minesweeper.domain.PlayingBoard;

import java.io.IOException;
import java.util.ArrayList;

public enum PersistenceService {
    INSTANCE;

    MemcachedService memcachedService = MemcachedService.INSTANCE;

    public PlayingBoard getGame(String idBoard) throws IOException {
        return (PlayingBoard)memcachedService.get(idBoard);
    }

    public ArrayList<PlayingBoard> getGamesOfUser (String user) throws IOException {
        ArrayList<String> gamesIds = (ArrayList<String>) memcachedService.get(user);
        ArrayList<PlayingBoard> playingBoards = new ArrayList<>();

        for (String gameId : gamesIds) {
            PlayingBoard playingBoard = (PlayingBoard) memcachedService.get(gameId);
            playingBoards.add(playingBoard);
            return playingBoards;
        }
        return null;
    }

    public void saveGame(String user, PlayingBoard playingBoard) throws IOException {
        ArrayList<String> gamesIds = (ArrayList<String>)memcachedService.get(user);

        if(gamesIds == null) {
            gamesIds = new ArrayList<>();
        }
        gamesIds.add(playingBoard.id);

        memcachedService.save(user, gamesIds);
        memcachedService.save(playingBoard.id, playingBoard);
    }

    public void updateGame(String boardId, PlayingBoard playingBoard) throws IOException {
        memcachedService.update(boardId, playingBoard);
    }
}
