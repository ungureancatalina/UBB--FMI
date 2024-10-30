import java.util.ArrayList;

public class AdditionExpression extends ComplexExpression
{
    public AdditionExpression(Operation op, ArrayList<NumarComplex> args)
    {
        super(op, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2) {
        return nr1.adunare(nr2);
    }
}
