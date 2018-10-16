package GUI.GameWindow;

import Model.UNOCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class CardPanel extends JPanel {
    // 卡片颜色
    public final Color RED = new Color(192, 80, 77);
    public final Color BLUE = new Color(31, 73, 125);
    public final Color GREEN = new Color(0, 153, 0);
    public final Color YELLOW = new Color(255, 204, 0);
    public final Color BLACK = new Color(0, 0, 0);
    public final Color GREY = new Color(192, 192, 192);
    public final Color[] colors = {RED, BLUE, GREEN, YELLOW, BLACK};

    public final int red = 0;
    public final int blue = 1;
    public final int green = 2;
    public final int yellow = 3;
    public final int black = 4;

    // 卡片类型
    public final int NUMBERS = 1;
    public final int ACTION = 2;
    public final int WILD = 3;

    // 动作牌字符
    public final Character charREVERSE = (char) 8634; // 十进制
    public final Character charSKIP = (char) Integer.parseInt("2718", 16); // Unicode

    // 动作牌
    public final String REVERSE = charREVERSE.toString();
    public final String SKIP = charSKIP.toString();
    public final String DRAW2PLUS = "2+";

    // 万能牌
    public final String W_COLORPICKER = "W";
    public final String W_DRAW4PLUS = "4+";

    // 卡片尺寸
    public final int WIDTH = 50;
    public final int HEIGHT = 75;
    public final Dimension SMALL = new Dimension(WIDTH, HEIGHT);
    public final Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);
    public final Dimension BIG = new Dimension(WIDTH * 3, HEIGHT * 3);
    public final Dimension CARDSIZE = MEDIUM; // 默认卡片大小

    // 默认偏移
    public final int OFFSET = 71;

    private Color color; // 牌的颜色
    private String value; // 牌上的数字或牌的功能
    private int type; // 牌的类型

    private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    private Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    // 构造方法
    public CardPanel(UNOCard unoCard) {
        this.color = colors[unoCard.getColor()];
        this.value = Integer.toString(unoCard.getValue());
        this.type = unoCard.getType();

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

    /* getter & setter */
    public Color getColor() {
        return color;
    }

    /**
     * 将卡片翻转到背面，无法查看其颜色、数值、类型
     */
    public void flipToBack() {
        Graphics2D g2 = (Graphics2D) getGraphics();

        int cardWidth = CARDSIZE.width;
        int cardHeight = CARDSIZE.height;

        g2.setColor(Color.WHITE); // 背景色
        g2.fillRect(0, 0, cardWidth, cardHeight); // 设置背景

        int margin = 5; // 边缘
        g2.setColor(GREY);
        g2.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin); // 填充边缘内的矩形
    }
}
