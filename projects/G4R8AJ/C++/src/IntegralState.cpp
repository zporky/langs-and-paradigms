#include "IntegralState.h"

IntegralState::IntegralState()
{
    this->isTerminal = true;
}

AbstractState::State IntegralState::next(std::string str,std::string curVal)
{
    State ret;
    if(str[0] == '.')
    {
        FractureState nextState;
        ret.nextState = new FractureState();
        ret.currentVal = curVal + ".";
        ret.currentRemaining = str.erase(0,1);
    }
    else if( isdigit(str[0]) )
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


