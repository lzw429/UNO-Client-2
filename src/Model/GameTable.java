package Model;

import java.util.List;

public class GameTable {
    private int status; // 游戏状态
    private int mode; // 游戏模式
    private int remainCardNum; // 剩余卡牌数
    private UNOCard firstCard;
    private List<Player> players; // 房间中的玩家列表

    // mode 常量
    public static final int ONLINE = 2;

    /**
     * 构造方法
     */
    public GameTable(int mode, int remainCardNum, UNOCard firstCard, List<Player> playerList) {
        this.mode = mode;
        this.remainCardNum = remainCardNum;
        this.firstCard = firstCard;
        this.players = playerList;
    }

    // getter & setter
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getRemainCardNum() {
        return remainCardNum;
    }

    public void setRemainCardNum(int remainCardNum) {
        this.remainCardNum = remainCardNum;
    }

    public UNOCard getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(UNOCard firstCard) {
        this.firstCard = firstCard;
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

    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public void setTurn(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                player.setMyTurn(true);
            } else player.setMyTurn(false);
        }
    }
}
