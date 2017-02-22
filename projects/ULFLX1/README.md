Double parser
=============

Haskell
-------
* Fordítás és futtatás:
    $ make
* A nyelv alapegységeiből építkeztem, ezért is lett egyszerű és rövid a megvalósítás.
* Nem is igazi FSM, függvény túlterhelésen alapul.
* Van egy erősen típusos állapot.
* Az isDigit könyvtári függvényt használtam fel, de ez is helyettesíthető lenne egy sajáttal.
* A hibás eredményeknél kivétel keletkezik, melyet elkapva az addig akkumulált részeredmény eldobódik.
* Nehéz rájönni a sok függvény overload közül, hogy melyik rontja el az eredényt, fejben kell végigjárni az utakat. Emiatt nehéz a hibakeresés.
* Két új állpot bevezetésével megoldható volt az implementálás.
* Könnyű volt bővíteni a meglévő kódot, nem volt szükség átstrukturálásra.

Erlang
------
* Fordítás és futtatás:
    $ make
* Az stdlib-ben lévő gen_fsm behaviourt használtam fel, mellyel állapotgépek készíŧhetők.
* Külön folyamatként fut az FSM.
* A kód jelentős része ahhoz kell, hogy az infrastruktúra működjön.
* Kicsit összefolyik az FSM és a modul interfész, pl. parse_rec függvény jön rá arra, hogy sikerült-e a beolvasás vagy sem, de ez már nem az FSM processzében fut.
* Mivel nem az fsm processz dolgozza fel közvetlenül az inputot, csak karaketereket kap egyesével, ezért ha egy terminális állapotban elfogynak a karaketerk, akkor kívülről kell leállítani.
* Nincsen mindegyik gen_fsm callback implementálva, csak a legszükségesebbek.
* Két új függvény felvételével sikerült megoldani a bővítést - mivel az állapotokat különböző függvények reprezentálják.
* Szintén nem kellett átstrukturálni, átírni a kódot.

KD1OUR két megolásánal módosítása
=================================

Java
----
* Teljes állapotgép implementáció.
* Elegendő volt az új állapotokat kibővíteni, a terminális szimbólumokat és a következtetési szabályokat módosítani.
* Az előjel kiírásához bele kellett nyűlni más részekbe is, a kimeneti karaktersorozatot nem az állapotgép állítja elő, hanem külön tevődik össze, az állapotgép állapota alapján.
* A végén az új terminális állapot elfogadásához az állapotgépet vezérlő kódba is bele kellett nyúlni.

Scala
-----
* Hasonló megoldás, mint a java-s, emiatt ugyanazok az állítások igazak itt is.
* Ugyanazon az osztályok és enumok is megjelennek benne, emiatt nagyjából ugyanazt kellett implementálni, mint a java-sban.
* Az előjelek levétele máshogy működik, ezért itt nem is csináltam meg azt, hogy az új kifejezéseknél a '+'-t levegye.
* Tömörebb lett ez a megvalósítás, mint a java-s, ez a nyelv sajátosságainak köszönhető.
