package server;

public class Calculator implements CalculationOperations {

    /**
     * String equation we get from the client
     */
    public String userInputString;

    public Calculator() {}

    public void setUserInputString(String userInputString) {
        this.userInputString = userInputString;
    }

    @Override
    public double add(double num1, double num2) {
        return num1 + num2;
    }

    @Override
    public double sub(double num1, double num2) {
        return num1 - num2;
    }

    @Override
    public double mul(double num1, double num2) {
        return num1 * num2;
    }

    @Override
    public double div(double num1, double num2) {
        return num1 / num2;
    }

    /**
     *
     * @param input is a String array we get from split the userInputString
     * @return true if the equation is in the right format
     */
    public boolean testEquation(String [] input) {
        System.out.println("test equation");
        for (String s : input) {
            System.out.println(s);
        }

        if (input.length != 3)  return false;

        if (!(input[1].equals("+") || input[1].equals("-") || input[1].equals("*") || input[1].equals("/")))
            return false;

        try {
            Double.parseDouble(input[0]);
            Double.parseDouble(input[2]);

        } catch (NullPointerException err) {
            return false;
        } catch (NumberFormatException err) {
            return false;
        }

        return true;

    }
}
