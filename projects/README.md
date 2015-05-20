Feladat:
Mostantól fogadja el az 1.3e-5 alakú számokat is.

Először a nyelvtant alakítottam át.
Új terminális:
x : {e, E}

Minden helyre, ahol szerepelhet exponenciális rész, hozzáadtam 1 új szabályt, N -> xE
A további szabályokat az exponenciális rész meghatározásához a következőképp definiáltam:
E -> sl1 | l1
l0 -> dE'
E' -> dE' | e

A létező szabályokat is átalakítottam. Figyelnem kellett rá, hogy az 1.e alakú számokat ne fogadjam el. A T1 szabályba való belépésnek feltétele volt, hogy a tizedespont után legalább 1 szám legyen, így ezt kihasználtam minden esetben, melyben létezik törtrész.
A T3 szabály esetén, legalább 1 számjegy kell az exponenciális rész előtt. Amennyiben a számjegy megvan, a T1 szabály használható a szám vizsgálatának befejezésére.
A T5 szabályt (mivel megegyezik a T3 szabállyal) kivettem. 
A T2 és T4 szabályok azokat a számokat írják le, amelyek (még) nem tartalmaznak törtrészt. Ha exponenciális rész következik, akkor törtrész már nem lesz, így az E szabály alkalmazható.

A kialakult szabályrendszer a következő:
S -> S' | sS'
S' -> pl0 | zT2 | dT4
l0 -> dT1
T1 -> dT1 | xE | e
T2 -> pT3 | xE | e
T3 -> dT1 | e
T4 -> dT4 | pT3 | xE | e
E  -> sl1
l1 -> dE'
E' -> dE' | e

Erlang (saját):
Az új szabályokat az előző feladathoz hasonlóan implementáltam és átalakítottam.
Mivel most egyes esetekben a szám közepébe is kell karaktert beszúrni (1e3 -> OK 1e+3), ezért a függvények kaptak 1 további paramétert, így tail-recursive módon írtam bele a karaktereket, a vizsgálat végén pedig ezt a listát adtam vissza. Így nem kell további vizsgálatokat végeznem (melyik szabálynál állt le a vizsgálat), a kiírást egyszerűen elvégezhetem: {ok, Result} -> "OK "++Result;

Java (saját):
A szabályokat az előző feladathoz hasonlóan impltementáltam és átalakítottam.
A Terminal enum-ot kiegészítettem az x terminálissal.
A Tokenizer osztályt kiegészítettem egy függvénnyel, mellyel az aktuális karaktert lehet lekérdezni, illetve az 'e' és 'E' karakterek kezelésével.
A bemeneti stringen a szükséges esetekben transzformációkat végzek a Tokenizer osztályban megírt függvények segítségével. Tokenizer így már nem csak tokenizálás szerepet tölt be, hanem transzformációs szerepet is (Nem szép megoldás, de ez volt a leggyorsabb).

Haskell(DK7UAQ):
Rájöttem, hogy az I0 és nem l0. Csodálkoztam is az elején, hogy miért lowerCase. Már tudom :)
Érdekes és egyben funkcionális nyelv. Mások munkái közül ezt láttam a legegyszerűbbnek átírni a saját átalakított szabályrendszeremre, továbbá nagyban hasonlít az Erlangos megoldásomra. A típusrendszere miatt egyszerűbb volt a szövegösszefűzést megvalósítani.

Perl(A298VQ):
Szabályrendszert átírtam az általam módosítottra.