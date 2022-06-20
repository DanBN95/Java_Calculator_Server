package server;

/**
 * Default IO functions
 */
public interface DefaultIO {
    void write(String text);
    String read();
    void close();
}
