package Service;

import Util.GameConstants;
import Util.OnlineUtil;

import java.util.concurrent.TimeUnit;

public class GameServiceImpl implements GameService {
    @Override
    public String[][] getGameTablesData() {
        int i = 0;
        String[][] data = new String[GameConstants.roomNum][3];
        try {
            OnlineUtil.connectServer();
            OnlineUtil.sendMsg("uno02 hall\r\n"); // 请求游戏大厅数据
            TimeUnit.MILLISECONDS.sleep(GameConstants.timeLimit); // 等待
            String msg = OnlineUtil.receiveMsg();
            if (msg == null) {
                // 消息超时
                System.out.println("GameService: message timeout");
                return null;
            } else if (msg.equals("")) {
                System.out.println("GameService: message is empty string");
                return null;
            }
            String[] msg_split = msg.split("\r\n\r\n");
            if (!msg_split[0].equals("uno02 hall")) {
                // 该消息不是大厅数据
                System.out.println("GameService: receive the wrong message");
                return null;
            } else {
                // 该消息是大厅数据
                if (msg_split.length == 1) {
                    // 大厅数据为空
                    return data;
                } else {
                    // 大厅数据非空
                    String[] content = msg_split[1].split("\r\n");
                    for (String line : content) {
                        String[] line_split = line.split(",");
                        deCodeContent(line_split);
                        System.arraycopy(line_split, 0, data[i], 0, 3);
                        i++;
                    }
                    return data;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("GameService: get game tables data exception");
            return null;
        }
    }

    private void deCodeContent(String[] line_split) {
         
    }
}
