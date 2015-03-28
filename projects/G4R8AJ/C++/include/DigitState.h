#include "AbstractState.h"

#ifndef DIGITSTATE_H
#define DIGITSTATE_H


class DigitState : public AbstractState
{
    public:
        DigitState();
        bool next(std::string str);
    protected:
    private:
};

#endif // DIGITSTATE_H
