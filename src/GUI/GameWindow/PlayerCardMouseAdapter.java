package GUI.GameWindow;

import Service.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 玩家当前持有卡牌的鼠标监听器
 */
public class PlayerCardMouseAdapter extends MouseAdapter {
    private CardFrontPanel sourceCard;
    private GameService gameService = new GameService();

    public void mousePressed(MouseEvent e) {
        sourceCard = (CardFrontPanel) e.getSource();
        if (sourceCard.getType() == CardPanel.WILD) {
            // 选择万能牌颜色
            List<String> colors = new CopyOnWriteArrayList<>();
            colors.add("红");
            colors.add("蓝");
            colors.add("绿");
            colors.add("黄");

            String chosenColor = (String) JOptionPane.showInputDialog(null, "选择颜色", "万能卡指定颜色",
                    JOptionPane.PLAIN_MESSAGE, null, colors.toArray(), null);

        }
        // 打出这张牌
        gameService.playCardRequest(sourceCard);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);

        // 调整位置
        sourceCard = (CardFrontPanel) e.getSource();
        Point p = sourceCard.getLocation();
        p.y -= 20;
        sourceCard.setLocation(p);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // 调整位置
        sourceCard = (CardFrontPanel) e.getSource();
        Point point = sourceCard.getLocation();
        point.y += 20;
        sourceCard.setLocation(point);
    }
}
