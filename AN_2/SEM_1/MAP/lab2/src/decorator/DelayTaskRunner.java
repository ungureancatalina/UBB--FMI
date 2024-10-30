package decorator;

public class DelayTaskRunner extends AbstractTaskRunner
{
    public DelayTaskRunner(TaskRunner taskRunner)
    {
        super(taskRunner);
    }

    @Override
    public void executeOneTask()
    {
        super.executeOneTask();
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
