import java.util.ArrayList;

public class SubtractionExpression extends ComplexExpression
{
    public SubtractionExpression(Operation op, ArrayList<NumarComplex> args)
    {
        super(op, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2) {
        return nr1.scadere(nr2);
    }
}
