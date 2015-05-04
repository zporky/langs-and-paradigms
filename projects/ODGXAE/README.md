### Task 1

#### Haskell
---

##### Futtatás

`runghc FloatParse.hs` vagy `ghci FloatParse.hs`. Az egyetlen `base`-en kívüli függőség az `mtl`;
ha ez hiányzik, akkor `cabal install mtl`-el telepíthető.

##### Megvalósítás

Haskell-ben van jelentős tapasztalatom. 

Haskell-ben, ha parse-olni kell, akkor mindenki valamilyen parser kombinátorhoz nyúl először. A jelenlegi feladatban
nem lehet külső parser library-t használni; viszont ezzel együtt is rendkívül egyszerű minimális parser monádot 
csinálni kézzel. Lényegében a következővel kész is vagyunk:

```haskell
{-# LANGUAGE LambdaCase #-}
import Control.Monad.State
import Control.Applicative

type Parser = StateT String Maybe

-- Parse next char if it satisfies a predicate
sat :: (Char -> Bool) -> Parser Char
sat p = get >>= \case c:cs | p c -> c <$ put cs; _ -> empty

-- Parse end of file
eof :: Parser ()
eof = get >>= \case [] -> pure (); _ -> empty
```

A meglévő `Applicative`, `Alternative` és `Monad` instance-ok szemantikája megfelelő a feladatra; `<|>` segítségével
választani tudunk két parser között, `empty`-vel hibával kilépünk a jelenlegi parserből, a `Monad` és
`Applicative` operátorok pedig a megszokott módon működnek. A szemantikában talán az a szépséghiba, hogy a `<|>`
választás visszalépéses, míg a véges determinisztikus automaták leírásához elég a visszalépés nélküli választás. 
Mindenestre én úgy írtam meg a feladathoz a parsereket, hogy ne legyen benne visszalépés, azaz a következő
karakter kiolvasásával mindig eldönthető, hogy melyik parsert választjuk. 

Első ránézésre lehetne azt mondani, hogy csaltam, hiszen mégiscsak importáltam parser library-t. Én erre azt válaszolnám, hogy
a `Control.Monad.State` modulban szó sincs parse-olásról, és egyébként is nagyjából 15-sornyi kóddal kiváltható lenne
ez az import (`StateT` definíció, `Monad` + `Alternative` instance). 


#### Clojure
---

Ez lenne a kevéssé ismert nyelv. 

Először úgy terveztem, hogy a J progrmozási nyelven valósítanám meg a feladatot,
mivel ez a stílus (APL nyelvcsalád) olyan, ahol valóban semmilyen tapasztalatom nincs, és érdekelt is. Viszont miután
felületesen végigszkenneltem az online elérhető könyveket, és elkezdtem próbálkozni REPL-ben, inkább bedobtam a törülközőt.
Nem lenne *különösen* nagy probléma J-ben megoldani a feladatot, de hogy megközelítőleg is jó megoldás legyen, ahhoz
több gyakorlat kéne, amit nehezemre esik beletenni a feladatba, mivel alapvetően nem nyerte el a nyelv a szimpátiámat.

Kicsit kifejve, hogy miért nem. A J olyan, mintha valaki Huffman-kódolta volna a tömbfeldolgozás során a leggyakoribb
programozási mintákat a lehető legrövidebb operátor-kombinációkra. Ha valaki gyakorlott, akkor így remekül tud kód-golfozni,
viszont én elsősorban a programozási *alapelvek* egyszerűségét preferálom, és azt, ha viszonylag kis számú alapvető
feature-ből lehet jól viselkedő módon építkezni. A J-ben az alapvető szintaxis szintjén össze van mosva az
egyszerű függvényapplikáció és a függvényapplikáció a `((->) a)` monádban (FP terminológiával élve). Hasonlóképpen 
össze vannak mosva az elemenkénti és a tömbönkénti tömb-operációk. Ez addig kényelmes, amíg a megfelelő operátor a
kontextusban azt csinálja, amit szeretnénk; de nem tudunk általánosan váltani a két forma között. Továbbá, túl vannak terhelve 
az operátorok nemcsak aritás szerint, hanem még az *argumentumok aritása* szerint is (!), és elméleti szempontból nem kifejezetten 
konzisztens módon. Ugyanakkor, annak ellenére, hogy a J a "point-free" magasbbrendű függvényhasználatot ösztönzi, 
a magasabbrendű függvények nem is igazán első osztályúak, és a szintaktikai cukor kizárólag az unáris és bináris függvényekre terjed ki. 
Ha esetleg komplexebb, több argumentumos kombinátorokat akarnánk ad-hoc használni, az már elég kényelmetelen. A sztringként
definiált függvényekről nem is beszélve...

Szóval inkább váltottam Clojure-ra. Kb. két évvel ezelőtt néhány száz sor és némi [kód-puzzle](http://www.4clojure.com/)
erejéig foglalkoztam Clojure-al, illetve némi általános Lisp műveltség ozmózisosan ragadt rám innen-onnan.

##### Futtatás

Nem voltam ismerős Clojure fejlesztői környezetkben, így azt választottam, amit a legtöbb basic tutorial tartalmazott.
Installáljuk a [leiningen](http://leiningen.org/)-t, majd `lein run`-al futtathatunk a projekt könyvtárából.

##### Megvalósítás

Megpróbáltam egy-az-egyben átültetni a Haskell megoldást (nem túl brilliáns koncepció, bevallom). Ennek során
minden bizonnyal nem szereztem hanszálható tapasztalatot idiomatikus Clojure programozásról, viszont némi képet
kaptam arról, hogy milyen lehetőséges és korlátok vannak a Clojure funkcionális progrmozásban.

Először abba a problémába ütköztem, hogy nincs parciális applikáció, a meglévő [`partial`](https://clojuredocs.org/clojure.core/partial) nem elég jó, mivel lényegében "egyszer használatos", és
nem sikerült vele olyan kódot produkálnom, ami nem dob "wrong argument number" hibát váratlan helyeken. Elkezdtem keresni
automatikus curry-ző makrókat, és találtam is, viszont úgy tűnik, hogy azt semmiképp sem lehet megoldani, hogy a
függvénnyel visszatérű függvényt ugyanabban az S-kifejezésben applikáljuk (pl: ha  `(f a b)` függvénnyel tér vissza,
akkor `((f a b) c)` -vel kell ezt a függvényt applikálni, pedig jobban szeretnénk az `(f a b c)` formát). Inkább 
hagytam tehát a makrókat, és kézzel curry-ztem mindent beágyazott `fn []` -ekkel. 

További, viszonylag kisebb probléma az volt, hogy nincsen rekurzív `let` a Clojure ban. Rekurzív definíciót lehet
adni függvényekre (kölcsönösen rekurzívat is), de nem lehet értékekre, és a a `fraction1` parser esetén a rekurzív
definíció a kézenfekvő. A probléma orvosolható volt a szoksásos `Y` fixpont kombinátorral. 

A végeredmény ránézésre csúnya lett, habár nem kifejezetten hosszú. Az `((f x) y)` kifejezések miatt elég nehéz volt
formázni és helyesen zárójelezniValószínűleg a megoldás elég lassú is,
és a stack-ből is gyorsan kifutna hosszabb inputokon, mivel nem vég rekurziót alkalmazunk. Idiomatikusabb megoldás
lett volna makrókat használni a monádhoz, pl. létezik egy `clojure.algo.monad` könyvtár.







