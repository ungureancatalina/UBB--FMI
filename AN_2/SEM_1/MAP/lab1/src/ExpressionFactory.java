import java.util.ArrayList;

public class ExpressionFactory
{
    private static final ExpressionFactory instance= new ExpressionFactory();

    private ExpressionFactory() {}

    public static ExpressionFactory getInstance()
    {
        return instance;
    }

    public ComplexExpression createExpression(Operation op, ArrayList<NumarComplex> args)
    {
        if (op == null)
        {
            return null;
        }
        switch(op)
        {
            case ADDITION:
                return new AdditionExpression(op, args);
            case SUBTRACTION:
                 return new SubtractionExpression(op, args);
            case MULTIPLICATION:
                 return new MultiplicationExpression(op, args);
            case DIVISION:
                 return new DivisionExpression(op, args);
            default:
                 throw new IllegalArgumentException("Operatia nu e gasita"+ op);
        }

    }
}
