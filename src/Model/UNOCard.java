package Model;

public class UNOCard {
    private int number;
    private int color;
    private int type;
    private int value;

    /* 构造方法 */

    public UNOCard(int number, int color, int type, int value) {
        this.number = number;
        this.color = color;
        this.type = type;
        this.value = value;
    }

    /* getter */

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
