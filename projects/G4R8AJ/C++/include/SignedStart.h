#include "AbstractState.h"
#include "UnsignedStart.h"
#include <string>

#ifndef SIGNEDSTART_H
#define SIGNEDSTART_H


class SignedStart : public AbstractState
{
    public:
        SignedStart();
        State next(std::string str,std::string curVal);
    protected:
    private:
};

#endif // SIGNEDSTART_H
