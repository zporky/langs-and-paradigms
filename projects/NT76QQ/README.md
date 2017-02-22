===============================
= TASK2                       =
===============================

A feladat specifikációját (állapotgépet) javítottam. Ehhez készítettem egy kis grafikát is, ami az
az új állapotgép állapotatit és átmeneteit mutatja be. (Floating_numbers_validation.gif) Illetve
annyit módosítottam még a specifikáción, hogy a negatív számok esetén a szám előjelét nem nyeli le
a program. (lásd: test_result.txt)


Saját Perl verzió
-----------------
A programot nagyon könnyű volt módosítani. Ami a legtöbb időt elvette, az az, hogy a tesztprogramot
több helyen kellett módosítani, mint az eredeti programot, ugyanis az új állapotok és az új átmenetek
új tesztesetekkel jártak.


Saját Hasekll verzió
--------------------
A programot itt is könyű volt módosítani, mivel itt nem írtam teszt programot, ezért ezzel nem egész fél
óra alatt el is készültem. Ha előre felírtam volna, hogy melyik részekbe kell belenyúlni, akkor
valószínűleg hiba nélkül ment volna egyből. Azért kellett csak javítani, mert az átmeneteket megvalósító
függvényeket néhol elfelejtettem módosítani. De még így is hamarabb meg voltam vele mint a perl verzióval.
Ez azért elég komoly érv a funkcionális nyelvek mellett.


A298VQ Perl verzió
------------------
Viszonylag könnyű volt a módosítás. Az én változatomhoz képest annyiban volt más, hogy itt nem az
átmenetek lettek függvényként megvalósítva, hanem az állapotok. Azaz minden állapotfüggvény azt csinálta,
hogy olvassa az inputból a következő karaktert, és annak eredményeképp hív meg egy másik
állapotfüggvényt. A kódnak ez a része jól érthető volt, gyakorlatilag csak fel kellett vennem az
új állapotokhoz tartozó eljárásokat, és mivel a "0"-ás bug-ot, ami az ereedeti speckóban benne volt, azt
itt is javítani kellett, ami szintén könnyen ment. Kb. háromnegyed óra volt a módosítást elvágezni.


A298VQ Java verzió
------------------
Felvettem az új terminális szimbólumhoz tartozó symbol_e változót, illetve az új állapotokhoz tartozó
eljárásokat a meglévőek alapján, illetve kiegészítettem a meglévőeket azzal, hogy a 0-t is fogadják el.
Na azt hittem, hogy kész is vagyok, de itt jött a fekete leves több órás nyomozás és kb. 20 perc
debugolást követően két hibába is beleestem, ami azért volt bosszantó, mert a perl változat gyakolratilag
ment egyből.
A symbol_s mintával, és a sate_start() függvénnyel akadtak gondjaim. Ezek ugyanis másképp működtek, mint
a többi hasonló minta és eljárás. A symbol_s-t gondolkodás nélkül felhasználtam a state_i_1() függvényben.
A probléma az volt, hogy a symbol_s-ben nem egy karakterre illesztés volt, hanem kettőre. Ez a kód
böngészése közben nem szúrt szemet, így ezt csak debugolásnál láttam, hogy az illesztés az "e" után nem
jó. Ugyanis a lemásolt függvények úgy dolgoztak, hogy a input stringből vették a következő karaktert, és
azt egy változóba bemásolták, majd ezt a változót vizsgálták, hogy milyen karakter is van benne. Így az
illesztésem nem volt jó. Megörültem, hogy megtaláltam a hibát, és gyorsan javítottam a symbol_s-t. De még
mindig volt hiba.
A következő problémára szintén csak debugolással jöttem rá, mert nem láttam a fától az erdőt.
A state_start függvény a symbol_s-t nem az első karakterre próbálta illeszteni, mint a többi állapothoz
tartozó függvény, hanem az egész inputstringre.
Mivel nem láttam a fától az erdőt így kénytelen voltam a debuggolni a JUnit-os eljárást ismét. Ez alatt
szembesültem azzal, hogy a javított state_start függvényben az if-ben nem s-t írtam, hanem a felette lévő
eredeti if-ben lévő inputStr-t. Erre pedig az "-"-szal történő egyenlőség vizsgálat nem működött. Itt
lettem mérges, és ezt az eljárást is átírtam olyanra, mint a többit. Tudom, hogy így kicsit lassabb lett
a kód, de legalább könnyű javítani. :-)
Összeségében több mint 2, de talán 3 órát is elszüttyögtem ezzel. :-(


UDB51N Haskell verzió
---------------------
Mivel az A298VQ által megvalósított programozási nyelveket viszonylag jól ismerem, ezért kerestem egy
haskell-es változatot is.

Alapvetően tetszett a kód, bár azt nem értettem, hogy az állapotoknak miért sima egész számok feleltek meg.
Ez a sebességen talán javít (bár nem hiszem), de nehezen olvasható, és értelmezhető a kód. Ezért, hogy a
saját dolgomat megkönnyítsem felvettem olyan konstans függvényeket, amelyek ezeket a számokat adják vissza.
Így egy kicsit olvashatób lett a kód. Illetve nekem nem kellett megjegyeznem, hogy melyik állapotnak melyik
szám felel meg.
Az állapotok számokkal történő reprezentálása azért sem jó megoldás szerintem, mert így nincs kihasználva
a haskell szigorú típusossága, így kódmódosítás során könnyen lehet hibázni. Kicsit tovább tartott ezt a
kódot módosítani, mint a többit, mert nem csak a számokat kellett kibogóznom, hogy melyik melyik állapotot
reprezentálja, de a függvényeket is meg kellett értenem, ugyanis a kiírt eredményekbe is bele kellett
módosítani.
Ha eltekintek az állapotok típusától, az algoritmus frappánsabb a sajátomnál, bár a listák használata itt
sem a sebesség záloga, már csak azért sem mivel sok helyen van használva a ++ és a last függvény.


===============================
= TASK1                       =
===============================
A feadat megoldására a perl-t mint ismert nyelvet és a haskell-t választottam, amivel most ismerkedem.
Azért is választottam a perl-t, mert régen sok webes alkalmazást és apró pici scripteket írtam benne,
és úgy gondoltam, hogy Java változatból úgy is lesz egy csomó.

A specifikáció szerint csináltam meg a programokat. Tehát a specifikációban lévő hibás feladat lett
megvalósítva. Például a 0 csak a szám elején lehet. Ezen azért nem akadtam fenn, mert azt hittem,
hogy a feladat az lesz, hogy ezeket a hibákat kell majd javítani. :-)
Mindkét programban gyakorlatilag ugyanazt az algoritmust valósítottam meg, ezért még a nevek is
majdnem ugyanazok.

Perl verzió
-----------
    Objektum orientáltan lett a feladat megvalósítva. Néhány regulásris kifejezéssel ebben a nyelvben
    hamarabb célt lehet volna írni, de most direkt az állapotgépes változatot csináltam meg.
    A perl alapvetően nem egy objektum orientált nyelv, de van néhány eszköz, aminek a segítségével
    objektumorientált gondolkozással lehet programozni benne. A láthatóság gyakorlatilag nem létezik
    benne, ezért bárki, bármit, bárhonnan elérhet. A tesztelés esetén ez könnyebség, mert a
    tesztprogramok bele tudnak nézni a tesztelt objektumokba.
    
    A program megírásakor törekedtem arra, hogy a forráskód egy C++, vagy Java programozó számára is
    érthető legyen. Nem használtam a perl-nek a rövidítéseit, és a beépített változóit. Használtam a
    "use strict" direktívát, hogy az explicit nem deklarált változók esetén szóljon az interpreter.
    
    Egy főprogramból és egy StateMachine oszályból áll a program. A logika gyakorlatilag a
    StateMachine osztályban lett megvalósítva.
    
    A tesztprogram megírása és a program hibáinak javítása (nem volt túl sok), legalább annyi ideig
    tartott, mint ameddig a programot elkészítettem. Ez azért annak is betudható, hogy kijöttem a
    gyakorlatból és néhány szintaktikai dolognak utána kellett néznem.
    
Haskell verzió
--------------
    Itt az iskolában kezdtem megismerkedni ezzel a nyelvvel. Ez a második program, amit írtam benne,
    és a monádos hókusz pókusz még nem megy csípőből. Még nem látom át azon függvények típusait. Ennek
    megfelelően nem is használtam, csak a kiíráshoz és az olvasáshoz. Pedig az állapotgéphez nagyon
    adja magát. De ez most nem ijen lett. :-) Bizonyára sok hatékonytalan és nem szép megoldás is van
    benne, de azért szerintem jól olvasható, és érthető.
    
    Viszont a tanulság az, hogy ha az ember agya már rááll erre a gondolkodásmódra, akkor sokkal
    hamarabb lehet célt érni ezzel a nyelvvel, mint bármelyik imperatívval. A típusok nagyon sokat segítenek
    a programozás során, gyakorlatilag felesleges a kommentelés, mert olvasható marad a kód. Csak oda
    kell komment, ahol valami trükközést kell csinálni. Van egy ilyen a kódban.
    
    Véleményem szerint ezt a kódot jobban lehet olvasni, de az tény, hogy kell érteni a típusok és a
    matematika nyelvén. Aki nem tud absztrakt függvényekben gondolkodni, az sosem fog tudni ebben a
    nyelvben programozni. Ellenben Perl vagy más hasonló imperativ nyelvben lehet matematikai tudás
    nélkül is programozni.
    
    Amikor programozok, vagy programokat tervezek, akkor mindig kívülről szoktam befele haladni, azaz
    először a fő problémát fogalmazom meg absztrakt függvényekkel, majd az absztrakt függvényeket
    kezdem hasonló módon megoldani, és így haladok egyre beljebb és beljebb. Ennek a gondolkodásnak
    ez a nyelv nagyon megfelel, és ha minden függvénynek megadom a típusát, akkor minden úgy adja magát.
    
Konklúzió
---------
    Perlben megírva közel 300 sor a program, dokumentáció (az nem is készült el, csak a helye van meg)
    és teszt program nélkül, míg Haskellben megírva ugyanez 140 sor. Időben is kevesebb ideig tartott a
    Haskell változatot megírni, még úgy is, hogy nem vagyok gyakorlott Haskell programozó. Bár itt azért
    az már könnyebbség volt, hogy az algoritmuson már nem kellett agyalni, csak a kódolást kellett megoldani.
    A Haskell verzió kommentek nélkül is olvasható és áttekinthető, míg a perl változatról ez nem mondható el.
    A karakter és a feldolgozó függvény összerendelését perl-ben is meg lehetett volna csinálni úgy, hogy
    a feldolgozó eljárásokra mutató pointereket teszem a tömbbe, de akkor ott ez nem jutott eszembe.
    Illetve Perlben és C++-ban ez rendszerint veszélyes is, ha többen írnak egy programot, illetve
    nehezebb olvasni a kódot is. Talán ezért nem gondoltam erre akkor. :-)
    
Összehasonlítás
----------------
                    | Perl           | Haskell
    ----------------+----------------+----------------
     Kódsorok száma | 308 + 239      | 141
     prg + tesz prg |                |
    ----------------+----------------+----------------
     karakterek     |                |
     száma szóközök | 3547 + 5361    | 3233
     nélkül         |                |
    ----------------+----------------+----------------
     Mi okozott     | Tesztesetek    | Hibakeresés,
     nehézséget?    | megírása +     | debug infók
                    | Javítások,     | kiíratása.
                    | sokat kellett  |
                    | gépelni.       |
    ----------------+----------------+----------------
     Mi volt        | A szintaktikán | Nem kellett
     könnyű         | nem kellett    | tesztprogramot 
                    | gondolkodni.   | írni.
                    | Debuggolni     | Könnyen érthető
                    | egyszerű.      | és olvasható a
                    |                | forráskód
    ----------------+----------------+----------------

