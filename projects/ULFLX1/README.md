Double parser
=============

Haskell
-------
* Fordítás és futtatás:
    $ make
* A nyelv alapegységeiből építkeztem, ezért is lett egyszerű és rövid a megvalósítás
* Nem is igazi FSM, függvény túlterhelésen alapul
* Van egy erősen típusos állapot
* Egyedül az isDigit könyvtári függvényt használtam el, de ez is helyettesíthető lenne egy sajáttal.
* Nehéz rájönni a sok függvény overload közül, hogy melyik rontja el az eredényt, nehéz a hibakeresés

Erlang
------
* Fordítás és futtatás:
    $ make
* Az stdlib-ben lévő gen_fsm behaviourt használtam fel, mellyel állapotgépek készíŧhetők
* Külön processzként fut az FSM
* A kód jelentős része ahhoz kell, hogy az infrastruktúra működjön
* Kicsit összefolyik az FSM és a modul interfész, pl. parse_rec függvény jön rá arra, hogy sikerült-e a beolvasás vagy sem, de ez már nem az FSM processzében fut
* Mivel nem az fsm processz dolgozza fel közvetlenül az inputot, csak karaketereket kap egyesével, ezért ha egy terminális állapotban elfogynak a karaketerk, akkor kívülről kell leállítani
* Nincsen mindegyik gen_fsm callback implementálva, csak a legszükségesebbek
