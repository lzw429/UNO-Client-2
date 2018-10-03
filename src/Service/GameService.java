package Service;

public interface GameService {
    /**
     * 请求游戏大厅数据
     *
     * @return 用于 GUI 显示的大厅数据
     */
    String[][] getGameTablesData();
}
