package GUI.GameWindow;

import java.awt.*;

public interface CardPanel {
    // 卡片颜色
    Color RED = new Color(192, 80, 77);
    Color BLUE = new Color(31, 73, 125);
    Color GREEN = new Color(0, 153, 0);
    Color YELLOW = new Color(255, 204, 0);
    Color BLACK = new Color(0, 0, 0);

    // 卡片类型
    int NUMBERS = 1;
    int ACTION = 2;
    int WILD = 3;

    // 动作牌字符
    Character charREVERSE = (char) 8634; // 十进制
    Character charSKIP = (char) Integer.parseInt("2718", 16); // Unicode

    // 动作牌
    String REVERSE = charREVERSE.toString();
    String SKIP = charSKIP.toString();
    String DRAW2PLUS = "2+";

    // 万能牌
    String W_COLORPICKER = "W";
    String W_DRAW4PLUS = "4+";

    // 卡片尺寸
    int WIDTH = 50;
    int HEIGHT = 75;
    Dimension SMALL = new Dimension(WIDTH, HEIGHT);
    Dimension MEDIUM = new Dimension(WIDTH * 2, HEIGHT * 2);
    Dimension BIG = new Dimension(WIDTH * 3, HEIGHT * 3);
    Dimension CARDSIZE = MEDIUM; // 默认卡片大小

    // 默认偏移
    int OFFSET = 71;
}
