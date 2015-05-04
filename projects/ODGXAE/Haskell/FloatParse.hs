{-# LANGUAGE LambdaCase #-}

import Control.Applicative
import Control.Monad.State
import Data.Char

main :: IO ()
main = do
  line <- getLine
  maybe
    (putStrLn "FAIL")
    (\(dbl, _) -> putStrLn ("OK " ++ show dbl))
    (runStateT double line)
  main

type Parser = StateT String Maybe

sat :: (Char -> Bool) -> Parser Char
sat p = get >>= \case c:cs | p c -> c <$ put cs; _ -> empty

char :: Char -> Parser Char
char = sat . (==)

eof :: Parser ()
eof = get >>= \case [] -> pure (); _ -> empty

digit :: Parser Double
digit = fromIntegral . digitToInt <$> sat isDigit

nonzero :: Parser Double
nonzero = digit >>= \case 0 -> empty; d -> pure d

zero :: Parser Double
zero = 0 <$ char '0'

fraction1 :: Parser Double
fraction1 = (/10) <$> ((+) <$> digit <*> (fraction1 <|> pure 0))

fraction :: Parser Double
fraction = fraction1 <|> pure 0

integral :: Parser Double
integral = go =<< nonzero where
  go acc = do {d <- digit; go (10 * acc + d)} <|> pure acc

sign :: Parser (Double -> Double)
sign = (id <$ char '+') <|> (negate <$ char '-')

double' :: Parser Double
double' = 
      (char '.' *> fraction1)
  <|> ((+) <$> zero <*> ((char '.' *> fraction) <|> pure 0))
  <|> ((+) <$> integral <*> ((char '.' *> fraction) <|> pure 0))

exponent :: Parser Double
exponent = (char 'e' <|> char 'E') *> ((sign <*> integral) <|> integral)      

double :: Parser Double
double = 
       ((sign <*> double') <|> double')
  <**> (((\e n -> n * (10 ** e)) <$> Main.exponent) <|> pure id)
  <*   eof

