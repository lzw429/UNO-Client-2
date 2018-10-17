package Service;

import GUI.GameWindow.CardFrontPanel;
import GUI.GameWindow.GameFrame;
import GUI.GameWindow.GamePanel;
import Model.GameTable;
import Model.Player;
import Model.UNOCard;
import Util.OnlineUtil;

import java.util.List;

public class GameServiceImpl implements GameService {
    private static GameTable gameTable = null;

    @Override
    public void getGameTablesData() {
        try {
            OnlineUtil.sendMsg("uno02 hall\r\n"); // 请求游戏大厅数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: get game tables data exception");
        }
    }

    @Override
    public void gameStart(int remainCardNum, UNOCard firstCard, List<Player> playerList) {
        // 修改视图层
        GameFrame.setGamePanel(new GamePanel(remainCardNum, new CardFrontPanel(firstCard), playerList));
        GameFrame.main(new String[10]); // 打开游戏窗口
    }
}
