#include "DigitState.h"

DigitState::DigitState()
{
    this->isTerminal = true;
}

bool DigitState::next(std::string str)
{
    bool ret = false;
    if(str.length() == 0)
    {
        ret = true;
    }
    else if( isdigit(str[0]) )
    {
        ret = this->next(str.erase(0,1));
    }
    else
    {
        ret = false;
    }
    return ret;
}
