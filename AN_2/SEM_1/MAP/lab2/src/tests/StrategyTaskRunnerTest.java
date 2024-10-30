package tests;

import model.MessageTask;
import decorator.StrategyTaskRunner;
import decorator.TaskRunner;
import Enum.Strategy;

public class StrategyTaskRunnerTest
{
    public static void strategyTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
    {
        TaskRunner taskRunner = new StrategyTaskRunner(strategy);
        taskRunner.addTask(messageTasks[0]);
        taskRunner.addTask(messageTasks[1]);
        taskRunner.addTask(messageTasks[2]);
        taskRunner.executeAll();
    }
}