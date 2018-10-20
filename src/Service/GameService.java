package Service;

import GUI.GameWindow.CardPanel;
import GUI.GameWindow.GameFrame;
import GUI.GameWindow.GamePanel;
import Model.GameTable;
import Model.Player;
import Model.UNOCard;
import Util.OnlineUtil;

import java.util.List;

public class GameService {
    private static GameTable gameTable = null;

    /* getter & setter */

    public static GameTable getGameTable() {
        return gameTable;
    }

    public void gameTableRequest() {
        try {
            String msg = "uno02 hall\r\n";
            OnlineUtil.sendMsg(msg); // 请求游戏大厅数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: get game tables data exception");
        }
    }


    public void gameStart(int remainCardNum, UNOCard firstCard, List<Player> playerList) {
        // 模型层 GameTable
        gameTable = new GameTable(GameTable.ONLINE, remainCardNum, firstCard, playerList);

        // 视图层
        GameFrame.setGamePanel(new GamePanel(gameTable));
        GameFrame.main(new String[10]); // 打开游戏窗口
    }


    public void drawCardRequest() { // uno02 drawcard username roomnum
        if (isNotMyTurn()) return; // 判断轮次
        String username = OnlineUtil.getUsername();
        String roomNum = OnlineUtil.getRoomNum();
        String msg = "uno02 drawcard " + username + " " + roomNum + "\r\n";
        try {
            OnlineUtil.sendMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: draw card request exception");
        }
    }


    public void sayUNORequest() { // uno02 sayuno username roomnum
        if (isNotMyTurn()) return; // 判断轮次
        String username = OnlineUtil.getUsername();
        String roomNum = OnlineUtil.getRoomNum();
        String msg = "uno02 sayuno " + username + " " + roomNum + "\r\n";
        try {
            OnlineUtil.sendMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: say UNO request exception");
        }
    }

    /**
     * 请求打出一张牌
     *
     * @param cardPanel 卡牌面板
     */
    public void playCardRequest(CardPanel cardPanel) { // uno02 playcard username roomnum cardnum
        if (isNotMyTurn()) return; // 判断轮次
        if (cannotPlayThisCard(cardPanel)) return; // 判断当前规则是否允许打出该牌
        String username = OnlineUtil.getUsername();
        String roomNum = OnlineUtil.getRoomNum();
        String msg = "uno02 playcard " + username + " " + roomNum + " " + cardPanel.getNumber() + "\r\n";
        try {
            OnlineUtil.sendMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: play card request exception");
        }
    }

    /**
     * 是否不是当前用户的轮次
     *
     * @return 不是当前用户的轮次，返回 true；是当前用户的轮次，返回 false
     */
    private static boolean isNotMyTurn() {
        try {
            if (gameTable.getPlayerByUsername(OnlineUtil.getUsername()).isMyTurn()) {
                return false;
            }
            GameFrame.getGamePanel().getTablePanel().getInfoPanel().setErrorOnPanel("还没轮到您");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据游戏规则判断可否打出这张牌
     *
     * @param newCard 卡牌面板
     * @return 不可打出，返回 true；可打出，返回 false
     */
    private boolean cannotPlayThisCard(CardPanel newCard) {
        boolean ret;
        CardPanel topCard = GameFrame.getGamePanel().getTablePanel().getTopCard();

        // 颜色或值匹配
        if (topCard.getColor().equals(newCard.getColor()) || topCard.getValue().equals(newCard.getValue())) {
            ret = false;
        }
        // 万能牌颜色匹配
        else if (topCard.getType() == CardPanel.WILD) {
            ret = !topCard.getWildChosenColor().equals(newCard.getColor()); // 注意返回否定形式
        } else {
            ret = newCard.getType() != CardPanel.WILD;
        }
        if (ret) { // 提示无效操作
            GameFrame.getGamePanel().getTablePanel().getInfoPanel().setErrorOnPanel("无效操作");
        }
        return ret;
    }

}
