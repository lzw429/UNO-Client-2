package GUI.GameWindow;

import Model.UNOCard;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class CardFrontPanel extends CardPanel {

    // 构造方法
    public CardFrontPanel(UNOCard unoCard) {
        super(unoCard);
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        int cardWidth = CARDSIZE.width;
        int cardHeight = CARDSIZE.height;

        g2.setColor(Color.WHITE); // 背景色
        g2.fillRect(0, 0, cardWidth, cardHeight); // 设置背景

        int margin = 5; // 边缘
        g2.setColor(color);
        g2.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin); // 填充边缘内的矩形

        g2.setColor(Color.white);
        AffineTransform org = g2.getTransform();
        g2.rotate(45, cardWidth * 3 / 4, cardHeight * 3 / 4);

        // 一个逆时针旋转 45 度的椭圆
        g2.fillOval(0, cardHeight * 1 / 4, cardWidth * 3 / 5, cardHeight);
        g2.setTransform(org);

        // 中间的值
        Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth / 2 + 5);
        FontMetrics fm = this.getFontMetrics(defaultFont);
        int StringWidth = fm.stringWidth(value) / 2;
        int FontHeight = defaultFont.getSize() * 1 / 3;

        g2.setColor(color);
        g2.setFont(defaultFont);
        g2.drawString(value, cardWidth / 2 - StringWidth, cardHeight / 2 + FontHeight);

        // 四周的值
        defaultFont = new Font("Helvetica", Font.ITALIC, cardWidth / 5);
        fm = this.getFontMetrics(defaultFont);
        StringWidth = fm.stringWidth(value) / 2;
        FontHeight = defaultFont.getSize() * 1 / 3;

        g2.setColor(Color.white);
        g2.setFont(defaultFont);
        g2.drawString(value, 2 * margin, 5 * margin);
    }
}
