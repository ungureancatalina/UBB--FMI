package container;

import model.Task;

public abstract class AbstractContainer implements Container
{
    protected Task[] taskuri;
    protected int size;

    public void add(Task task)
    {
        if(taskuri.length == size)
        {
            Task[] t= new Task[taskuri.length*2];
            System.arraycopy(taskuri, 0, t, 0, taskuri.length);
            taskuri = t;
        }
        taskuri[size++] = task;
        size++;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }
}
