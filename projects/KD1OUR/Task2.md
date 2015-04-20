__Név:__ Rónai Péter  
__Neptun:__ KD1OUR  
__GitHub:__ conTroll

A 2. feladathoz elkészített megoldások
======================================

<table>
    <tr>
        <th>Megnevezés</th>
        <th>Hol található?</th>
        <th>Szint</th>
    </tr>
    <tr>
        <td>Saját Java megoldás bővítése</td>
        <td><code>projects/KD1OUR/automata/automata-java</code></td>
        <td>haladó</td>
    </tr>
    <tr>
        <td>Saját Scala megoldás bővítése</td>
        <td><code>projects/KD1OUR/automata/automata-scala</code></td>
        <td>kezdő-közép</td>
    </tr>
    <tr>
        <td>Simon Bence Java megoldásának bővítése</td>
        <td><code>projects/EJULOK/Java/prognyelvek</code></td>
        <td>haladó</td>
    </tr>
    <tr>
        <td>Simon Bence Haskell megoldásának bővítése</td>
        <td><code>projects/EJULOK/Haskell</code></td>
        <td>kezdő</td>
    </tr>
</table>

Az átalakítás során szerzett tapasztalatok
==========================================

1. Saját megoldások
-------------------

<h3>1. 1. Felépítés</h3>

Saját megoldásaim két lényegi részből állnak, egyrészt magából az automatából, amely egy inputról eldönti, hogy elfogadja-e, vagy sem, illetve egy _pretty print_ funkcióból, amely - nagyon magas szinten - a nem _szép_, de helyes inputokat alakítja át megfelelően formázott lebegőpontos számmá.

A bővítésénél nyilvánvalóan előnyben voltam, hiszen ismertem a kódot. Az automata bővítését hamar sikerült elvégezni mindkét megoldásnál, viszont mivel a _pretty print_ funkciót a főprogramba írtam bele az egyes konkrét eseteket szétbontva (automata állapottól függően), így ennek bővítése során kellett változásokat eszközölni a főprogramban (`main`). Nem volt vészes a dolog (nagyjából 8-10 új sorról van szó), de azért úgy érzem ezen a ponton a programom nem volt elég rugalmas.

<h3>1. 2. Automata logikájának bővítése</h3>

A két program bővítése hasonló módon történt, először az új token típust kellett felvenni a megfelelő felsorolásos típusba (`E`), majd ezután rövid átgondolást követően a kapott dokumentumon levő ábrán kibővítettem az automata működését. Ehhez szükség volt egy új állapot bevezetésére. Ebben az állapotban akkor lépünk be, amikor egy elfogadó állapotra `E` token érkezik. Ilyenkor egy __illegális állapot__ba kerülünk, amelyből kétféleképpen juthatunk tovább:
 - Egyrészt előjel (`SIGN`) esetén az eddig meglevő illegális állapotba jutunk, amelyből `DIGIT` hatására elfogadó állapotba kerülünk, és további `DIGIT`-ek hatására is ebben az állapotban maradunk, bármely más tokenre elutasítjuk az inputot. 
 - A másik lehetőség, ha egyből egy `DIGIT` következik, ekkor egyből az előbb említett elfogadó állapotba jutunk.

A bővítés tehát az alábbi elemi lépésekre bontható le:  

1. Új token felvétele: `E`
2. Új állapot felvétele: `I_1` (nem elfogadó)
3. Az eddigi elfogadó állapotokból átmenet az újonnan felvett `I_1` állapotba `E` token érkezése esetén.
4. Új szabály felvétele az új állapothoz (`I_1`): előjel (`SIGN`) esetén átlépünk az eddig meglevő nem elfogadó (`I_0`) állapotba.
5. Új szabály felvétele az új állapothoz  (`I_1`): számjegy (`DIGIT`) esetén átlépünk a `T_1` (meglévő elfogadó) állapotba, amely további számjegyek előfordulását engedi meg.


2. Másik hallgató (_EJULOK_) megoldásai
-------------------------------------

A hallgató megoldásai szépen felépítettek. 

A Haskell kód - tesztesetek nélkül - még a bővítést követően is mindössze 130 soros lett. Amelett, hogy a megoldás tömör, átlátható és nagyon egyszerűen bővíthető, a funkcionális nyelv előnyei - a saját Scala-s megoldásomhoz hasonlóan - itt is előjöttek. Bátran kijelenthető, hogy a feladat jellege passzol a funkcionális modellre.

A Java kódon látszik, hogy ez a kolléga haladó nyelve. A megoldás robosztus, moduláris, és minden aspektusa minimális erőfeszítéssel bővíthető. A saját megoldásomnál említett problémakörre (_pretty print_) a hallgató `action`-öket definiált, melyek megmondják, hogy egy adott állapotátmenet hatására milyen változtatás menjen végbe az output-on.

A logika bővítését tekitve ugyanazokat a lépéseket végeztem el az itt található mindkét megoldás esetében is.

3. Tesztelés
------------

Alapvetően a kiadott feladatlapon található teszteseteket használtam fel annak tesztelésére, hogy az eddigi funkcionalitás nem romlott-e el.

Ezen kívül olyan inputokat teszteltem, melyekben e, vagy E karakter után:
- számjegyek szerepelnek (helyes)
- előjel szerepel (helytelen)
- előjelt követően egy, vagy több számjegy következik (helyes)
- semmi nem következik (helytelen)
- betű, vagy pont következik (helytelen)
- számjegyek között, után, vagy előtt pont, vagy betű következik (mindegyik helytelen)
- kettő, vagy több előjelt követő számjegyek következnek (helytelen) 