#include "Machine.h"

Machine::Machine() {
	Init();
}

void Machine::Init() {
	state = stS;
	value = 0;
	sign = 1;
	wholeWeight = 10;
	partialWeight = 1;
	digitCount = 0;
}

bool Machine::Check(string s) {
	bool res = true;

	for (unsigned int i = 0; i < s.length(); i++) {
		if (!Push(s[i])) {
			res = false;
			break;
		}
	}

	return res && state >= stT1 && state <= stT5;
}

bool Machine::Push(char symbol) {
	bool s = symbol == '+' || symbol == '-';
	bool p = symbol == '.';
	bool z = symbol == '0';
	bool d = symbol >= '1' && symbol <= '9';

	if (symbol == '-') {
		sign = -1;
	}

	if (p) {
		wholeWeight = 1;
		partialWeight = 0.1;
	}

	if (d) {
		value = value*wholeWeight + sign*(symbol - '0')*partialWeight;

		if (wholeWeight == 1) {
			partialWeight *= 0.1;
			digitCount++;
		}
	}

	if (state == stS && !s) {
		state = stS1;
	}

	switch (state)
	{
	case stS:
		state = s || p || z || d ? stS1 : stE;
		break;
	case stS1:
		state = p ? stI0 : z ? stT2 : d ? stT4 : stE;
		break;
	case stI0:
		state = d ? stT1 : stE;
		break;
	case stT1:
		state = d ? stT1 : stE;
		break;
	case stT2:
		state = p ? stT3 : stE;
		break;
	case stT3:
		state = d ? stT3 : stE;
		break;
	case stT4:
		state = d ? stT4 : p ? stT5 : stE;
		break;
	case stT5:
		state = d ? stT5 : stE;
		break;
	default:
		state = stE;
		break;
	}

	return state != stE;
}

double Machine::GetValue() {
    return value;
}

double Machine::GetDigitCount() {
    return digitCount;
}

