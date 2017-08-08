package fmyl.demo2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fuzq on 17/8/8.
 */
public class ServerPrxoy {
    public static void main(String args[]) throws Exception {
        ServerSocket server = new ServerSocket(8002);
        while (true) {
            Socket socket = server.accept();
            ActionSocket ap = new ActionSocket(socket);
            ap.start();
        }
    }
}
