package GUIForm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class CardPanelImpl extends JPanel implements CardPanel {
    private Color cardColor; // 牌的颜色
    private String value; // 牌上的数字或牌的功能
    private int type; // 牌的类型

    private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    private Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    public CardPanelImpl(Color cardColor, String value, int type) { // 构造方法
        this.cardColor = cardColor;
        this.value = value;
        this.type = type;

        this.setPreferredSize(CARDSIZE); // 卡片大小
        this.setBorder(defaultBorder); // 卡片边界

        this.addMouseListener(new CardMouseAdapter());
    }

    /**
     * Mouse 监听器
     */
    public class CardMouseAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            // 鼠标进入时 设置边框
            setBorder(focusedBorder);
        }

        public void mouseExited(MouseEvent e) {
            // 鼠标退出时 设置边框
            setBorder(defaultBorder);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int cardWidth = CARDSIZE.width;
        int cardHeight = CARDSIZE.height;

        g2.setColor(Color.WHITE); // 背景色
        g2.fillRect(0, 0, cardWidth, cardHeight); // 设置背景

        int margin = 5; // 边缘
        g2.setColor(cardColor);
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

        g2.setColor(cardColor);
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

    public Color getCardColor() {
        return cardColor;
    }

    public void setCardColor(Color cardColor) {
        this.cardColor = cardColor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
