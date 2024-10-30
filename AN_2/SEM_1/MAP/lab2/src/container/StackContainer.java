package container;

import model.Task;

public class StackContainer extends AbstractContainer
{
    public StackContainer()
    {
        taskuri = new Task[10];
        size = 0;
    }

    @Override
    public Task remove()
    {
        if (!isEmpty())
        {
            Task task = taskuri[--size];
            taskuri[size] = null;
            return task;
        }
        return null;
    }
}