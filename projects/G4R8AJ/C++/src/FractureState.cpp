#include "FractureState.h"

FractureState::FractureState()
{
    this->isTerminal = true;
}

AbstractState::State FractureState::next(std::string str,std::string curVal)
{
    State ret;
    ret.currentRemaining = str;
    ret.nextState = NULL;
    if(isdigit(str[0]))
    {
        ret.currentVal = curVal + str[0];
        ret.currentRemaining = str.erase(0,1);
        ret.nextState = this;
    }
    else
    {
        ret.nextState = NULL;
    }
    return ret;
}
