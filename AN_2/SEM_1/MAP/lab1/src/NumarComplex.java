public class NumarComplex
{
    private double real;
    private double imag;

    public NumarComplex(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }

    public NumarComplex adunare(NumarComplex nr)
    {
        NumarComplex num=new NumarComplex(this.real+nr.real, this.imag+nr.imag);
        return num;
    }

    public NumarComplex scadere(NumarComplex nr)
    {
        NumarComplex num=new NumarComplex(this.real-nr.real, this.imag-nr.imag);
        return num;
    }

    public NumarComplex inmultire(NumarComplex nr)
    {
        double real_nou= this.real*nr.real-this.imag*nr.imag;
        double imag_nou= this.real*nr.imag+this.imag*nr.imag;
        NumarComplex num=new NumarComplex(real_nou,imag_nou);
        return num;
    }

    public NumarComplex impartire(NumarComplex nr)
    {
        double real_nou= (this.real*nr.real+this.imag*nr.imag)/(nr.real*nr.real+nr.imag*nr.imag);
        double imag_nou=(nr.real*this.imag-this.real*nr.real)/(nr.real*nr.real+nr.imag*nr.imag);
        NumarComplex num=new NumarComplex(real_nou,imag_nou);
        return num;
    }

    public NumarComplex conjugat(NumarComplex nr)
    {
        NumarComplex num=new NumarComplex(nr.real,nr.imag*(-1));
        return num;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    @Override
    public String toString()
    {
        if(this.imag>0)
            return this.real+ " + " + this.imag + "*i";
        else if(this.imag==0)
            return this.real+ " ";
        else
            return this.real + " " + this.imag + "*i";
    }
}
