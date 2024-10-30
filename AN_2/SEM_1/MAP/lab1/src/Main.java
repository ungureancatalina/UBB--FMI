import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Numarul de parametrii(operand+operator+operand...): ");
        int n=scanner.nextInt();
        scanner.nextLine();
        args=new String[n];
        for(int i=0;i<n;i++)
        {
            args[i]=scanner.nextLine();
        }
        Parser parser=new Parser();

        if(parser.eValid(args)==true && args.length%2!=0)
        {
            NumarComplex rez=parser.calculateExpression(args);
            System.out.println("Rezultat: "+ rez.toString());
        }
        else
        {
            System.out.println("Nu e valida expresia");
        }
        scanner.close();
    }
}