### Task 2

A bővítést a következőképpen értelmeztem: a kitevőben az előjel opcionális, a kitevő nem kezdődhet nullával, legalább
egy számjegyet tartalmazia kell, és csak egész lehet (azaz nincs tizedespont a kitevőben). 

----
#### Saját kód bővítése



##### Haskell

Nem igényelt lényeges erőfeszítést a kiterjesztés itt, a kód sem változott sokat.

##### Clojure

Megint csak a Haskell kódot próbáltam másolni, de itt némi rejtélyes hibába ütköztem: a Haskell megoldásban 
alkalmaztam a `<**>` kombinátort (fordított `Applicative` applikáció), ezért a kombinátor megpróbáltam `revap`
néven implementálni Clojure-ban, de itt runtime-típushibát kaptam. Valószínűleg a hiba orvosolható lett volna,
de én inkább csak átírtam a `pdouble` parser-t explicit `bind`-el, kombinátor nélkül, és így működött, így aztán
többet nem is foglalkoztam az esettel. Az mindenesetre látszik, hogy a Clojure megoldásom nem a legrobusztusabb. 
Statikus ellenőrzés nélkül nagyon könnyű triviális hibákat véteni operácionálisan bonyolultabb kódban (kombinátorok, 
CPS, magasabbrendű függvények, stb...).

----
#### Idegen kód bővítése

##### Java

Java-ban nem programoztam előzőleg, de a nyelvi elemek könnyen értelmezhetők előzetes C/C++/C# ismeretek birtokában.

Lényeges változtatás, amit eszközöltem: elhagytam a `FloatParser` osztályt. A `FloatParser` nem generált tényleges
`Float` értéket, hanem csak az input stringet formázta, a feladatot leaíró pdf-nek megfelelően. Viszonylag bonyolult
volt, mivel az automata lépéseivel párhuzamosan működött. Helyette hozzáadtam egy `formatInput` metódust a `Pny2`
osztályhoz, ami a `FloatParser`-el ekvivalens formázást valósít meg a bővítetlen nyelvtanra. 

A nyelvtan bővítését új szabályok és állapotok  hozzáadásával valósítottam meg. 

##### Haskell

A Haskell megoldást szerkezetileg hasonló a Java-hoz, ezért változatlanul átültettem ide a `formatInput` függvényt. 
Csináltam némi egyszerűsítést és formázást, ill. helyettesítettem néhány definícót meglévő library függvényekkel.
A megoldást szerkezetén nem változtattam lényegesen. Ugyanazokat a szabályokat és állapotokat használtam itt is,
mint Java-ban. 
