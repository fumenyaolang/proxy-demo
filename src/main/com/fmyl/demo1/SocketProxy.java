package fmyl.demo1;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用线程池分发任务
 * Created by fuzq on 17/8/8.
 */
public class SocketProxy {

    static final int workerNumber = 4;//线程池保留数量，服务器为8核cpu，合适的数量应该小于8

    static final int maxPoolSize = 256;//最大线程数量，即最大并发量

    static final int maxWorkerInQueue = 2500;// 最大工作队列数量

    static final int waitTime = 10;// 超时等待时间

    static final int listenPort = 8002;

    static final ThreadPoolExecutor tpe = new ThreadPoolExecutor(workerNumber,
            maxPoolSize, waitTime, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(maxWorkerInQueue));

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ServerSocket serverSocket = new ServerSocket(listenPort);

        System.out.println("Proxy Server Start At" + sdf.format(new Date()));
        System.out.println("listening port:" + listenPort + "……");
        System.out.println();
        System.out.println();

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
                //加入任务列表，等待处理
                tpe.execute(new ProxyTask(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
