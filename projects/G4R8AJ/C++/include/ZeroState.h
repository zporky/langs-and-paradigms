#include "AbstractState.h"
#include "FractureState.h"

#ifndef ZEROSTATE_H
#define ZEROSTATE_H


class ZeroState : public AbstractState
{
    public:
        ZeroState();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // ZEROSTATE_H
