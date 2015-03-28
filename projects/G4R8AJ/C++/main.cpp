#include <iostream>
#include "StateMachine.h"

using namespace std;

int main()
{
    string line;
    while( getline(cin,line) )
    {
        StateMachine machine;
        bool run = machine.run(line);
        if(run)
            cout << "OK " + machine.getVal() << endl;
        else
            cout << "FAIL" << endl;
    }
    return 0;
}
