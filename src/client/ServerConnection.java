package client;

import helper.SocketIO;
import java.io.IOException;
import java.net.Socket;

/**
 * Pull from server
 */
public class ServerConnection implements Runnable {

    private SocketIO socketIO;

    public ServerConnection(Socket s) {
        try {
            socketIO = new SocketIO(s.getInputStream(), s.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            String serverResponse = "";
            while (!"bye".equalsIgnoreCase(serverResponse)) {
                serverResponse = socketIO.read();
                System.out.println(serverResponse);
            }
        } finally {
            socketIO.close();
        }
    }
}
