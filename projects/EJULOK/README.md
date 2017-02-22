#Tapasztalatok

##Saját programok

###Java
Az első feladat készítésekor készültem a bővíthetőségre, és igyekeztem újrafelhasználható, konfigurálható elemekből felépíteni. Ez úgy érzem meghálálta magát, mert most egy apró logikai hiányosságot leszámítva (hiányzó ellenőrzés az egyik feltételben), csakis az elemek összekomponálását kellett bővíteni a builder osztályban.
Ezen túl a TDD megint működött, még talán jobban mint a zöldmezős feladatban, így nem féltem kipróbálni pár dolgot, illetve változtatni az osztálypéldányok összefűzésén. Egy apró csúsztatás: csak application test van, mint TDD, mindenre kiterjedő unit test nincs, de a fenti megállapítások e mellett is tarthatóak egy ekkora feladatnál.

###Haskell
Talán már kicsit közelebbi barátságban vagyunk, bár nem állítom, hogy teljes mértékben uralom a nyelvet. Ugyan felmerült bennem a monádok használata, vagy komplexebb nyelvi struktúrák bevezetése, némi próbálkozás után úgy döntöttem, hogy továbbra is maradok az egyszerű konstrukcióknál, a másik út sok rizikót hozott volna be. Végül ezzel a módszerrel, a Java projektnél felismert bővített állapotgépet (T1..T5 --> E, E --> T6, T6 --> T6 került be) mintegy szolgai módon implementáltam a már megkezdett módon. A TDD jellegű megoldást itt is követtem (itt külön, mert először egyátalán nem voltam biztos, hogy a szerteágazó feltételrendszereket jól raktam e össze), elég megnyugtató volt látni, hogy hamar sikerrel futott a tesztek nagy része. Nem volt minden elsőre jó, de rövid keresgetéssel megvolt az elcsúszás. A gyorsabb ellenőrzésre bevezettem az isGood függvényt, mely akkor tér vissza igazzal, ha minden teszt jól fut le.

##Mások programjai

##Java KD1OUR
Első lépésként maven projektté tettem és átmásoltam a saját megoldásomból az egységtesztet. Ezt kicsit átírva egyszerűen kézben tudtam tartani a változtatásaim okozta hatásokat. A legtöbb dolog egyszerűen ment, az állapotgéphez szükséges lépéseket könnyen fel lehetett venni: kevés osztály lévén könnyen áttkinthető volt. A tesztek nagy része így hamar zöld lett, a maradékon kellett még dolgozni, ahhoz magában a StateMachine osztályban kellett néhány speciális esetet lekezelő ágat felvegyek. Lévén ez a projekt Java nyelvű, ezért a kódban otthonosan mozogtam, ahol nem, ott az eszközismeretemet használva (maven és junit tesztek) megteremtettem a módosításokhoz szükséges önbizalmat.

##Haskell UDB51N
Elsőre kicsit ilyesztő volt számomra a monádok használata, és a bonyolultnak látszó processzáló kód. Második nézésre kiderült, hogy a felépítés ésszerű, és nekem a futtató kódon nem (vagyis minimálisan) kellett változtatni, és csak az állapotokat kellett újradrótozni. Ez egy minimális kutatást igényelt, a state 0-8 (majd 0-10) elnevezéseket meg kellett feleltessem a dokumentációban megadottal, de ezek után már tényleg csak tranzíciókat kellett felvegyek, szóval a kezdeti ijedtség után örömjáték volt. Az egyetlen, amit hiányoltam, hogy nem tudtam a saját megoldásomhoz hasonlóan TDD jelleggel autotesztelni. Ezt persze megoldottam, ad hoc, de így több idő volt. Nem fejlesztettem bele újabb monádos megoldást, ami ezt automatizáltan megoldja, annyira még nem kontrollálom a nyelvet.
