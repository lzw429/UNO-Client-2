package Model;

import java.util.List;

public class GameTable {
    private int status; // 游戏状态
    private List<String> players; // 房间中的玩家列表
    private int mode; // 游戏模式

    // mode 常量
    public static final int ONLINE = 2;

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
}
