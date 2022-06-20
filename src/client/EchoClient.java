package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 32000;

    /**
     *
     * Client opens communication with the server.
     * The client send Equation to the server and gets the result.
     * Sending "exit" message to the server will return a "bye" message from the server, and end the communication.
     * The client runs a thread in the bg inorder to pull messages from the server, that are unrelated to engagement.
     */
    public static void main(String [] args) {

        try {
            Socket socket = new Socket(HOST, PORT);

            ServerConnection serverConn = new ServerConnection(socket);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(System.in);

            new Thread(serverConn).start();

            String line = null;

            while (!"bye".equalsIgnoreCase(line)) {
                line = in.nextLine();
                out.println(line);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
