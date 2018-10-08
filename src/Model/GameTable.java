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
            String msg = "uno02 player " + OnlineUtil.getRoomNum() + "\r\n";
            OnlineUtil.sendMsg(msg);
            System.out.println("[" + TimeUtil.getTimeInMillis() + "] creating online gametable");
            String receive = OnlineUtil.receiveMsg();
            if (receive == null || !receive.startsWith("uno02 player")) {
                System.out.println("GameTable: message timeout");
                return;
            }
            receive = receive.substring(0, receive.length() - 2); // 去除字符串末尾 \r\n
            String[] receive_split = receive.split(" ");
            String[] playerList = receive_split[2].split(",");
            if (playerList.length == 0) {
                System.out.println("GameTable: player list is empty");
                return;
            }
            players = new ArrayList<>(playerList.length);
            players.addAll(Arrays.asList(playerList));
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
    }
}
