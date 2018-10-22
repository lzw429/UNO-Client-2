package Model;

import GUI.GameWindow.CardPanel;

import java.awt.*;
import java.util.List;

public class GameTable {
    private int status; // 游戏状态
    private int mode; // 游戏模式
    private int remainCardNum; // 剩余卡牌数
    private UNOCard topCard;
    private List<Player> players; // 房间中的玩家列表
    private Color tableBackgroundColor;

    // mode 常量
    public static final int ONLINE = 2;

    /**
     * 构造方法
     */
    public GameTable(int mode, int remainCardNum, UNOCard firstCard, List<Player> playerList) {
        this.mode = mode;
        this.remainCardNum = remainCardNum;
        this.topCard = firstCard;
        this.players = playerList;
        this.tableBackgroundColor = CardPanel.colors[topCard.getColor()];
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

    public UNOCard getTopCard() {
        return topCard;
    }

    public void setTopCard(UNOCard topCard) {
        this.topCard = topCard;
    }

    public Color getTableBackgroundColor() {
        return tableBackgroundColor;
    }

    public void setTableBackgroundColor(Color tableBackgroundColor) {
        this.tableBackgroundColor = tableBackgroundColor;
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
