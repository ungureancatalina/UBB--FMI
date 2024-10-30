package factory;

import container.Container;
import Enum.Strategy;

public interface Factory
{
    Container createContainer(Strategy strategy);
}

