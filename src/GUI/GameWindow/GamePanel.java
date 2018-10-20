package GUI.GameWindow;

import Model.GameTable;
import Model.Player;
import Model.UNOCard;
import Util.OnlineUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class GamePanel extends JPanel {
    private PlayerPanel playerPanel1; // 对方
    private PlayerPanel playerPanel2; // 本客户
    private TablePanel tablePanel; // 牌桌

    public GamePanel(GameTable gameTable) {
        int remainCardNum = gameTable.getRemainCardNum();
        List<Player> playerList = gameTable.getPlayers();
        CardFrontPanel firstCardPanel = new CardFrontPanel(gameTable.getFirstCard());

        setPreferredSize(new Dimension(960, 720));
        setBackground(new Color(30, 36, 40));
        setLayout(new BorderLayout());

        for (Player player : playerList) {
            if (player.getUsername().equals(OnlineUtil.getUsername())) { // 该 player 对象对应本用户
                playerPanel2 = new PlayerPanel(player);
            } else {
                playerPanel1 = new PlayerPanel(player);
            }
        }
        tablePanel = new TablePanel(remainCardNum, firstCardPanel);
        playerPanel1.setOpaque(false);
        playerPanel2.setOpaque(false);

        add(playerPanel1, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(playerPanel2, BorderLayout.SOUTH);
    }

    /* getter & setter */

    public PlayerPanel getPlayerPanel1() {
        return playerPanel1;
    }

    public void setPlayerPanel1(PlayerPanel playerPanel1) {
        this.playerPanel1 = playerPanel1;
    }

    public PlayerPanel getPlayerPanel2() {
        return playerPanel2;
    }

    public void setPlayerPanel2(PlayerPanel playerPanel2) {
        this.playerPanel2 = playerPanel2;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public void setTablePanel(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    /* 成员方法 */
    boolean isValidMove(UNOCard unoCard) {
        // todo code
        return false;
    }

    public void refreshPanel(GameTable gameTable) {
        for (Player player : gameTable.getPlayers()) {
            if (OnlineUtil.isThisClient(player)) {
                playerPanel2.setCards(player);
            } else {
                playerPanel1.setCards(player);
            }
            if (player.isMyTurn()) {
                if (OnlineUtil.isThisClient(player))
                    this.tablePanel.getInfoPanel().setMessage("轮到您");
                else this.tablePanel.getInfoPanel().setMessage("轮到 " + player.getUsername());
            }
            this.tablePanel.getInfoPanel().setError("");
        }
        // 业务无关
        tablePanel.revalidate();
        revalidate();
    }
}
