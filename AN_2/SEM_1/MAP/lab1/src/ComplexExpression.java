import java.util.ArrayList;

public abstract class ComplexExpression
{
    protected Operation operatie;
    protected ArrayList<NumarComplex> numere;

    public ComplexExpression(Operation op, ArrayList<NumarComplex> args)
    {
        this.operatie = op;
        this.numere = args;
    }

    public NumarComplex execute()
    {
        NumarComplex result = this.numere.get(0);
        for(int i=1; i<this.numere.size(); i++)
        {
            result=this.executeOneOperation(result, this.numere.get(i));
        }
        return result;
    }

    protected abstract NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2);
}
