#include "AbstractState.h"

#ifndef EXPONENTIALINTEGRAL_H
#define EXPONENTIALINTEGRAL_H


class ExponentialIntegral : public AbstractState
{
    public:
        ExponentialIntegral();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // EXPONENTIALINTEGRAL_H
