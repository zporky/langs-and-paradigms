#include <iostream>
#include "state-machine.h"

//using namespace std;
using namespace FloatEnums;

int main()
{
	int p;
	
	StateMachine m1;
	std::string input;
	std::string event;
	do {
		input = "";
		std::cin>>input;
		m1.init(input.substr(0,1));
		for (int i=0; i<input.length(); i++) { 
			//std::cout<<"Input symbol: "<<input.substr(i,1)<<std::endl;
			event = m1.processInput(input.substr(i,1));
			if (event == FloatEnums::unknown) {m1.setState(FloatEnums::I);break;}
			p=m1.nextState(event, input.substr(i,1));
			if (p<0) {m1.setState(FloatEnums::I);break;/*skip the rest of the input*/}
			//std::cout<<"Current state: "<<m1.getState()<<std::endl;
		}
		if (m1.getState().substr(0,1)=="T") {
			std::cout<<"OK "<<m1.getNumber()<<std::endl;
		} else { std::cerr<<"FAIL"<<std::endl;}
	} while(!std::cin.eof());
	return 0;
}
