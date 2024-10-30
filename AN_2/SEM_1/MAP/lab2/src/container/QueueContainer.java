package container;

import model.Task;

public class QueueContainer extends AbstractContainer
{
    public QueueContainer()
    {
        taskuri=new Task[10];
        size=0;
    }

    @Override
    public Task remove()
    {
        if(!isEmpty())
        {
            Task task=taskuri[0];
            for(int i=0;i<size-1;i++)
                taskuri[i]=taskuri[i+1];
            taskuri[--size]=null;
            return task;
        }
        return null;
    }
}
