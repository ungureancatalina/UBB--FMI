#pragma once
#include <vector>
#include <algorithm>

class Observer
{
public:
    virtual void update() = 0;
};

class Observable
{
private:
    std::vector<Observer*> observers;
public:
    void adauga_obs(Observer* obs)
    {
        observers.push_back(obs);
    }

    void sterge_obs(Observer* obs)
    {
        observers.erase(std::remove(observers.begin(), observers.end(), obs), observers.end());
    }

    void notify()
    {
        for (auto obs : observers)
        {
            obs->update();
        }
    }
};
