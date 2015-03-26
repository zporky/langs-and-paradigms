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
    hamarabb célt lehet írni, de most direkt az állapotgépes változatot csináltam meg.
    A perl alapvetően nem egy objektum orientált nyelv, de van néhány eszköz, aminek a segítségével
    objektumorientált gondolkozással lehet programozni benne. A láthatóság gyakorlatilag nem létezik
    benne, ezért bárki, bármit, bárhonnan elérhet. A tesztelés esetén ez könnyebség, mert a
    tesztprogramok bele tudnak nézni a tesztelt objektumokba.
    
    A program megírásakor törekedtem arra, hogy a forráskód egy C++, vagy Java programozó számára is
    érthető legyen. Nem használtam a perl-nek a rövidítéseit, és a beépített változóit. Használtam a
    "use strict" direktívát, hogy az explicit nem deklarált változók esetén szóljon az interpreter.
    
    Egy főprogramból és egy StateMachine oszályból áll a program. A logika gyakorlatilag a
    StateMachine osztályban lett megvalósítva.
    
    A tesztprogram megírása és a program hiáinak javítása (nem volt túl sok), legalább annyi ideig
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
    hamarabb lehet célt ezzel a nyelvvel, mint bármelyik iteratívval. A típusok nagyon sokat segítenek
    a programozás során, gyakorlatilag felesleges a kommentelés, mert olvasható marad a kód. Csak oda
    kell komment, ahol valami trükközést kell csinálni. Van egy ilyen a kódban.
    
    Véleményem szerint ezt a kódot jobban lehet olvasni, de az tény, hogy kell érteni a típusok és a
    matematika nyelvén. Aki nem tud absztrakt függvényekben gondolkodni, az sosem fog tudni ebben a
    nyelvben programozni.
    
    Amikor programozok, vagy programokat tervezek, akkor mindig kívülről szoktam befele haladni, azaz
    először a fő problémát fogalmazom meg absztrakt függvényekkel, majd az absztrakt függvényeket
    kezdem hasonló módon megoldani, és így haladok egyre beljebb és beljebb. Ennek a gondolkodásnak
    ez a nyelv nagyon megfelel, és ha minden függvénynek megadom a típusát, akkor minden úgy adja magát.
    
Konklúzió
---------
    Perlben ~300 sor a program dokumentáció és teszt modul nélkül, míg Haskellben megírva ugyanez 140 sor.
    Időben is kevesebb ideig tartott a Haskell változatot megírni, még úgy is, hogy nem vagyok gyakorlott
    Haskell programozó. A Haskell verzió kommentek nélkül is olvasható és áttekinthető, míg a perl.
    A karakter feldolgozó függvény összerendelését perl-ben is meg lehetett volna csinálni úgy hogy a
    feldolgozó eljárásokra mutató pointereket teszem a tömbbe, de akkor ott ez nem jutott eszembe. Illetve
    iteratív környezetben ez rendszerint veszélyes, illetve nehezebb olvasni a kódot is. Talán ezért nem
    gondoltam erre akkor. :-)
