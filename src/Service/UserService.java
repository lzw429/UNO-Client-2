package Service;

public interface UserService {
    /**
     * 用户登录 uno 01 username
     *
     * @param username 玩家昵称
     * @return 登录结果
     */
    boolean login(String username);
}
