package minesweeper.service;

import minesweeper.domain.PlayingBoard;

import java.io.IOException;
import java.util.ArrayList;

public enum PersistenceService {
    INSTANCE;

    MemcachedService memcachedService = MemcachedService.INSTANCE;

    public PlayingBoard getGame(String user, String idBoard) throws IOException {
        ArrayList<String> gamesIds = (ArrayList<String>) memcachedService.get(user);
        for(String gameId : gamesIds){
            if(gameId.equals(idBoard)) {
                return (PlayingBoard)memcachedService.get(gameId);
            }
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
