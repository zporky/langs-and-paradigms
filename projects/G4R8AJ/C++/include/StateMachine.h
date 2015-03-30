#include "AbstractState.h"
#include <string>

#ifndef STATEMACHINE_H
#define STATEMACHINE_H


class StateMachine
{
    public:
        StateMachine();
        virtual ~StateMachine();
        /*
            Running the stateMachine
        */
        bool run(std::string str);
        std::string getVal(){ return this->value; };
    protected:
    private:
        AbstractState* startState;
        AbstractState* currentState;
        std::string value;
};


#endif // STATEMACHINE_H
