package Model;

import Util.OnlineUtil;
import Util.TimeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameTable {
    private int status; // 游戏状态
    private List<String> players; // 房间中的玩家列表
    private int mode; // 游戏模式

    // mode 常量
    public static final int ONLINE = 2;

    /**
     * 构造方法
     */
    public GameTable(int mode) {
        if (mode == ONLINE)
            OnlineGameTable();
    }

    /**
     * 初始化在线对局
     */
    public void OnlineGameTable() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameTable: create online GameTable exception");
        }
    }

    // getter & setter
    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    /**
     * 检查当前打出的牌是否有效
     *
     * @param playedCard 打出的牌
     * @return 有效 true；无效 false
     */
    public boolean isValidMove(UNOCard playedCard) {
        // todo code
        return false;
    }
}
