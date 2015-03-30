#include "AbstractState.h"
#include "FractureState.h"

#ifndef POINTSTATE_H
#define POINTSTATE_H


class PointState : public AbstractState
{
    public:
        PointState();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // POINTSTATE_H
