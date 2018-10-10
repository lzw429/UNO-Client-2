package Service;

import GUI.GameWindow.GameFrame;
import Model.GameTable;
import Util.GameConstants;
import Util.OnlineUtil;
import Util.TimeUtil;

import java.util.concurrent.TimeUnit;

public class GameServiceImpl implements GameService {
    private static GameTable gameTable = null;

    public void createGameTable() {
        if (gameTable == null) {
            gameTable = new GameTable(GameTable.ONLINE); // 从服务器获取当前对局信息
        } else {
            System.out.println("GameService: game table already exists");
        }
        GameFrame.main(new String[10]); // 打开游戏窗口
    }

    @Override
    public void getGameTablesData() {
        int i = 0;
        try {
            OnlineUtil.connectServer();
            OnlineUtil.sendMsg("uno02 hall\r\n"); // 请求游戏大厅数据
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: get game tables data exception");
        }
    }
}
