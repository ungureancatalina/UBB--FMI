package model;

public class BubbleSort extends AbstractSorter
{
    @Override
    public void sort(int[] numere)
    {
        int nr=numere.length;
        for(int i=0;i<nr-1;i++)
            for(int j=0;j<nr-1;j++)
                if(numere[j]>numere[j+1])
                {
                    int temp=numere[j];
                    numere[j]=numere[j+1];
                    numere[j+1]=temp;
                }
    }
}
