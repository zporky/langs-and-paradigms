#ifndef _STATE_MACHINE_H_
#define _STATE_MACHINE_H_

#include <string>

namespace FloatEnums {
	//valid states
	const std::string S0 = "S0"; //start symbol of signed float
	const std::string S1 = "S1"; //start symbol of unsigned float
	const std::string I = "I"; //non-accepted states (non-terminals)
	const std::string T1 = "T1"; //.123
	const std::string T2 = "T2"; //0
	const std::string T3 = "T3"; //0.123
	const std::string T4 = "T4"; //123
	const std::string T5 = "T5"; //123.123
	
	//valid events
	//z (0), d (1..9), p (.), s (+, -)
	const std::string zero = "z";
	const std::string decimal = "d";
	const std::string point = "p";
	const std::string sign = "s";
	const std::string unknown = "u";
}

class StateMachine
{
public:	
	StateMachine();
	virtual ~StateMachine();

	virtual void init(std::string startSymbol);

	virtual std::string getState();
	virtual void setState(std::string state);

	virtual std::string getNumber();
	virtual void setNumber(std::string number);
	virtual void appendNumber(std::string number);

	virtual std::string processInput(std::string symbol);
	virtual int nextState(std::string event, std::string symbol);

protected:
	std::string myNumber;
	std::string myState;

private:

};

#endif // _STATE_MACHINE_H_
