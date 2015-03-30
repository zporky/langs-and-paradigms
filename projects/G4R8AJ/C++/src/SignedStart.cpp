#include "SignedStart.h"

SignedStart::SignedStart() : AbstractState()
{
    this->isTerminal = false;
}

AbstractState::State SignedStart::next(std::string str,std::string curVal)
{
    UnsignedStart nextState;
    State ret;
    ret.nextState = new UnsignedStart();
    ret.currentVal = curVal;
    if(str.length() == 0)
    {
        ret.nextState = NULL;
    }
    if(str[0] == '+' || str[0] == '-')
    {
        ret.currentRemaining = str.erase(0,1);
    }
    else
    {
        ret.currentRemaining = str;
    }
    return ret;
}

