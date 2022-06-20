package helper;

import server.DefaultIO;

import java.io.*;
import java.util.Scanner;

public class SocketIO implements DefaultIO {

    Scanner in;
    PrintWriter out;

    public SocketIO(InputStream inputStream, OutputStream outputStream) {
        in = new Scanner(new InputStreamReader(inputStream));
        out = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    public void write(String text) {
        out.println(text);
        out.flush();
    }

    @Override
    public String read() {
        return in.nextLine();
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}
