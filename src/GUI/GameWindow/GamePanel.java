package GUI.GameWindow;

import Model.Player;
import Model.UNOCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class GamePanel extends JPanel {
    private PlayerPanel playerPanel1; // 对方
    private PlayerPanel playerPanel2; // 本客户
    private TablePanel tablePanel; // 牌桌

    public GamePanel(int remainCardNum, CardPanel firstCardPanel, List<Player> playerList) {
        setPreferredSize(new Dimension(960, 720));
        setBackground(new Color(30, 36, 40));
        setLayout(new BorderLayout());

        playerPanel1 = new PlayerPanel(playerList.get(0));
        playerPanel2 = new PlayerPanel(playerList.get(1));
        tablePanel = new TablePanel(remainCardNum, firstCardPanel);
        playerPanel1.setOpaque(false);
        playerPanel2.setOpaque(false);

        add(playerPanel1, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(playerPanel2, BorderLayout.SOUTH);
    }

    boolean isValidMove(UNOCard unoCard) {
        // todo code
        return false;
    }
}
