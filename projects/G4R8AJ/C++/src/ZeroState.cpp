#include "ZeroState.h"

ZeroState::ZeroState()
{
    this->isTerminal = true;
}

AbstractState::State ZeroState::next(std::string str,std::string curVal)
{
   State ret;
   if(str[0] == '.')
   {
       FractureState nextState;
       ret.nextState = new FractureState();
       ret.currentVal = curVal + ".";
       ret.currentRemaining = str.erase(0,1);
   }
   else
   {
       ret.nextState = NULL;
   }
   return ret;
}
