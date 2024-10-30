package model;

public class QuickSort extends AbstractSorter
{
    @Override
    public void sort(int[] numere) {
        quickSort(numere,0,numere.length-1);
    }

    public void quickSort(int[] numere , int low , int high)
    {
        if(low < high)
        {
            int index=partition(numere,low,high);
            quickSort(numere,low,index-1);
            quickSort(numere,index+1,high);
        }
    }

    public int partition(int[] numere , int low , int high)
    {
        int pivot=numere[high];
        int i=low-1;
        for(int j=low;j<high;j++)
            if(numere[j]<=pivot)
            {
                i++;
                int temp=numere[i];
                numere[i]=numere[j];
                numere[j]=temp;
            }
        int temp=numere[i+1];
        numere[i+1]=numere[high];
        numere[high]=temp;
        return i+1;
    }
}
