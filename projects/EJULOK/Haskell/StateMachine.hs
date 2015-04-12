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

isE :: Char -> Bool
isE 'e' = True
isE 'E' = True
isE _ = False

isD :: Char -> Bool
isD c = (ord c) >= 49 && (ord c) <= 57

-- STATE T1, T3 and T5

state_T1_3_5 :: Context -> String
state_T1_3_5 (_, "FAIL") = "FAIL"
state_T1_3_5 ("", o) = o -- Elfogado allapot
state_T1_3_5 ((i:is), o) = if (isD i || isZ i)
    then state_T1_3_5 (is, (o ++ [i]))
	else if (isE i)
		then state_I1 (is, (o ++ [i]))
    else "FAIL"

-- STATE I0

state_I0 :: Context -> String
state_I0 ((i:is), o) = if (isD i || isZ i)
    then state_T1_3_5 (is, (o ++ [i]))
    else "FAIL"
state_I0 (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input

-- STATE I1 (új állapot, amely az E karakter utáni állapotot reprezentálja)

state_I1 :: Context -> String
state_I1 ((i:is), o) = if (isS i)
	then state_I0 (is, (o ++ [i]))
	else if (isD i)
		then state_T1_3_5 (is, o ++ [i])
	else "FAIL"
state_I1 (_, _) = "FAIL"
	
-- STATE T2

state_T2 :: Context -> String
state_T2 (_, "FAIL") = "FAIL"
state_T2 ("", o) = o -- Elfogado allapot
state_T2 ((i:is), o) = if (isP i)
    then if (length is) == 0
        then o ++ [i] ++ ".0"
        else state_T1_3_5 (is, (o ++ [i]))
	else if (isE i) 
		then state_I1 (is, (o ++ [i]))
    else "FAIL"

-- STATE T4

state_T4 :: Context -> String
state_T4 (_, "FAIL") = "FAIL"
state_T4 ((i:is), o) = if (isP i)
    then if (length is) == 0
        then o ++ [i] ++ "0"
        else state_T1_3_5 (is, (o ++ [i]))
    else if (isD i || isZ i)
        then state_T4 (is, (o ++ [i]))
	else if (isE i)
		then state_I1 (is, (o ++ [i]))
    else "FAIL"
state_T4 ("", o) = o -- Elfogado allapot

-- STATE S'

state_S2 :: Context -> String
state_S2 ((i:is), o) = if (isP i)
    then state_I0 (is, (o ++ "0" ++ [i]))
    else if (isZ i)
        then state_T2 (is, (o ++ [i]))
        else if (isD i)
            then if (length is) == 0
                then o ++ [i] ++ ".0"
                else state_T4 (is, (o ++ [i]))
            else "FAIL"
state_S2 (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input

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
    -- specified
    process "1"          == "OK 1.0"
    , process "-3.14"    == "OK 3.14"
    , process "0"        == "OK 0"
    , process "+0.1"     == "OK 0.1"
    , process ".1"       == "OK 0.1"
    , process "1"        == "OK 1.0"
    , process "."        == "FAIL"
    , process "+x1"      == "FAIL"
    , process "-"        == "FAIL"
    , process "3.14e-2"  == "FAIL"

    -- discussed
    , process "1."       == "OK 1.0"
    , process "1.0"      == "OK 1.0"

    -- extra
    , process "01.0"     == "FAIL"
    , process "12.12.12" == "FAIL"
    , process "..12"     == "FAIL"
    , process "--12"     == "FAIL"
    , process ".-3"      == "FAIL"
    , process "100.0"    == "OK 100.0"
    , process "100.001"  == "OK 100.001"
    , process "1.00000"  == "OK 1.00000"
    , process "00"       == "FAIL"
    ]
