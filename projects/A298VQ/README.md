# task2

A feladat megoldásához három új állapotot határoztam meg:
 - E0 : nem elfogadó,
 - E1: nem elfogadó,
 - T6: elfogadó,

Új szimbólum csoport:
 - e: {e,E}
 
Az átmenetek az alábbiakban módosultak:
- T(1-5) -> eE0  átmenettel egészült ki
- E0    -> dT6 | sE1 | e
- E1    -> dT6 | e
- T6     -> dT6 | e

### Tapasztalatok:

A korábbi megoldásom miatt mind a perl, mind a java kódomba a három új állapotot reprezentáól függvényt kellet megírnom. Ezen felül plusz elágazásokkal kellett kiegészítenem a korábbi (T1 - T5 állapotokat reprezentáló) függvényeimet.
A meglévő tesztjeimet kiegészítettem űj tesztesetekkel is, viszonylag gyorsan el tudtam készülni.

Idegen kódnak RYRACR megoldásait választottam részben a Java miatt, részben mert mindig is érdekelt a Python.
Nagyon szép megoldás mindkettő, jól olvasható és könnyen bővíthető. Így még gyorsabban el is tudtam készülni, mint a saját kódom átírásával.

# task1

### Perl:

 - A feladatot objektum orientáltan valósítottam meg, amennyire a perl nyelvi elemei ezt lehetővé tették.
 - A validálást az automatát reprezentáló objektum rekurzív függvényhívással hajtja végre, melynek végkimenete egy string lesz.
 - A visszaadott string nem elfogadott bemenet esetén FAIL egyébként OK és a float értéke 
 - A szimbólumok reguláris kifejezésként vannak eltárolva.
 - Az automata függvényei egy-egy állapotnak felelnek meg és minden végrehajtáskor a bemeneti stringből elveszi az aktuális első karaktert és a szabályok szerint ellenőrzi.
 
### Java:

 - A java-s megoldásom nagyon hasonló a perl-ös implementációhoz képest.
 - Itt a rekurzív függvény hívás egy bool értéket ad vissza és az eredményt egy változóba tárolja el az objektum.
 - Ebben a változatban a bemeneti string vágása helyett, egy integerben tárolom az éppen ellenőrizendő karakter pozícióját.
 
### Tapasztalatok:

- Egyik változat sem okozott nehézséget, mindkét esetben írtam teszteket, amiket az implementálás során alakítottam és javítottam.
- A perl implementálása igényelt több időt a kezdeti ötlet és tesztesetek kidolgozása miatt, amiket aztán később javaban újra tudtam hasznosítani
- A Java nyelvi elemeinek megfelelő kihasználásával szebb megoldást is lehet írni a perl-ös változathoz képest .
