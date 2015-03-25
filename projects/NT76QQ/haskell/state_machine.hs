{-# LANGUAGE FlexibleContexts #-}

import System.IO
import Control.Monad.State
import Control.Monad.Writer
import Data.Char (chr, ord, toUpper)
import Data.List (find)
import Data.Ord

data MyState = STATE_ERR
             | STATE_S0
	         | STATE_S1
	         | STATE_I0
	         | STATE_T1
	         | STATE_T2
	         | STATE_T3
	         | STATE_T4
	         | STATE_T5

type ActionFunc = (MyState, String) -> Char -> (MyState, String)

terminals :: [(Char, ActionFunc)]
terminals = [ ('+', actionS),
              ('-', actionS),
              ('.', actionP),
              ('0', actionZ)
            ] ++
            [((chr (49 + x)), actionD) | x <- [0..8]]

actionE :: (MyState, String) -> Char -> (MyState, String)
actionE a _ = a

actionS :: (MyState, String) -> Char -> (MyState, String)
actionS (STATE_S0, str) c = (STATE_S1, str)
actionS (_,_)           _ = myFail

actionP :: (MyState, String) -> Char -> (MyState, String)
actionP (STATE_S1, str) c = (STATE_I0, ('0':[c]))
actionP (STATE_T2, str) c = (STATE_T3, str ++ [c])
actionP (STATE_T4, str) c = (STATE_T5, str ++ [c])
actionp (_,_)           _ = myFail

actionZ :: (MyState, String) -> Char -> (MyState, String)
actionZ (STATE_S1, str) c = (STATE_T2, str ++ [c])
actionZ (_,_)           _ = myFail

actionD :: (MyState, String) -> Char -> (MyState, String)
actionD (STATE_I0, str) c = (STATE_T1, str ++ [c])
actionD (STATE_S1, str) c = (STATE_T4, str ++ [c])
actionD (STATE_T1, str) c = (STATE_T1, str ++ [c])
actionD (STATE_T3, str) c = (STATE_T3, str ++ [c])
actionD (STATE_T4, str) c = (STATE_T4, str ++ [c])
actionD (STATE_T5, str) c = (STATE_T5, str ++ [c])
actionD (_,_)           _ = myFail

actionFail :: (MyState, String) -> Char -> (MyState, String)
actionFail _ _ = myFail

myFail :: (MyState, String)
myFail = (STATE_ERR, "FAIL")

isOk :: MyState -> Bool
isOk STATE_ERR = False
isOk STATE_S0  = False
isOk STATE_S1  = False
isOk STATE_I0  = False
isOk _         = True

stateMachine :: (MyState, String) -> Char -> (MyState, String)
stateMachine (s, str) c = nextState c
    where
        nextState :: Char -> (MyState, String)
        nextState c = (clearAction (find (\(x, _) -> c == x) terminals)) (s, str) c
            where
                clearAction :: Maybe (Char, ActionFunc) -> ActionFunc
                clearAction (Just (_, f)) = f
                clearAction Nothing       = actionFail

checkEnd :: (MyState, String) -> (MyState, String)
checkEnd (STATE_T4, str) = stateMachine (stateMachine (STATE_T4, str) '.') '0'
checkEnd (STATE_I0, str) = myFail
checkEnd (STATE_S0, str) = myFail
checkEnd (STATE_S1, str) = myFail
checkEnd cs              = cs

process :: String -> (MyState, String)
process str = foldl stateMachine (firstTerm str, "") str
    where
        firstTerm :: String -> MyState
        firstTerm (c:_)
            | c == '+' || c == '-' = STATE_S0
            | otherwise            = STATE_S1

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
               putStrLn out
               main

