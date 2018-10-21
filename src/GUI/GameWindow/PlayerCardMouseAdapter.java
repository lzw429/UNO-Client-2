package GUI.GameWindow;

import Service.GameService;
import Util.TimeUtil;

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
        if (sourceCard.getType() == CardPanel.WILD) { // 打出万能牌
            // 选择万能牌颜色
            List<String> colors = new CopyOnWriteArrayList<>();
            colors.add("红");
            colors.add("蓝");
            colors.add("绿");
            colors.add("黄");

            String chosenColor = (String) JOptionPane.showInputDialog(null, "选择颜色", "万能卡指定颜色",
                    JOptionPane.PLAIN_MESSAGE, null, colors.toArray(), null);
            if (chosenColor == null) return; // 不打出这张牌
            int selectedColor = -1;
            switch (chosenColor) {
                case "红":
                    selectedColor = CardPanel.red;
                    break;
                case "蓝":
                    selectedColor = CardPanel.blue;
                    break;
                case "绿":
                    selectedColor = CardPanel.green;
                    break;
                case "黄":
                    selectedColor = CardPanel.yellow;
                    break;
                default:
                    break;
            }
            if (selectedColor == -1) { // 正常的取值是 0～3 范围内的整数
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] PLayerCardMouseAdapter: choose wild card color failed"); // 颜色选择错误
                return;
            }
            gameService.playCardRequest(sourceCard, selectedColor);
        } else // 打出数字牌或功能牌
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
