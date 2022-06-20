package server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    void communication(InputStream inFromClient, OutputStream outToClient, String exitStr);
}
