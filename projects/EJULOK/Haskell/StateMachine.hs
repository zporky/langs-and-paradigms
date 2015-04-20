
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
isE _ = False

-- STATE T1, T3 and T5

state_T1_3_5 :: Context -> String
state_T1_3_5 (_, "FAIL") = "FAIL"
state_T1_3_5 ("", o) = o -- Elfogado allapot
state_T1_3_5 ((i:is), o) = if (isD i)
    then state_T1_3_5 (is, (o ++ [i]))
    else if (isE i)
        then if last o == '.'
	    then state_I1 (is, (o ++ "0e"))
	    else state_I1 (is, (o ++ "e"))
	else "FAIL"

-- STATE I0

state_I :: Context -> String
state_I ((i:is), o) = if (isD i)
    then state_T1_3_5 (is, (o ++ [i]))
    else "FAIL"
state_I (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input

-- STATE T2

state_T2 :: Context -> String
state_T2 (_, "FAIL") = "FAIL"
state_T2 ("", o) = o -- Elfogado allapot
state_T2 ((i:is), o) = if (isP i)
    then if (length is) == 0
        then o ++ [i] ++ ".0"
        else state_T1_3_5 (is, (o ++ [i]))
    else if (isE i)
	then state_I1 (is, (o ++ ".0e"))
        else "FAIL"

-- STATE T4

state_T4 :: Context -> String
state_T4 (_, "FAIL") = "FAIL"
state_T4 ((i:is), o) = if (isP i)
    then if (length is) == 0
        then o ++ [i] ++ "0"
        else state_T1_3_5 (is, (o ++ [i]))
    else if (isD i)
        then state_T4 (is, (o ++ [i]))
	else if (isE i)
	    then state_I1 (is, (o ++ ".0e"))
            else "FAIL"
state_T4 ("", o) = o -- Elfogado allapot

-- STATE S'

state_S2 :: Context -> String
state_S2 ((i:is), o) = if (isP i)
    then state_I (is, (o ++ "0" ++ [i]))
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
    then if i == '-' 
	then state_S2 (is, "-" ++ o) 
	else state_S2 (is, o) 
    else state_S2 (i:is, o)

-- STATE I1

state_I1 :: Context -> String
state_I1 (_, "FAIL") = "FAIL"
state_I1 ((i:is), o) = if (isD i)
    then state_T6 (is, (o ++ "+" ++ [i]))
    else if (isS i)
	then state_I2 (is, (o ++ [i]))
        else "FAIL"
state_I1 (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input

-- STATE T6

state_T6 :: Context -> String
state_T6 (_, "FAIL") = "FAIL"
state_T6 ((i:is), o) = if (isD i || isZ i)
    then state_T6 (is, (o ++ [i]))
    else "FAIL"
state_T6 ("", o) = o -- Elfogado allapot

--state I2

state_I2 :: Context -> String
state_I2 (_, "FAIL") = "FAIL"
state_I2 ((i:is), o) = if (isD i)
    then state_T6 (is, (o ++ [i]))
    else "FAIL"
state_I2 (_, _) = "FAIL" -- Nem elfogado allapot & FAIL input
    
    
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

test = print([
    -- specified
    process "1"          == "OK 1.0"
    , process "-3.14"    == "OK -3.14"
    , process "0"        == "OK 0"
    , process "+0.1"     == "OK 0.1"
    , process ".1"       == "OK 0.1"
    , process "1"        == "OK 1.0"
    , process "."        == "FAIL"
    , process "+x1"      == "FAIL"
    , process "-"        == "FAIL"

    -- discussed
    , process "1."       == "OK 1.0"
    , process "1.0"      == "FAIL"

    -- extra
    , process "01.0"     == "FAIL"
    , process "12.12.12" == "FAIL"
    , process "..12"     == "FAIL"
    , process "--12"     == "FAIL"
    , process ".-3"      == "FAIL"
    , process "100.0"    == "FAIL"
    , process "100.001"  == "FAIL"
    , process "1.00000"  == "FAIL"
    , process "00"       == "FAIL"
    
    --task2 extension
    , process "-3.14e2" == "OK -3.14e+2"
    , process "1.12e+1" == "OK 1.12e+1"
    , process "+0.1E10" == "OK 0.1e+10"
    , process ".1E-4" 	== "OK 0.1e-4"
    , process "1.E4" == "OK 1.0e+4"
    , process "13e4" == "OK 13.0e+4"
    , process "1e" 	== "FAIL"
    , process "+1.235E0" == "FAIL"
    , process "1e-0" 	== "FAIL"
    , process "+1.235E+" == "FAIL"
    , process "2+1" 	== "FAIL"
    , process "2 e-4" 	== "FAIL"
    , process "2e-04" 	== "FAIL"
    ])

main = runner
    