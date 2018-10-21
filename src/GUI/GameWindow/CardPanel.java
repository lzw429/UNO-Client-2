package GUI.GameWindow;

import Model.UNOCard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardPanel extends JPanel {
    // 卡片颜色
    public static Color RED = new Color(192, 80, 77);
    public static Color BLUE = new Color(31, 73, 125);
    public static Color GREEN = new Color(0, 153, 0);
    public static Color YELLOW = new Color(255, 204, 0);
    public static Color BLACK = new Color(0, 0, 0);
    public static Color[] colors = {RED, BLUE, GREEN, YELLOW, BLACK};

    public static final int red = 0;
    public static final int blue = 1;
    public static final int green = 2;
    public static final int yellow = 3;

    // 动作牌字符
    public static Character charREVERSE = (char) 8634; // 十进制
    public static Character charSKIP = (char) Integer.parseInt("2718", 16); // Unicode

    // 动作牌
    public static String REVERSE = charREVERSE.toString();
    public static String SKIP = charSKIP.toString();
    public static String DRAW2PLUS = "2+";

    // 万能牌
    public static String W_COLORPICKER = "W";
    public static String W_DRAW4PLUS = "4+";
    public static String[] actionTypes = {REVERSE, SKIP, DRAW2PLUS}; // 动作牌
    public static String[] wildTypes = {W_COLORPICKER, W_DRAW4PLUS}; // 万能牌

    // 卡片类型
    public static final int NUMBERS = 1;
    public static final int ACTION = 2;
    public static final int WILD = 3;

    // 卡片尺寸
    public final int WIDTH = 50;
    public final int HEIGHT = 75;
    public final Dimension SMALL = new Dimension(WIDTH, HEIGHT);
    public final Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);
    public final Dimension BIG = new Dimension(WIDTH * 3, HEIGHT * 3);
    public final Dimension CARDSIZE = MEDIUM; // 默认卡片大小

    // 默认偏移
    public final int OFFSET = 71;

    protected int number; // 牌号
    protected Color color; // 牌的颜色
    protected String value; // 牌上的数字或牌的功能
    protected int type; // 牌的类型

    protected Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    protected Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    // 构造方法
    public CardPanel(UNOCard unoCard) {
        this.number = unoCard.getNumber();
        this.color = colors[unoCard.getColor()];
        this.type = unoCard.getType();
        switch (unoCard.getType()) {
            case NUMBERS:
                this.value = String.valueOf(unoCard.getValue());
                break;
            case ACTION:
                this.value = actionTypes[unoCard.getValue()];
                break;
            case WILD:
                this.value = wildTypes[unoCard.getValue()];
                break;
        }
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
    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}
