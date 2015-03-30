{-# LANGUAGE FlexibleContexts #-}

import System.IO
import Control.Monad.State
import Control.Monad.Writer
import Data.Char (chr, ord, toUpper)
import Data.List (find)
import Data.Ord

-- States of Machine
data MyState = STATE_ERR
             | STATE_S0
             | STATE_S1
             | STATE_I0
             | STATE_T1
             | STATE_T2
             | STATE_T3
             | STATE_T4
             | STATE_T5

-- Type of action (transaction function)
type ActionFunc = (MyState, String) -> Char -> (MyState, String)

-- Convert MyState to String
myStateToStr :: MyState -> String
myStateToStr STATE_ERR = "STATE_ERR"
myStateToStr STATE_S0  = "STATE_S0"
myStateToStr STATE_S1  = "STATE_S1"
myStateToStr STATE_I0  = "STATE_I0"
myStateToStr STATE_T1  = "STATE_T1"
myStateToStr STATE_T2  = "STATE_T2"
myStateToStr STATE_T3  = "STATE_T3"
myStateToStr STATE_T4  = "STATE_T4"
myStateToStr STATE_T5  = "STATE_T5"

-- There are the terminals, and theirs actions
terminals :: [(Char, ActionFunc)]
terminals = [ ('+', actionS),
              ('-', actionS),
              ('.', actionP),
              ('0', actionZ)
            ] ++
            [((chr (49 + x)), actionD) | x <- [0..8]]

-- Empty action
actionE :: (MyState, String) -> Char -> (MyState, String)
actionE a _ = a

-- S action
actionS :: (MyState, String) -> Char -> (MyState, String)
actionS (STATE_S0, str) c = (STATE_S1, str)
actionS (_,_)           _ = myFail

-- P action
actionP :: (MyState, String) -> Char -> (MyState, String)
actionP (STATE_S1, str) c = (STATE_I0, ('0':[c]))
actionP (STATE_T2, str) c = (STATE_T3, str ++ [c])
actionP (STATE_T4, str) c = (STATE_T5, str ++ [c])
actionp (_,_)           _ = myFail

-- Z action
actionZ :: (MyState, String) -> Char -> (MyState, String)
actionZ (STATE_S1, str) c = (STATE_T2, str ++ [c])
actionZ (_,_)           _ = myFail

-- D action
actionD :: (MyState, String) -> Char -> (MyState, String)
actionD (STATE_I0, str) c = (STATE_T1, str ++ [c])
actionD (STATE_S1, str) c = (STATE_T4, str ++ [c]) --actionD (STATE_S1, str) c = (STATE_T4, str ++ [c])
actionD (STATE_T1, str) c = (STATE_T1, str ++ [c])
actionD (STATE_T3, str) c = (STATE_T3, str ++ [c])
actionD (STATE_T4, str) c = (STATE_T4, str ++ [c])
actionD (STATE_T5, str) c = (STATE_T5, str ++ [c])
actionD (_,_)           _ = myFail

-- Fail action
actionFail :: (MyState, String) -> Char -> (MyState, String)
actionFail _ _ = myFail

-- Fail pair
myFail :: (MyState, String)
myFail = (STATE_ERR, "FAIL")

-- Check the final state
isOk :: MyState -> Bool
isOk STATE_ERR = False
isOk STATE_S0  = False
isOk STATE_S1  = False
isOk STATE_I0  = False
isOk _         = True

-- This function calculate the next state, and the output string.
stateMachine :: (MyState, String) -> Char -> (MyState, String)
stateMachine (s, str) c = nextState c
    where
        nextState :: Char -> (MyState, String)
        nextState c = (clearAction (find (\(x, _) -> c == x) terminals)) (s, str) c
            where
                clearAction :: Maybe (Char, ActionFunc) -> ActionFunc
                clearAction (Just (_, f)) = f
                clearAction Nothing       = actionFail

{-
 I have to create this function, because if the input is only 1..9, the program has to append
 the ".0" for the calculated string.
-}
checkEnd :: (MyState, String) -> (MyState, String)
    {-
    This is the logical:
        stateMachine (stateMachine (STATE_T4, str) '.') '0'
    But the '0' is not digit therefore we have to hack. -}
checkEnd (STATE_T4, str) = actionD (stateMachine (STATE_T4, str) '.') '0' 
checkEnd (STATE_I0, _)   = myFail
checkEnd (STATE_S0, _)   = myFail
checkEnd (STATE_S1, _)   = myFail
checkEnd cs              = cs

-- This fuction calculate the result
process :: String -> (MyState, String)
process str = foldl stateMachine (firstTerm str, "") str
    where
        firstTerm :: String -> MyState
        firstTerm (c:_)
            | c == '+' || c == '-' = STATE_S0
            | otherwise            = STATE_S1

-- Tis is the main function. The program reads from STDIN and write to STDOUT.
main =
    do eof <- isEOF
       if eof
       then return()
       else do inStr <- getLine
               putStr (inStr++"\t")
               let (s, out) = checkEnd (process inStr)
               if isOk s
                    then putStr "OK "
                    else putStr ""
               --putStr (" " ++ (myStateToStr s) ++ " ")
               putStrLn out
               main

