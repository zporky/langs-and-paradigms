{-# LANGUAGE FlexibleContexts #-}

module StateMachine where

import Data.Char (chr, ord)
import Data.List (isPrefixOf, maximumBy)

type Context = (String, String)

-- CHECK TERMINAL SYMBOLS

isS :: Char -> Bool
isS '+' = True
isS '-' = True
isS _ = False

isP :: Char -> Bool
isP '.' = True
isP _ = False

isZ :: Char -> Bool
isZ '0' = True
isZ _ = False

isD :: Char -> Bool
isD c = (ord c) >= 49 && (ord c) <= 57

isE :: Char -> Bool
isE 'e' = True
isE 'E' = True
isE _   = False

-- STATE E'
state_E2 :: Context -> String
state_E2 ("", o) = o -- Elfogado allapot
state_E2 ((i:is), o) = if (isD i)
            then state_E2 (is, (o ++ [i]))
            else "FAIL"

-- STATE I1
state_I1 :: Context -> String
state_I1 (_, "FAIL") = "FAIL"
state_I1 ((i:is), o) = if (isD i)
    then state_E2 (is, (o ++ [i]))
    else "FAIL"
state_I1 (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input


-- STATE E

state_E :: Context -> String
state_E ((i:is), o) = if (isS i)
    then state_E2 (is, o++[i])
    else state_E2 (i:is, o++"+")
state_E (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input
	
-- STATE T1
state_T1 :: Context -> String
state_T1 ("", o) = o -- Elfogado allapot
state_T1 ((i:is), o) = if (isD i )
    then state_T1 (is, (o ++ [i]))
    else if (isE i)
			then state_E (is, o ++ "e")
			else "FAIL"

-- STATE T3
state_T3 :: Context -> String
state_T3 ("", o) = o -- Elfogado allapot
state_T3 ((i:is), o) = if (isD i )
    then state_T1 (is, (o ++ [i]))
    else "FAIL"	
	
-- STATE I0

state_I :: Context -> String
state_I ((i:is), o) = if (isD i )
    then state_T1 (is, (o ++ [i]))
    else "FAIL"
state_I (_, _) = "FAIL" -- Nem elfogado allapot

-- STATE T2

state_T2 :: Context -> String
state_T2 ("", o) = o  -- Elfogado allapot
state_T2 ((i:is), o) = if (isP i)
    then state_T3 (is, (o ++ [i]))
    else if (isE i)
			then state_E (is, o ++ "e")
			else "FAIL"
			
-- STATE T4
state_T4 :: Context -> String
state_T4 ("", o) = o ++ ".0" -- Elfogado allapot
state_T4 ((i:is), o) = if (isP i)
    then if (length is) == 0
        then o ++ [i] ++ "0"
        else state_T3 (is, (o ++ [i]))
    else if (isD i)
        then state_T4 (is, (o ++ [i]))
        else if (isE i)
			then state_E (is, o ++ "e")
			else "FAIL"

-- STATE S'
state_S2 :: Context -> String
state_S2 ((i:is), o) = if (isP i)
    then state_I (is, (o ++ "0" ++ [i]))
    else if (isZ i)
        then state_T2 (is, (o ++ [i]))
        else if (isD i)
            then state_T4 (is, (o ++ [i]))
            else "FAIL"
state_S2 (_, _) = "FAIL" -- Nem elfogado allapot

-- STATE S
state_S :: Context -> String
state_S ((i:is), o) = if (isS i)
    then state_S2 (is, o)
    else state_S2 (i:is, o)

-- START STATE MACHINE
state_machine :: String -> String
state_machine i = state_S (i, "")

process :: String -> String
process i = if (result == "FAIL")
    then result
    else "OK " ++ result
    where result = state_machine i

-- RUNNER
runner :: IO ()
runner = do
    line <- getLine
    if line /= ""
        then (putStr ((process line) ++ "\n")) >>= (return(runner))
        else return()

-- TEST
test :: [Bool]
test = [
      process "1"          == "OK 1.0"
    , process "-3.14"    == "OK 3.14"
    , process "0"        == "OK 0"
    , process "+0.1"     == "OK 0.1"
    , process ".1"       == "OK 0.1"
    , process "1"        == "OK 1.0"
    , process "."        == "FAIL"
    , process "+x1"      == "FAIL"
    , process "-"        == "FAIL"
    , process "3.14e-2"  == "OK 3.14e-2"
	, process "1e2"      == "OK 1e+2" 
	, process "2.4E-5"   == "OK 2.4e-5"
	, process  ".1e+3"   == "OK 0.1e+3"

    -- extra
    , process "01.0"     == "FAIL"
    , process "12.12.12" == "FAIL"
    , process "..12"     == "FAIL"
    , process "--12"     == "FAIL"
    , process ".-3"      == "FAIL"
    , process "00"       == "FAIL"
	]