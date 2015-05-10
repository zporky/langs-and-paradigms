#include "state-machine.h"
#include <iostream>

StateMachine::StateMachine() {
	myNumber.clear();
	myState = FloatEnums::S0;
}

StateMachine::~StateMachine() {

}

void StateMachine::init(std::string startSymbol) {
	myNumber.clear();
	if (startSymbol == "+" || startSymbol == "-") {
		myState = FloatEnums::S0;
	} else {
		myState = FloatEnums::S1;
	}
}

std::string StateMachine::getState() { return myState; }

void StateMachine::setState(std::string state) { myState = state; }

std::string StateMachine::getNumber() { return myNumber; }

void StateMachine::setNumber(std::string number) { myNumber = number; }
void StateMachine::appendNumber(std::string number) { myNumber += number; }

std::string StateMachine::processInput(std::string symbol) {
	if (symbol == "1" || symbol == "2" || symbol == "3"
	    || symbol == "4" || symbol == "5" || symbol == "6"
	    || symbol == "7" || symbol == "8" || symbol == "9") {
		return FloatEnums::decimal;
	} else if (symbol == "0") {
		return FloatEnums::zero;
	} else if (symbol == ".") {
		return FloatEnums::point;
	} else if (symbol == "+" || symbol == "-") {
		return FloatEnums::sign;
	} else {
		//unkown symbol...
		return FloatEnums::unknown;
	}
}
int StateMachine::nextState(std::string event, std::string symbol) {
	if (getState() == FloatEnums::S0 && event == FloatEnums::sign) {
		appendNumber(symbol);
		setState(FloatEnums::S1);
		return 0;
	} else if (getState() == FloatEnums::S1 && event == FloatEnums::point) {
		appendNumber(symbol);
		setState(FloatEnums::I);
		return 0;
	} else if (getState() == FloatEnums::S1 && event == FloatEnums::zero) {
		appendNumber(symbol);
		setState(FloatEnums::T2);
		return 0;
	} else if (getState() == FloatEnums::S1 && event == FloatEnums::decimal) {
		appendNumber(symbol);
		setState(FloatEnums::T4);
		return 0;
	} else if (getState() == FloatEnums::T4 && event == FloatEnums::point) {
		appendNumber(symbol);
		setState(FloatEnums::T5);
		return 0;
	} else if (getState() == FloatEnums::T2 && event == FloatEnums::point) {
		appendNumber(symbol);
		setState(FloatEnums::T3);
		return 0;
	} else if (getState() == FloatEnums::I && event == FloatEnums::decimal) {
		appendNumber(symbol);
		setState(FloatEnums::T1);
		return 0;
	} else if ((getState() == FloatEnums::T1 
		         || getState() == FloatEnums::T3 
		         || getState() == FloatEnums::T5
		         || getState() == FloatEnums::T4)
	           && event == FloatEnums::decimal) {
		appendNumber(symbol);
		return 0;
	}
	else {
		//invalid state
		std::cerr<<"Invalid state/event combination."<<std::endl;
		return -1;
	}
}