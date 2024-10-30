import java.util.ArrayList;
import java.util.regex.Pattern;

public class Parser {
    public boolean eValid(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                if (!eValidNr(args[i]))
                    return false;
            } else if (!eValidOperator(args[i]))
                return false;
        }
        return true;
    }

    public boolean eValidNr(String nr) {
        String regex = "^([-+]?[0-9]+(?:\\.[0-9]+)?)?([+-]?[0-9]*(?:\\.[0-9]+)?\\*?i)?$";
        return nr.matches(regex);
    }

    public boolean eValidOperator(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/");
    }

    public NumarComplex parserNrComplex(String nr) {
        String[] split;
        double re = 0, im = 0;

        String regexPlus = "^([-]?[0-9]+(?:\\.[0-9]+)?)?([+][0-9]+(?:\\.[0-9]+)?)\\*i$";  // a+b*i
        String regexMinus = "^([-]?[0-9]+(?:\\.[0-9]+)?)?([-][0-9]+(?:\\.[0-9]+)?)\\*i$"; // a-b*i

        // a+b*i
        if (Pattern.matches(regexPlus, nr)) {
            split = nr.split("\\+");
            if (split.length == 2) {
                re = Double.parseDouble(split[0].trim());
                im = Double.parseDouble(split[1].replace("*i", "").trim());
            } else {
                im = Double.parseDouble(split[0].replace("*i", "").trim());
            }
        }
        // a-b*i
        else if (Pattern.matches(regexMinus, nr)) {
            split = nr.split("(?<=\\d)(?=-)");
            if (split.length == 2) {
                re = Double.parseDouble(split[0].trim());
                im = Double.parseDouble(split[1].replace("*i", "").trim());
            } else {
                im = Double.parseDouble(split[0].replace("*i", "").trim());
            }
        }
        // b*i sau -b*i
        else if (nr.matches("^([-+]?[0-9]+(?:\\.[0-9]+)?)\\*i$")) {
            im = Double.parseDouble(nr.replace("*i", "").trim());
        }
        // a
        else {
            re = Double.parseDouble(nr.trim());
        }

        return new NumarComplex(re, im);
    }

    private Operation parseOperation(String op) {
        switch (op) {
            case "+":
                return Operation.ADDITION;
            case "-":
                return Operation.SUBTRACTION;
            case "*":
                return Operation.MULTIPLICATION;
            case "/":
                return Operation.DIVISION;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    public NumarComplex calculateExpression(String[] args) {
        ArrayList<NumarComplex> numere = new ArrayList<>();
        Operation op = parseOperation(args[1]);

        for (int i = 0; i < args.length; i += 2) {
            numere.add(parserNrComplex(args[i]));
        }

        ExpressionFactory factory = ExpressionFactory.getInstance();
        return factory.createExpression(op, numere).execute();
    }
}
