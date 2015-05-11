#include<iostream>
using namespace std;
#include<string>
#include "Machine.h"
#include <iomanip>
#include<math.h>

int main() {
    cout << showpoint << fixed;

	do {
		string s;
		getline(cin, s);

		if (s != "" || !cin.eof())
		{
			Machine machine;

			if (machine.Check(s)) {
                    double value = machine.GetValue();

                    int digitCount = machine.GetDigitCount();
                    if (digitCount == 0) digitCount=1;
                    cout << "OK " << setprecision(digitCount) << value;
			}
			else {
				cout << "FAIL";
			}
			cout << endl;
		}
	} while (!cin.eof());

	return 0;
}
