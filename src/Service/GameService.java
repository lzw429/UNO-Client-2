package Service;

import Model.Player;
import Model.UNOCard;

import java.util.List;

public interface GameService {
    /**
     * 请求游戏大厅数据
     *
     * @return 用于 GUI 显示的大厅数据
     */
    void getGameTablesData();

    /**
     * 对局初始化，修改视图层
     */
    void gameStart(int remainCardNum, UNOCard firstCard, List<Player> playerList);
}
