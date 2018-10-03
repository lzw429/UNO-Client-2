package Model;

import java.util.List;

public class GameTable {
    private boolean started; // 是否正在游戏中
    private List<String> players; // 房间中的玩家列表

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public GameTable(boolean started, List<String> players) {
        this.started = started;
        this.players = players;
    }
}
