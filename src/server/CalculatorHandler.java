package server;

import helper.SocketIO;

import java.io.*;

/**
 * Inorder to separate between the Server mechanism and the communication protocol
 */
public class CalculatorHandler implements ClientHandler {

    Calculator calculator;
    SocketIO socketIO;

    /**
     *
     * @param inFromClient stream we get from the client
     * @param outToClient stream we send to the client
     * @param exitStr exit word to stop the communication between the client and the server
     */
    @Override
    public void communication(InputStream inFromClient, OutputStream outToClient, String exitStr) {
        this.calculator = new Calculator();
        socketIO = new SocketIO(inFromClient, outToClient);
        String line;
        socketIO.write("Welcome to Calculator Server");
        socketIO.write("Add/Subtract/Multiply/Divide and press Enter. ex: 100-73");
        while (!(line = socketIO.read()).equals(exitStr)) {
            System.out.printf("Send from the client: %s\n", line);
            System.out.println(line.equals(exitStr));
            calculator.setUserInputString(line);

            String [] equation = line.split("(?<=[-+*/])|(?=[-+*/])");
            if(!calculator.testEquation(equation)) {
                socketIO.write("Something went wrong with your typo.\n");
                socketIO.write("Try to add/subtract/multiply/divide in this format. ex: 10+73 , and press Enter");
                continue;
            }


            String sign = equation[1];
            double res = Double.MAX_VALUE;

            switch (sign) {
                case "+":
                    res = (calculator.add(Double.parseDouble(equation[0]), Double.parseDouble(equation[2])));
                    break;
                case "-":
                    res = (calculator.sub(Double.parseDouble(equation[0]), Double.parseDouble(equation[2])));
                    break;
                case "*":
                    res = (calculator.mul(Double.parseDouble(equation[0]), Double.parseDouble(equation[2])));
                    break;
                case "/":
                    if ((Double.parseDouble( equation[2])) == 0) {
                        socketIO.write("Error dividing by zero");
                        break;
                    }
                    res = (calculator.div(Double.parseDouble(equation[0]), Double.parseDouble(equation[2])));
                    break;
            }

            if (res != Double.MAX_VALUE)
                socketIO.write("= " + res);

        }
        System.out.println("Close socket");
        socketIO.write("bye");
        socketIO.close();

    }
}
