package tests;

import model.MessageTask;
import decorator.PrinterTaskRunner;
import decorator.StrategyTaskRunner;
import Enum.Strategy;

public class PrinterTaskRunnerTest
{
    public static void printerTaskRunnerTest(Strategy strategy, MessageTask[] messageTasks)
    {
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);
        printerTaskRunner.addTask(messageTasks[0]);
        printerTaskRunner.addTask(messageTasks[1]);
        printerTaskRunner.addTask(messageTasks[2]);
        printerTaskRunner.executeAll();
    }
}
