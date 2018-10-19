package Service;

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

    public void getGameTablesData() {
        try {
            String msg = "uno02 hall\r\n";
            OnlineUtil.sendMsg(msg); // 请求游戏大厅数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: get game tables data exception");
        }
    }


    public void gameStart(int remainCardNum, UNOCard firstCard, List<Player> playerList) {
        // 构造模型层 GameTable
        gameTable = new GameTable(GameTable.ONLINE, remainCardNum, firstCard, playerList);

        // 视图通过模型渲染
        GameFrame.setGamePanel(new GamePanel(gameTable));
        GameFrame.main(new String[10]); // 打开游戏窗口
    }


    public void drawCardRequest() { // uno02 drawcard username roomnum
        String username = OnlineUtil.getUsername();
        // 判断是否允许抽牌
        if (!gameTable.getPlayerByUsername(username).isMyTurn()) { // “还没轮到您”
            GameFrame.getGamePanel().getTablePanel().getInfoPanel().setError("还没轮到您");
            return;
        }
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

}
