package minesweeper.service;

import minesweeper.client.MemcachedMinesweeperClient;
import minesweeper.domain.PlayingBoard;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    MemcachedMinesweeperClient memcachedMinesweeperClient;

    @Autowired
    public PersistenceService(MemcachedMinesweeperClient memcachedMinesweeperClient) {
        this.memcachedMinesweeperClient = memcachedMinesweeperClient;
    }

    public PlayingBoard getGame(String idBoard) {
        return (PlayingBoard)memcachedMinesweeperClient.get(idBoard);
    }

    public ArrayList<PlayingBoard> getGamesOfUser (String user) {
        ArrayList<String> gamesIds = (ArrayList<String>) memcachedMinesweeperClient.get(user);
        ArrayList<PlayingBoard> playingBoards = new ArrayList<>();

        for (String gameId : gamesIds) {
            PlayingBoard playingBoard = (PlayingBoard) memcachedMinesweeperClient.get(gameId);
            playingBoards.add(playingBoard);
            return playingBoards;
        }
        return null;
    }

    public void saveGame(String user, PlayingBoard playingBoard){
        ArrayList<String> gamesIds = (ArrayList<String>)memcachedMinesweeperClient.get(user);

        if(gamesIds == null) {
            gamesIds = new ArrayList<>();
        }
        gamesIds.add(playingBoard.id);

        memcachedMinesweeperClient.save(user, gamesIds);
        memcachedMinesweeperClient.save(playingBoard.id, playingBoard);
    }

    public void updateGame(String boardId, PlayingBoard playingBoard) {
        memcachedMinesweeperClient.update(boardId, playingBoard);
    }
}
