
import Data.List
import System.IO (isEOF)

--for float parsing
type Parser = String -> State -> String -> String	-- <parser result before> <origin state> <parsed terminal> -> <parser result after>

--for recognizing
data State = State String Bool
instance Eq State where
	(State str1 b1) == (State str2 b2) = (str1 == str2) && (b1 == b2)

data RHS = RHS Terminals State
type Terminals = [String]
data Rule = Rule State RHSS
type RHSS = [RHS]
data FSM = FSM State String String	-- FSM <current state> <reminaing input> <parsed float>


-- returns (Just y) if arg0 has an y element that is a prefix for arg1; returns Nothing otherwise
hasPrefix :: Terminals -> String -> (Maybe String)
hasPrefix (prefix:xs) input
			| startsWith input prefix = Just prefix
			| otherwise = hasPrefix xs input
hasPrefix [] s = Nothing

--returns the suffix of the input word: the whole input except the first n characters
cutPrefix :: String -> Int -> String
cutPrefix s 0 = s
cutPrefix (x:xs) n = cutPrefix xs (n-1)

-- returns whether arg1 is a prefix of arg0
startsWith :: String -> String -> Bool
startsWith (x:xs) (y:ys) = (x == y) && startsWith xs ys
startsWith xs [] = True
startsWith [] ys = False

-- returns (Just (Tuple3 <the remaining part of arg1> <the result State> <the matched terminal>)) after it was successfully matched with arg0; if the match was not successful, returns Nothing
tryMatchingRHS :: RHS -> String -> (Maybe (String, State, String))
tryMatchingRHS (RHS ts stResult) input = case (hasPrefix ts input) of
					Just prefix -> Just ((cutPrefix input (length prefix)), stResult, prefix)
					Nothing -> Nothing

-- executes tryMatchingRHS on a list of RHS (RHSS); returns (Just (Tuple3 <remaining part of input after matching> <the result state> <the matched terminal>)) for the first matching RHS; returns Nothing if none could be matched
tryMatchingRHSS :: RHSS -> String -> (Maybe (String, State, String))
tryMatchingRHSS (x:xs) input = case (tryMatchingRHS x input) of
					Just t3 -> Just t3 --Just (Tuple3 rem stResult matchedTerminal) -> Just (Tuple3 rem stResult matchedTerminal)
					Nothing -> tryMatchingRHSS xs input
tryMatchingRHSS [] input = Nothing

-- returns Just <the remaining part of arg2>) after it was successfully matched with arg0; if the match was not successful, returns Nothing
tryMatchingRule :: Rule -> FSM -> Parser -> (Maybe FSM)
tryMatchingRule (Rule stR rR) (FSM st input output) parserFunc
							| stR == st = case (tryMatchingRHSS rR input) of
									Just (rem, stResult, matched) -> Just (FSM stResult rem (parserFunc output st matched))
									Nothing -> Nothing
							| otherwise = Nothing

--executes tryMatchingRule on a lit of Rule; returns (Just <remaining part of input after matching>) for the first matching Rule; returns Nothing if none could be matched
tryMatchingRules :: [Rule] -> FSM -> Parser -> (Maybe FSM)
tryMatchingRules (x:xs) m parserFunc = case (tryMatchingRule x m parserFunc) of
				Just m2 -> Just m2
				Nothing -> (tryMatchingRules xs m parserFunc)
tryMatchingRules [] m parserFunc = Nothing

-- returns whether arg0 is in an accepting state and its input is empty
isAccepted :: FSM -> Bool
isAccepted (FSM (State stID accepted) input output) = (input == "" && accepted)

--TERMINALS
tS, tP, tZ, tD, tE :: Terminals
tS = ["+","-"]
tP = ["."]
tZ = ["0"]
tD = ["1","2","3","4","5","6","7","8","9"]
tE = ["e","E"]

--STATES
stS, stS2, stI0, stT135, stT2, stT4, stI1, stT6, stI2 :: State
stS = State "S" False
stS2 = State "S2" False
stI0 = State "I0" False
stT135 = State "T135" True
stT2 = State "T2" True
stT4 = State "T4" True
stI1 = State "I1" False
stT6 = State "T6" True
stI2 = State "I2" False

--parsing output
containsAnyTerminal :: String -> Terminals -> Bool
containsAnyTerminal str (x:xs)
			| isInfixOf x str = True
			| otherwise = containsAnyTerminal str xs

containsAnyTerminal str [] = False

floatParser :: Parser
floatParser prefix st symbol
		| st == stI1 && (last prefix == 'e') && (symbol == "+") = prefix ++ "+"
		| st == stI1 && (last prefix == 'e') && (symbol /= "-") = prefix ++ "+" ++ symbol
floatParser prefix _ "+" = prefix
floatParser prefix _ "."
		| not (containsAnyTerminal prefix (tZ ++ tD)) = prefix ++ "0."
		| otherwise = prefix ++ "."
floatParser prefix st "E" = floatParser prefix st "e"
floatParser prefix _ "e"
		| last prefix == '.' = prefix ++ "0e"
		| not (containsAnyTerminal prefix tP) = prefix ++ ".0e"
		| otherwise = prefix ++ "e"
floatParser prefix st symbol = prefix ++ symbol

finalizeParsed :: String -> String
finalizeParsed "0" = "0"
finalizeParsed str
		| last str == '.' = str ++ "0"
		| not (containsAnyTerminal str (tE ++ tP)) = str ++ ".0"
		| otherwise = str

--RULES
r1, r2, r3, r4, r5, r6, r7, r8, r9 :: Rule
r1 = Rule stS [(RHS tS stS2),(RHS tP stI0),(RHS tZ stT2),(RHS tD stT4)]
r2 = Rule stS2 [(RHS tP stI0),(RHS tZ stT2),(RHS tD stT4)]
r3 = Rule stI0 [(RHS tD stT135)]
r4 = Rule stT135 [(RHS tD stT135),(RHS tE stI1)]
r5 = Rule stT2 [(RHS tP stT135),(RHS tE stI1)]
r6 = Rule stT4 [(RHS tD stT4),(RHS tP stT135),(RHS tE stI1)]
r7 = Rule stI1 [(RHS tS stI2),(RHS tD stT6)]
r8 = Rule stT6 [(RHS tD stT6),(RHS tZ stT6)]
r9 = Rule stI2 [(RHS tD stT6)]

rules :: [Rule]
rules = [r1,r2,r3,r4,r5,r6,r7,r8,r9]

-- FSM initialization
startingState :: State
startingState = stS

initFSM :: String -> FSM
initFSM s = FSM startingState s ""

-- runFSM: recursive; returns (Just machine) if the machine has accepted the input and it is in terminal state; returns Nothing otherwise
runFSM :: FSM -> (Maybe FSM)
runFSM m = case (tryMatchingRules rules m floatParser) of
		Just m2 -> (if isAccepted m2 then Just m2 else runFSM m2)
		Nothing -> Nothing

getResult :: (Maybe FSM) -> String
getResult (Just (FSM _ _ output)) = "OK " ++ (finalizeParsed output)
getResult Nothing = "FAIL"

runFloatParser :: String -> String
runFloatParser input = getResult (runFSM (initFSM input))

--

test = print ([runFloatParser "1" == "OK 1.0",
		runFloatParser "-3.14" == "OK -3.14",
		runFloatParser "0" == "OK 0",
		runFloatParser "+0.1" == "OK 0.1",
		runFloatParser ".1" == "OK 0.1",
		runFloatParser "1" == "OK 1.0",
		runFloatParser "+1.235" == "OK 1.235",
		runFloatParser "1.02" == "FAIL",
		runFloatParser "+0.02" == "FAIL",
		runFloatParser "-0.230" == "FAIL",
		runFloatParser "." == "FAIL",
		runFloatParser "+x1" == "FAIL",
		runFloatParser "+ x1" == "FAIL",
		runFloatParser "+ 1" == "FAIL",
		runFloatParser "+" == "FAIL",
		runFloatParser "-" == "FAIL",
		runFloatParser "a" == "FAIL",
		runFloatParser "abc2" == "FAIL",
		--bovites
		runFloatParser "-3.14e2" == "OK -3.14e+2",
		runFloatParser "1.12e+1" == "OK 1.12e+1",
		runFloatParser "+0.1E10" == "OK 0.1e+10",
		runFloatParser ".1E-4" == "OK 0.1e-4",
		runFloatParser "1.E4"== "OK 1.0e+4",
		runFloatParser "13e4" == "OK 13.0e+4",
		runFloatParser "1e" == "FAIL",
		runFloatParser "+1.235E0" == "FAIL",
		runFloatParser "1e-0" == "FAIL",
		runFloatParser "+1.235E+" == "FAIL",
		runFloatParser "2+1" == "FAIL",
		runFloatParser "2 e-4" == "FAIL",
		runFloatParser "2e-04" == "FAIL"])
		
{-
test2 = case (tryMatchingRule r1 (initFSM "-1.235") floatParser) of
		Just (FSM (State stID stAcc) rem out) -> print ("Just " ++ stID ++ " " ++ rem ++ " " ++ out)
		Nothing -> print ("Nothing")
	
Just m1 = tryMatchingRules rules (initFSM "+.0235") floatParser
Just m2 = tryMatchingRules rules m1 floatParser
Just m3 = tryMatchingRules rules m2 floatParser
Just m4 = tryMatchingRules rules m3 floatParser
Just m5 = tryMatchingRules rules m4 floatParser
Just m6 = tryMatchingRules rules m5 floatParser
--Just m7 = tryMatchingRules rules m6 floatParser
	
test4 = case m3 of
	FSM (State stID stAcc) rem out -> print ("Just " ++ stID ++ " " ++ rem ++ " " ++ out)
		
test5 = print (isAccepted m5)
-}


readLoop = do 	done <- isEOF
		if done
		then putStrLn ""
		else do input <- getLine
			putStrLn (runFloatParser input)
			readLoop
		
main = readLoop
