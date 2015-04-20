#include "AbstractState.h"
#include "ExponentialIntegral.h"

#ifndef EXPONENTIALSTATE_H
#define EXPONENTIALSTATE_H


class ExponentialState : public AbstractState
{
    public:
        ExponentialState();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // EXPONENTIALSTATE_H
