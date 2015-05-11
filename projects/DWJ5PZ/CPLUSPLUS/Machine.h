#ifndef MACHINE_H
#define MACHINE_H

#include<string>
using namespace std;

enum State { stS, stS1, stI0, stT1, stT2, stT3, stT4, stT5, stE };

class Machine {
	State state;
	double value;
	int sign;
	double wholeWeight;
	double partialWeight;
	int digitCount;

	bool Push(char symbol);
public:
	Machine();
	void Init();
	bool Check(string s);
	double GetValue();
	double GetDigitCount();
};

#endif // MACHINE_H
