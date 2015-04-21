#include "ExponentialIntegral.h"

ExponentialIntegral::ExponentialIntegral()
{
    this->isTerminal = true;
}

AbstractState::State ExponentialIntegral::next(std::string str,std::string curVal)
{
    State ret;
    if( isdigit(str[0]) )
    {
        ret.nextState = this;
        ret.currentVal = curVal + str[0];
        ret.currentRemaining = str.erase(0,1);
    }
    else
    {
        ret.nextState = NULL;
    }
    return ret;
}
