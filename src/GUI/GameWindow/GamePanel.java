package GUI.GameWindow;

import Model.UNOCard;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private PlayerPanel playerPanel1; // 对方
    private PlayerPanel playerPanel2; // 本客户
    private TablePanel tablePanel; // 牌桌

    public GamePanel() {
        setPreferredSize(new Dimension(960, 720));
        setBackground(new Color(30, 36, 40));
        setLayout(new BorderLayout());
    }

    boolean isValidMove(UNOCard unoCard) {
        // todo code
        return false;
    }
}
