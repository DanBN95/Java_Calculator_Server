package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server
 * @params server: It's a ServerSocket
 * @params calculatorHandlers it is an ArrayList the keep tracks the different clients
 * @params timer for automatic messages
 */
public class CalculatorServer {

    volatile boolean stop;
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    ServerSocket server;
    ArrayList<CalculatorHandler> calculatorHandlers = new ArrayList<>();
    private Timer timer;

    public CalculatorServer() {
        stop = false;
    }

    /**
     *
     * @param port startServer gets a port number for the server and run the server in a new thread.
     *             As long 'stop' == false, the server keeps accept new client.
     *             It does that by managing thread pool, and let threads run CalculatorHandler communication protocol.
     */
    private void startServer(int port) {

        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            System.out.println("Server is connected");

            while (!stop) {
                //server.setSoTimeout(1000 * 10);  // avoid long blocking call while waiting for a client

                Socket aClient = server.accept(); // Blocking call
                System.out.println("New client connected " + aClient.getInetAddress().getHostAddress());

                CalculatorHandler ch = new CalculatorHandler();
                calculatorHandlers.add(ch);

                executorService.execute(() -> {
                    try {
                        ch.communication(aClient.getInputStream(), aClient.getOutputStream(), "exit");
                        aClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     *
     * @param port port number we get from Main Function to run the server.
     *             The reason the server runs in bg thread it because otherwise
     *             it would be stuck on running with no option to abort server's activity from an outside function.
     *             timer runs new thread that every minute sends messages to all clients.
     */
    public void start(int port) {
        new Thread(() -> startServer(port)).start();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (calculatorHandlers.size() > 0) {
                    for (CalculatorHandler ch : calculatorHandlers) {
                        ch.socketIO.write("**** Server Is ALIVE! ***");
                    }
                }
            }
        }, 0, 60 * 1000);
    }

    /**
     * @params stop running server
     */
    public void stop() {
        if (timer != null)
            timer.cancel();
        stop = true;
    }

}
