#include "AbstractState.h"
#include "IntegralState.h"
#include "PointState.h"
#include "ZeroState.h"
#include <string>

#ifndef UNSIGNEDSTART_H
#define UNSIGNEDSTART_H


class UnsignedStart : public AbstractState
{
    public:
        UnsignedStart();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // UNSIGNEDSTART_H
