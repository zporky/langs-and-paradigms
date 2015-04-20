#include "AbstractState.h"

#ifndef EXPONENTIALFRACTURESTATE_H
#define EXPONENTIALFRACTURESTATE_H


class ExponentialFractureState : public AbstractState
{
    public:
        ExponentialFractureState();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // EXPONENTIALFRACTURESTATE_H
