#include "StateMachine.h"
#include "SignedStart.h"

StateMachine::StateMachine()
{
    this->startState = new SignedStart();
    this->currentState = NULL;
}

StateMachine::~StateMachine()
{
    delete currentState;
}

bool StateMachine::run(std::string str)
{
    /**
        Pre run setting of the state machine
        -Optimistic search
        -basic value
        -processing of the first step.
    */
    bool success = true;
    this->value = str;
    AbstractState::State nextState;
    nextState = this->startState->next(str,"");
    this->currentState = startState;

    /**
        Running the State Machine
        Until there is remaining characters and we have not Failed.
    */
    while(nextState.currentRemaining.length() > 0 && success)
    {
        /**
            if there the string size is not 0 and the next state is null -> Fail State
        */
        if(nextState.nextState != NULL)
        {
            /**
                Collect garbage + do the step
            */
            if(currentState != nextState.nextState)
                delete currentState;
            currentState = nextState.nextState;
            nextState = currentState->next(nextState.currentRemaining,nextState.currentVal);
        }
        else
        {
            success = false;
        }
    }

    /**
        Case Full Integral(No fracture part, adding the .0 to the number)
    */
    if(nextState.currentVal.find('.') == nextState.currentVal.npos && nextState.currentVal != "0")
    {
        nextState.currentVal = nextState.currentVal + ".0";
    }

    /**
        Handling after analysis states
    */
    if(nextState.nextState == NULL)
    {
        success = false;
    }
    if(nextState.nextState != NULL )
    {
        success = nextState.nextState->terminal();
    }
    value = nextState.currentVal;

    return success;
}
