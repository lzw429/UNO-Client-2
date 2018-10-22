package Util;

import java.util.stream.IntStream;

public class ListeningThread extends Thread {
    public void run() {
        while (!OnlineUtil.connectServer()) {
            try {
                System.out.println("OnlineClient: The connection failed and will be retried after 5 seconds");
                sleep(5000); // 5 秒后重试连接
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        StringBuilder builder;
        char chars[] = new char[GameConstants.BUFSIZ];
        System.out.println("[" + TimeUtil.getTimeInMillis() + "] ListeningThread has started");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                OnlineUtil.readyToReceive = true;
                IntStream.range(0, GameConstants.BUFSIZ).forEach(i -> chars[i] = '\0');
                int len = OnlineUtil.reader.read(chars);
                if (len < 0)
                    continue;
                builder = new StringBuilder();
                builder.append(new String(chars, 0, len));
                String msg = builder.toString();
                String[] msgSplit = msg.split("\f");
                for (String oneMsg : msgSplit) {
                    OnlineUtil.getMessageList().put(oneMsg); // 阻塞队列
                    System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: Receive from server, len = " + oneMsg.length() + ": " + oneMsg);
                }
            } catch (Exception e) {
                System.out.println("[" + TimeUtil.getTimeInMillis() + "] OnlineClient: receive message exception");
                return;
            }
        }
        System.out.println("[" + TimeUtil.getTimeInMillis() + "] ListeningThread: thread interrupt");
    }
}
