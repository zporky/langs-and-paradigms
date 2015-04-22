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
