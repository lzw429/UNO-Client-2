package Service;

import Util.OnlineUtil;

public class UserServiceImpl implements UserService {
    @Override

    public boolean login(String username) {
        try {
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
