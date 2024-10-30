package model;
import Enum.SortStrategy;
import java.util.Arrays;

public class SortingTask extends Task
{
    private AbstractSorter abstractSorter;
    private int[] numere;
    private SortStrategy sortStrategy;

    public SortingTask(String id_task, String descriere, int[] numere, SortStrategy sortStrategy)
    {
        super(id_task, descriere);
        this.numere = numere;
        this.sortStrategy = sortStrategy;

        switch (sortStrategy)
        {
            case BUBBLESORT -> abstractSorter = new BubbleSort();
            case QUICKSORT -> abstractSorter = new QuickSort();
            default -> {}
        }
    }

    public void execute()
    {
        abstractSorter.sort(numere);
        System.out.println(Arrays.toString(numere));
    }
}
