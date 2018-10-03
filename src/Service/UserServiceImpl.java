package Service;

import Util.GameConstants;
import Util.OnlineUtil;

import java.util.concurrent.TimeUnit;

public class UserServiceImpl implements UserService {
    @Override

    public boolean login(String username) {
        try {
            OnlineUtil.connectServer();
            String msg = "uno01 login " + username + "\r\n";
            OnlineUtil.sendMsg(msg);
            TimeUnit.MILLISECONDS.sleep(GameConstants.timeLimit); // 等待
            String receive = OnlineUtil.receiveMsg();
            if (receive == null) {
                System.out.println("UserService: message timeout");
                return false;
            }
            receive = receive.substring(0, receive.length() - 2); // 去除字符串末尾 \r\n
            String[] receive_split = receive.split(" ");
            if (receive_split[1].equals("login")) {
                if (receive_split[3].equals("0")) // 登录失败
                    return false;
                else if (receive_split[3].equals("1")) { // 登录成功
                    return true;
                }
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService: login failed");
            return false;
        }
        return true;
    }
}
