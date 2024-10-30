import java.util.ArrayList;

public class DivisionExpression extends ComplexExpression
{
    public DivisionExpression(Operation op, ArrayList<NumarComplex> args)
    {
        super(op, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2) {
        return nr1.impartire(nr2);
    }
}
