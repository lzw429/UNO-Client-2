package GUI.GameWindow;

import Model.UNOCard;

import java.awt.*;

public class CardBackPanel extends CardPanel {

    // 构造方法
    public CardBackPanel(UNOCard unoCard) {
        super(unoCard);
    }

    /**
     * 展示卡片背面，无法查看其颜色、数值、类型
     */
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        int cardWidth = CARDSIZE.width;
        int cardHeight = CARDSIZE.height;

        g2.setColor(Color.WHITE); // 背景色
        g2.fillRect(0, 0, cardWidth, cardHeight); // 设置背景

        int margin = 5; // 边缘
        g2.setColor(Color.GRAY);
        g2.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin); // 填充边缘内的矩形
    }
}
