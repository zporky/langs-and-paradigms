#include "PointState.h"

PointState::PointState()
{
    this->isTerminal = false;
}

AbstractState::State PointState::next(std::string str,std::string curVal)
{
    State ret;
    if( isdigit(str[0]) )
    {
        FractureState nextState;
        ret.nextState = new FractureState();
        ret.currentVal = curVal + str[0];
        ret.currentRemaining = str.erase(0,1);
    }
    else
    {
        ret.nextState = NULL;
    }
    return ret;
}

