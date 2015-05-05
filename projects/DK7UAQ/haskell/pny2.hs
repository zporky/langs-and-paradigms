{-# LANGUAGE LambdaCase #-}

import Data.List
import Control.Monad
import Control.Applicative
import System.IO (isEOF)

--for float parsing
-- <parsed before> <current state> <parsed terminal> <parsed float part> -> <parse result>
type Parser = String -> State -> String -> String  

--for recognizing
data State = State String Bool deriving (Eq, Show)
data RHS = RHS Terminals State deriving (Eq, Show)
type Terminals = [String]
data Rule = Rule State [RHS] deriving (Eq, Show)
data FSM = FSM State String String deriving (Eq, Show) -- FSM <current state> <reminaing input> <parsed float>

-- returns (Just y) if arg0 has an y element that is a prefix for arg1; returns Nothing otherwise
hasPrefix :: Terminals -> String -> Maybe String
hasPrefix terminals inp =
  msum $ map (\prefix -> prefix <$ guard (isPrefixOf prefix inp)) $ terminals

-- returns (Just (Tuple3 <the remaining part of arg1> <the result State> <the matched terminal>)) after it was successfully matched with arg0; if the match was not successful, returns Nothing
tryMatchingRHS :: RHS -> String -> Maybe (String, State, String)
tryMatchingRHS (RHS ts stResult) input =
  fmap (\prefix -> (drop (length prefix) input, stResult, prefix)) $
  hasPrefix ts input

-- executes tryMatchingRHS on a list of RHS; returns (Just (Tuple3 <remaining part of input after matching> <the result state> <the matched terminal>)) for the first matching RHS; returns Nothing if none could be matched
tryMatchingRHSS :: [RHS] -> String -> Maybe (String, State, String)
tryMatchingRHSS xs input = msum $ map (`tryMatchingRHS` input) xs

-- returns Just <the remaining part of arg2>) after it was successfully matched with arg0; if the match was not successful, returns Nothing
tryMatchingRule :: Rule -> FSM -> Parser -> Maybe FSM
tryMatchingRule (Rule stR rR) (FSM st input output) parserFunc = do
  guard $ stR == st
  (rem, stResult, matched) <- tryMatchingRHSS rR input
  Just $ FSM stResult rem (parserFunc output st matched)

--executes tryMatchingRule on a lit of Rule; returns (Just <remaining part of input after matching>) for the first matching Rule; returns Nothing if none could be matched
tryMatchingRules :: [Rule] -> FSM -> Parser -> Maybe FSM
tryMatchingRules rules m parserFunc =
  msum $ map (\x -> tryMatchingRule x m parserFunc) rules

-- returns whether arg0 is in an accepting state and its input is empty
isAccepted :: FSM -> Bool
isAccepted (FSM (State _ accepted) input _) = null input && accepted

floatParser :: Parser
floatParser prefix _ "+" = prefix
floatParser prefix _ "."
  | not (any (`isInfixOf` prefix) (tZ ++ tD)) = prefix ++ "0."
  | otherwise = prefix ++ "."                        
floatParser prefix _ symbol = prefix ++ symbol

finalizeParsed :: String -> String
finalizeParsed = \case
  "0" -> "0"
  str ->
    if all (`notElem` ".eE") str' then
      str' ++ ".0"
    else if head str' == '.' then
      '0':str'
    else
      str'
    where
      str' = map (\case 'E' -> 'e'; x -> x) $ filter (/='+') str

--TERMINALS
tS, tP, tZ, tD :: Terminals
tS = ["+","-"]
tP = ["."]
tZ = ["0"]
tD = ["1","2","3","4","5","6","7","8","9"]
tE = ["e", "E"]

--STATES
stS  = State "S"  False
stS2 = State "S2" False
stI0 = State "I0" False
stT1 = State "T1" True
stT2 = State "T2" True
stT4 = State "T4" True
stT5 = State "T5" False
stT6 = State "T5" False
stT7 = State "T5" True      

rules = [
  Rule stS  [RHS tP stI0, RHS tZ stT2, RHS tD stT4, RHS tS stS2],
  Rule stS2 [RHS tP stI0, RHS tZ stT2, RHS tD stT4],
  Rule stI0 [RHS tD stT1],
  Rule stT1 [RHS tD stT1, RHS tE stT5],
  Rule stT2 [RHS tP stT1, RHS tE stT5],
  Rule stT4 [RHS tD stT4, RHS tP stT1, RHS tE stT5],
  Rule stT5 [RHS tS stT6, RHS tD stT7],
  Rule stT6 [RHS tD stT7],
  Rule stT7 [RHS tD stT7, RHS tZ stT7]]

-- FSM initialization
startingState :: State
startingState = stS

initFSM :: String -> FSM
initFSM s = FSM startingState s ""

-- runFSM: recursive; returns (Just machine) if the machine has accepted the input and it is in terminal state; returns Nothing otherwise
runFSM :: FSM -> Maybe FSM
runFSM m = do
  m2 <- tryMatchingRules rules m floatParser
  if isAccepted m2
     then Just m2
     else runFSM m2          

getResult :: Maybe FSM -> String
getResult (Just (FSM _ _ output)) = "OK " ++ finalizeParsed output
getResult Nothing = "FAIL"

runFloatParser :: String -> String
runFloatParser = getResult . runFSM . initFSM 


main :: IO ()
main = forever $ putStrLn . runFloatParser =<< getLine


tests =
  [runFloatParser "1" == "OK 1.0",
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
   runFloatParser "3.14e-2" == "OK 3.14e-2",
   runFloatParser "2e+2" == "OK 2e2",
   runFloatParser "2E+2" == "OK 2e2",
   runFloatParser "2e+0" == "FAIL"]

