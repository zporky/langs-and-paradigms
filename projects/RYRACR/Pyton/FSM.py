import sys

########
# Defining custom exception
########
class InvalidTransationException(Exception):
    pass

########
# Defining states
########
states = {}
states['START'] = False;
states['S_'] = False;
states['I0'] = False;
states['T1'] = True;
states['T2'] = True;
states['T3'] = True;
states['T4'] = True;
states['T5'] = True;
states['T6'] = True;

########
# Defining transitions
########
transitions = {}
transitions['s'] = ['+', '-']
transitions['p'] = ['.']
transitions['z'] = ['0']
transitions['d'] = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

def getTransation(char):
    for x in transitions:
        if char in transitions[x]:
            return x
    raise InvalidTransationException("Input is not in alphabet.")

########
# Defining production rules
########

productionRules = {}
productionRules['START', 'p'] = 'I0'
productionRules['START', 'z'] = 'T2'
productionRules['START', 'd'] = 'T4'
productionRules['START', 's'] = 'S_'
 
productionRules['S_', 'p'] = 'I0'
productionRules['S_', 'z'] = 'T2'
productionRules['S_', 'd'] = 'T4'

productionRules['I0', 'd'] = 'T1'
productionRules['T2', 'p'] = 'T3'
productionRules['T4', 'p'] = 'T5'
productionRules['T4', 'd'] = 'T4'

productionRules['T1', 'd'] = 'T1'
productionRules['T3', 'd'] = 'T3'
productionRules['T5', 'd'] = 'T5'

def getNextState(currentState, transation):
    try:
        return productionRules[currentState, transation]
    except KeyError:
        raise InvalidTransationException(currentState + ' state does not accept transition ' + transation)

########
# validateInput
########
def validateInput(line):
    currentState = 'START';
    for c in line:
        transation = getTransation(c);
        currentState = getNextState(currentState, transation)
    if not states[currentState]: #if not in ending state 
        raise InvalidTransationException('Premature end of line')

########
# processLine
########
def processLine(line):
    'Gives descriptive information about the input'
    try:
        validateInput(line)
        print "OK " + line
    except InvalidTransationException:
        print "FAIL " + line

########
# The "Main" "method"
########
lines = [line.strip() for line in open(sys.argv[1])]
for line in lines:
    processLine(line)
    
