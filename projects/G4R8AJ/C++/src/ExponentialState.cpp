#include "ExponentialState.h"

ExponentialState::ExponentialState()
{
    this->isTerminal = false;
}

AbstractState::State ExponentialState::next(std::string str,std::string curVal)
{
    State ret;
    ret.currentRemaining = str;
    ret.nextState = NULL;
    if(str[0] == '+' || str[0] == '-')
    {
        ret.currentVal = curVal + str[0];
        ret.currentRemaining = str.erase(0,1);
        ret.nextState = this;
    }
    else if( isdigit(str[0]) )
    {
        ret.currentVal = curVal;
        ret.currentRemaining = str;
        ret.nextState = new ExponentialIntegral();
    }
    else
    {
        ret.nextState = NULL;
    }
    return ret;
}
