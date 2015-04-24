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

states :: [Int]
states = [0..8]

transitions :: Int -> Char -> Int
transitions 0 t
	| isS t = 1
transitions 1 t
	| isP t = 2 
	| isZ t = 4
	| isD t = 6
	| otherwise = 8
transitions 2 t
	| isD t = 3
	| otherwise = 8
transitions 3 t
	| isD t = 3
	| otherwise = 8
transitions 4 t
	| isP t = 5
	| otherwise = 8
transitions 5 t
	| isD t = 5
	| otherwise = 8
transitions 6 t
	| isD t = 6
	| isP t = 7
	| otherwise = 8
transitions 7 t
	| isD t = 7
	| otherwise = 8
transitions _ t = 8

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
	| elem (last x) [1..2] || (last x) == 8 	= writer ((x,y), "FAIL\n")
	| (head x) == 0								= writer ((x,y), "OK " ++ (tail y) ++ "\n")
	| (head x) == 1 && (x!!1) == 2				= writer ((x,y), "OK " ++ "0" ++ y ++ "\n")
	| (last x) == 6								= writer ((x,y), "OK " ++ y ++ ".0\n")
	| otherwise									= writer ((x,y), "OK " ++ y  ++ "\n")

main = do
	args <- getArgs
	content <- evalFileContent (head args)
	putStr (evalState (execWriterT (processContent content))  ([],""))
