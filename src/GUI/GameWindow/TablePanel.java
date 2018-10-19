package GUI.GameWindow;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    private CardFrontPanel topCard;
    private JPanel table;
    private InfoPanel infoPanel;

    /* getter & setter */

    public CardFrontPanel getTopCard() {
        return topCard;
    }

    public JPanel getTable() {
        return table;
    }

    public void setTable(JPanel table) {
        this.table = table;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public TablePanel(int remainCardNum, CardFrontPanel firstCard) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        topCard = firstCard; // 牌桌上首张牌
        table = new JPanel();
        table.setBackground(new Color(64, 64, 64));
        infoPanel = new InfoPanel(remainCardNum);

        setTable();
        setComponents();
    }

    private void setTable() {
        table.setPreferredSize(new Dimension(500, 200));
        table.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        table.add(topCard, gridBagConstraints);
    }

    private void setComponents() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 130, 0, 45);
        add(table, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 1, 0, 1);
        add(infoPanel, gridBagConstraints);
    }

    /**
     * 将一张牌放置在牌桌上
     *
     * @param playedCard 打出的牌
     */
    public void setPlayedCard(CardFrontPanel playedCard) {
        table.removeAll();
        topCard = playedCard;
        setTable();

        setBackgroundColor(playedCard);
    }

    /**
     * 将牌桌颜色设定为指定牌的颜色
     *
     * @param playedCard 打出的牌
     */
    private void setBackgroundColor(CardFrontPanel playedCard) {
        Color background = playedCard.getColor();
        table.setBackground(background);
    }
}
