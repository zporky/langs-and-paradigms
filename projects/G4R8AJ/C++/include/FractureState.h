#include "AbstractState.h"

#ifndef FRACTURESTATE_H
#define FRACTURESTATE_H


class FractureState : public AbstractState
{
    public:
        FractureState();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // FRACTURESTATE_H
