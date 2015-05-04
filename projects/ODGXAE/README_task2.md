### Task 2
----
#### Saját kód bővítése

A bővítést a következőképpen értelmeztem: a kitevőben az előjel opcionális, a kitevő nem kezdődhet nullával, legalább
egy számjegyet tartalmazia kell, és csak egész lehet (azaz nincs tizedespont a kitevőben). 

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



