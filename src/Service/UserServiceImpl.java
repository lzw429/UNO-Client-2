package Service;

import Util.OnlineUtil;
import Util.TimeUtil;

import java.util.concurrent.TimeUnit;

public class UserServiceImpl implements UserService {
    @Override

    public boolean login(String username) {
        try {
            OnlineUtil.connectServer();
            String msg = "uno01 login " + username + "\r\n";
            synchronized (OnlineUtil.messageLock) {
                OnlineUtil.sendMsg(msg);
                OnlineUtil.messageLock.wait();
                if (OnlineUtil.getUsername() == null)
                    return false;
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
