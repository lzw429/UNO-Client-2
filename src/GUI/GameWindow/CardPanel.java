package GUI.GameWindow;

import Model.UNOCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends JPanel {
    // 卡片颜色
    public final Color RED = new Color(192, 80, 77);
    public final Color BLUE = new Color(31, 73, 125);
    public final Color GREEN = new Color(0, 153, 0);
    public final Color YELLOW = new Color(255, 204, 0);
    public final Color BLACK = new Color(0, 0, 0);
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

    protected Color color; // 牌的颜色
    protected String value; // 牌上的数字或牌的功能
    protected int type; // 牌的类型

    protected Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    protected Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    /* getter & setter */
    public Color getColor() {
        return color;
    }
}
