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
isE c = c == 'e' || c == 'E'

states :: [Int]
states = [0..10]

transitions :: Int -> Char -> Int
transitions 0 t -- S
	| isS t = 1
transitions 1 t -- S'
	| isP t = 2
	| isZ t = 4
	| isD t = 6
	| otherwise = 10
transitions 2 t -- I0
	| isD t = 3
	| otherwise = 10
transitions 3 t -- T1
	| isD t = 3
	| isZ t = 3
	| isE t = 8
	| otherwise = 10
transitions 4 t -- T2
	| isP t = 5
	| isE t = 8
	| otherwise = 10
transitions 5 t -- T3
	| isD t = 5
	| isZ t = 5
	| isE t = 8
	| otherwise = 10
transitions 6 t -- T4
	| isD t = 6
	| isZ t = 6
	| isP t = 7
	| isE t = 8
	| otherwise = 10
transitions 7 t -- T5
	| isD t = 7
	| isZ t = 7
	| isE t = 8
	| otherwise = 10
transitions 8 t -- E
	| isD t = 9
	| isZ t = 9
	| isS t = 9
	| otherwise = 10
transitions 9 t -- T6
	| isD t = 9
	| isZ t = 9
	| otherwise = 10
transitions _ t = 10 -- Fail state

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
		then put ([0], x)
		else put ([1], x)
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
	| elem (last x) [1..2] || (last x) == 8 || (last x) == 10 	= writer ((x,y), "FAIL\n")
	| (head x) == 0								= writer ((x,y), "OK " ++ (tail y) ++ "\n")
	| (head x) == 1 && (x!!1) == 2				= writer ((x,y), "OK " ++ "0" ++ y ++ "\n")
	| (last x) == 6								= writer ((x,y), "OK " ++ y ++ ".0\n")
	| otherwise									= writer ((x,y), "OK " ++ y  ++ "\n")

main = do
	args <- getArgs
	content <- evalFileContent (head args)
	putStr (evalState (execWriterT (processContent content))  ([],""))
