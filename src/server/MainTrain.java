package server;

public class MainTrain {

    private static final int PORT = 32000;

    public static void main(String [] args) {

        CalculatorServer server = new CalculatorServer();
        server.start(PORT);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
