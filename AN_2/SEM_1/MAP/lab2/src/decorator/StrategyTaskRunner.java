package decorator;

import container.Container;
import model.Task;
import factory.TaskContainerFactory;
import Enum.Strategy;

public class StrategyTaskRunner implements TaskRunner {

    private Container container;

    public StrategyTaskRunner(Strategy strategy)
    {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask()
    {
        Task task = container.remove();
        if (task != null)
            task.execute();
    }

    @Override
    public void executeAll()
    {
        while (hasTask()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t)
    {
        container.add(t);
    }

    @Override
    public boolean hasTask()
    {
        return !container.isEmpty();
    }
}
