package GUI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private PlayerPanel playerPanel1; // 对方
    private PlayerPanel playerPanel2; // 本客户
    private TablePanel tablePanel; // 牌桌

    public GamePanel() {
        setPreferredSize(new Dimension(960, 720));
    }
}
