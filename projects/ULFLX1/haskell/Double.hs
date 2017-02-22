import qualified Control.Exception as E
import Control.Monad (forM_)
import Data.Char (isDigit)

isExp :: Char -> Bool
isExp c = c == 'E' || c == 'e'

data State = S | S' | I0 | T1 | T2 | T3 | T4 | T5 | I6 | T7

double :: State -> String -> String
double S  ('+':xs) = 'O':'K':' ':double S' xs
double S  ('-':xs) = 'O':'K':' ':double S' xs
double S  []       = error "FAIL"
double S  xs       = 'O':'K':' ':double S' xs
double S' ('.':xs) = '0':'.':double I0 xs
double S' ('0':xs) = '0':double T2 xs
double S' (x:xs)
    | isDigit x    = x:double T4 xs
    | otherwise    = error "FAIL"
double S' []       = error "FAIL"
double I0 (x:xs)
    | isDigit x    = x:double T1 xs
    | otherwise    = error "FAIL"
double I0 []       = error "FAIL"
double T1 (x:xs)
    | isDigit x    = x:double T1 xs
    | isExp x      = x:double I6 xs
    | otherwise    = error "FAIL"
double T2 ('.':xs) = '.':double T3 xs
double T3 (x:xs)
    | isDigit x    = x:double T3 xs
    | isExp x      = x:double I6 xs
    | otherwise    = error "FAIL"
double T4 ('.':xs) = '.':double T5 xs
double T4 (x:xs)
    | isDigit x    = x:double T4 xs
    | isExp x      = x:double I6 xs
    | otherwise    = error "FAIL"
double T4 []       = ".0"
double T5 (x:xs)
    | isDigit x    = x:double T5 xs
    | isExp x      = x:double I6 xs
    | otherwise    = error "FAIL"
double I6 (x:xs)
    | '-' == x     = x:double I6 xs
    | '+' == x     = double I6 xs
    | isDigit x    = x:double T7 xs
    | otherwise    = error "FAIL"
double T7 (x:xs)
    | isDigit x    = x:double T7 xs
    | otherwise    = error "FAIL"
double _ []        = []
double _ _         = error "FAIL"

testData :: [String]
testData = [double S "1", double S "-3.14", double S "0", double S "+0.1",
            double S ".1", double S "1", double S ".", double S "+ x1",
            double S "-", double S "3.14e-2", double S "1e2", double S "2.4E-5",
            double S ".1e+3"]

evalData :: [String] -> IO ()
evalData d = forM_ d (\a -> E.catch (putStrLn $ a)
                            (\(E.ErrorCall err) -> putStrLn $ err))

main :: IO()
main = evalData testData
