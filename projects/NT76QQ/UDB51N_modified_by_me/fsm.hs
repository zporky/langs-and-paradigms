{-# LANGUAGE FlexibleContexts #-}

import Control.Monad.State
import Control.Monad.Writer
import Data.Char
import System.Environment ( getArgs )

isS :: Char -> Bool
isS c = c == '+' || c == '-'

isP :: Char -> Bool
isP c = c == '.'

isZ :: Char -> Bool
isZ c = c == '0'

isD :: Char -> Bool
isD c = elem c ['1','2','3','4','5','6','7','8','9']

isE :: Char -> Bool
isE c = c == 'e'

states :: [Int]
states = [0..11]

state_s0 = 0
state_s1 = 1
state_i0 = 2
state_i1 = 9
state_i2 = 10
state_t1 = 3
state_t2 = 4
state_t3 = 5
state_t4 = 6
state_t5 = 7
state_t6 = 11
state_err = 8


transitions :: Int -> Char -> Int
transitions 0 t -- S0
	| isS t = state_s1
	| otherwise = state_err
transitions 1 t -- S1
	| isP t = state_i0 
	| isZ t = state_t2
	| isD t = state_t4
	| otherwise = state_err
transitions 2 t -- I0
	| (isD t) || (isZ t) = state_t1
	| otherwise = state_err
transitions 9 t -- I1
	| isS t = state_i2
	| otherwise = state_err
transitions 10 t -- I2
	| isD t = state_t6
	| otherwise = state_err
transitions 3 t -- T1
	| (isD t) || (isZ t) = state_t1
	| isE t = state_i1
	| otherwise = state_err
transitions 4 t -- T2
	| isP t = state_t3
	| isE t = state_i1
	| otherwise = state_err
transitions 5 t -- T3
	| (isD t) || (isZ t) = state_t3
	| isE t = state_i1
	| otherwise = state_err
transitions 6 t -- T4
	| (isD t) || (isZ t) = state_t4
	| isP t = state_t5
	| isE t = state_i1
	| otherwise = state_err
transitions 7 t -- T5
	| (isD t) || (isZ t) = state_t5
	| isE t = state_i1
	| otherwise = state_err
transitions 11 t -- T6
	| (isD t) || (isZ t) = state_t6
	| otherwise = state_err
transitions _ t = 8 -- ERROR

evalFileContent file = do
	f <- readFile file
	let numbers = lines f
	return numbers

processContent :: [String] -> WriterT String (State ([Int],String)) ([Int],String)
processContent [] 	= do
	put ([],"")
	logResult ([],"")
processContent (x:xs) = do
	if (isS (head x))
		then put ([state_s0], x)
		else put ([state_s1], x)
	(s, str) <- processLine x
	logResult (s, str)
	processContent xs
	
processLine :: MonadState ([Int],String) m => String -> m ([Int],String)
processLine ""     	= do
	(stat, str) <- get
	return (stat, str)
processLine (x:xs) 	= do
	(stat, str) <- get
	if ((length stat) /= 0) 
		then (do
			let stat' = stat ++ [transitions (last stat) x]
			put (stat', str)
			res <- processLine xs
			return res)
		else
			return (stat, str)

logResult :: ([Int],String) -> WriterT String (State ([Int],String)) ([Int],String)
logResult ([],y)								= writer (([],y), "")
logResult (x,y)
	| elem (last x) [state_s0, state_s1, state_i0, state_i1, state_i2, state_err]
	                                                = writer ((x,y), "FAIL\n")
	| (head x) == state_s0 && (head y) == '-'	    = writer ((x,y), "OK " ++ y ++ "\n")
	| (head x) == state_s0 && (head y) /= '-'	    = writer ((x,y), "OK " ++ (tail y) ++ "\n")
	| (head x) == state_s1 && (x!!1) == state_i0    = writer ((x,y), "OK " ++ "0" ++ y ++ "\n")
	| (last x) == state_t4 && (last y) == '.'	    = writer ((x,y), "OK " ++ y ++ ".0\n")
	| (last x) == state_t5 && (last y) == '.'	    = writer ((x,y), "OK " ++ y ++ "0\n")
	| otherwise									    = writer ((x,y), "OK " ++ y ++ "\n")

main = do
	args <- getArgs
	content <- evalFileContent (head args)
	putStr (evalState (execWriterT (processContent content))  ([],""))

