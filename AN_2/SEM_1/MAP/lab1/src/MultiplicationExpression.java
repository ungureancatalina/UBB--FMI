import java.util.ArrayList;

public class MultiplicationExpression extends ComplexExpression
{
    public MultiplicationExpression(Operation op, ArrayList<NumarComplex> args)
    {
        super(op, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2) {
        return nr1.inmultire(nr2);
    }
}
