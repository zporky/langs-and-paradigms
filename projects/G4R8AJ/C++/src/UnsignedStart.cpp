#include "UnsignedStart.h"

UnsignedStart::UnsignedStart()
{
    this->isTerminal = false;
}

AbstractState::State UnsignedStart::next(std::string str,std::string curVal)
{

    State ret;
    ret.currentVal = curVal;
    if(str[0] ==  '.')
    {
        PointState nextState;
        ret.nextState = new PointState();
        ret.currentVal = curVal + "0.";
        ret.currentRemaining = str.erase(0,1);
    }
    else if( str[0] == '0' )
    {
        ret.nextState = new ZeroState();
        ret.currentVal = curVal + "0";
        ret.currentRemaining = str.erase(0,1);
    }
    else if( isdigit(str[0]) )
    {
        ret.nextState = new IntegralState();
        ret.currentVal = curVal + str[0];
        ret.currentRemaining = str.erase(0,1);
    }
    else
    {
        ret.nextState = NULL;
    }
    return ret;
}
