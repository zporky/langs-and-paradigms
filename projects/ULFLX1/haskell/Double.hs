import Data.Char (isDigit)

data State = S | S' | I0 | T1 | T2 | T3 | T4 | T5

double :: State -> String -> Bool
double S  ('+':xs) = double S' xs
double S  ('-':xs) = double S' xs
double S  xs       = double S' xs
double S  []       = False
double S' ('.':xs) = double I0 xs
double S' ('0':xs) = double T2 xs
double S' (x:xs)
    | isDigit x    = double T4 xs
    | otherwise    = False
double S' []       = False
double I0 (x:xs)
    | isDigit x    = double T1 xs
    | otherwise    = False
double I0 []       = False
double T1 (x:xs)
    | isDigit x    = double T1 xs
    | otherwise    = False
double T2 ('.':xs) = double T3 xs
double T3 (x:xs)
    | isDigit x    = double T3 xs
    | otherwise    = False
double T4 ('.':xs) = double T5 xs
double T4 (x:xs)
    | isDigit x    = double T4 xs
    | otherwise    = False
double T5 (x:xs)
    | isDigit x    = double T5 xs
    | otherwise    = False
double _ []        = True
double _ _         = False

testData :: [(Bool, Bool)]
testData = [(double S "1", True), (double S "-3.14", True),
            (double S "0", True), (double S "+0.1", True),
            (double S ".1", True), (double S ".", False),
            (double S "+ x1", False), (double S "-", False),
            (double S "3.14e-2", False)]

test :: [(Bool, Bool)] -> [Bool]
test = map $ uncurry (==)

main :: IO()
main = print $ test testData
