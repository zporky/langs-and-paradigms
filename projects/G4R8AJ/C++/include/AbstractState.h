#include <string>
#include <iostream>

#ifndef ABSTRACTSTATE_H
#define ABSTRACTSTATE_H


class AbstractState
{
    public:

        struct State
        {
            std::string currentRemaining;
            std::string currentVal;
            AbstractState* nextState;
        };

        AbstractState();

        /**
            get the next State and remaining string and current accumulated value!
        */
        virtual State next(std::string str,std::string curVal) = 0;

        /**
            return a boolean showing if the state is terminal
        */
        bool terminal() { return this->isTerminal; }

        virtual ~AbstractState();
    protected:
        bool isTerminal;
    private:
};

#endif // ABSTRACTSTATE_H
