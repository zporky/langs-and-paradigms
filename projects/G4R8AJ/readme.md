# C++
	## Futtatás
		-a fordításhoz g++ szükséges
		-fordítani a g++ main.cpp src/*.cpp include/*.cpp paranccsal lehet
		-utána pedig a kimeneti fájl futtatásával lehet indítani a programot

	## Tervezési döntések
		-Az implementáció során és a fordításhoz is a cél a minél kissebb natív c++ eszközkészlet használata
		-Ezen felül még a átláthatóság és a bővíthetőség volt a kód felépítésénél a vezérelv, ezért ilyen terjedelmes
		-Ennek eredménye az állpotgépet reprezentáló osztály és az állapotokat reprezentáló osztály
		-Az állapotgép csak egy-egy állapot kimenetének feldolgozásáért felel és a következő lépés megtételéért
		-Az adott állapotok pedig felelnek azért, hogy a következő lépést megtegyék
		-Az állapotgépet az őt reprezentáló osztály hajtja meg
		-Hatékonysági megfontolásból mindig csak egy állapotot tárolunk kezdetben és a futás során cseréljük az aktuális állapotot
		-Emiatt különösen oda kellett a memóriaszivárgásra is figyelni és arrra, hogy minden objektum megszűnjön a használatának végén és csak akkor
		
		
	## Felépítés
		-A futást a StateMachine osztály indítja a run fügvénnyel, amelynek paramétere a bemenet
		-Az állapotok( AbstractState leszármazottak ) mondják meg, hogy melyik állapot követi őket a bemenet első eleme alapján
		-Minden állapot következő művelete visszaadja a maradék bemenetet, a jelenleg felismert értéket és a rákövetkező állapotot
		-Ha a rákövetkező null, akkor hiba állapotban vagyunk
		
	## Tapasztalatok
		-Gyorsan fel tudtam egy olyan struktúrát építeni,ami az állapot géphez hasonló( Egy-egy állapot össze lett vonva, mivel a funkcinalításuk hasonló volt)
		-A program megírása után az optimalizálás volt még időigényes
		-Mivel a kimenetet nem tudtam pontosan, így az állapotgépet fentről lefelé építettem fel, minden réteget letesztelve kézzel
		-Egy-két ponton még vannak optimalizálható kódrészletek, de azokat az eredeti állapotukban hagytam a jobb érthetőség miatt 
		-A megoldás legnagyobb hibája talán az, hogy nem csak az objektum megszünésekor szabadítunk fel memóriát, ami ugyan optimálisabb megoldást ad,de stabilitási szempontból komoly veszélyt jelenthet
		
# C#

	## Futtatás
		-Visual Studió szükséges a forrás fordításához
		-Betöltjük a projektet és futtatjuk
	
	## Tapasztalatok
		-Kiemelendő, hogy sokkal biztonságosabb, mint a c++-os megoldás!
		-A felépítést tekintve törekedtem a c++-os megvalósításhoz hasonlóra, hogy látszódjon a különbség és adjon nekem egy "mankót"
		-A főbb különbségek a két implementáció között, hogy itt az állapot tárolja a maradék inputot, a jelenleg összeakkumulált értéket és a következő állapotot
		-Próbáltam nem egy-az-egy ben a c++-t portolni, hanem egy implementációs alternatívát adni a c++-osra a C# nyelv elemeit felhasználva
		-Kevesebb ellenőrzés szükséges,mint a c++-os verzióban, így a forrásfájlok is rövidebbek
		
# C# Második feladat - idegen fejlesztő módosítása, általa kevésbé ismert nyelvben
	## Tapasztalatok
		-Nem meglepődve látom, hogy nincs command line build mellékelve, hanem kötelezve vagyok a teljes Visual Studion használatára
		-Meglepően sok forrásfájl tárult elém a projekt megnyitása után
		-Kód megértése viszonylag egyszerű volt, ám ezt nem a nyelv előnyének tekintem, sokkal inkább a feladat egyszerűségének
		-AbstractState fájl olvashatatlan számomra a formázás miatt. Rengeteg az "üres" sor.
		-Szomorúan tapasztaltam, hogy több új fájlt kell létrehoznom, töbnyire copy-paste kóddal. Ezt nem a nyelv-nek róvom fel, inkább a program tervezésének.
		-Észrevettem, hogy a PointState furán van használva. VisualStudion ismereteim elég csekélyek de így is egy pillanat alatt rá tudtam keresni az őt használó referenciákra. VS egy jó IDE.
		-Hiányoznak a Java-beli konveciók: javadoc style, konvenció, hogy a getter neve azért igyekszik megegyzezni a mező nevével. Ez a hibakeresést mind-mind nehezíti.
```
        public IState Next
        {
            get
            {
                return this.followingState;
            }
            set
            {
                this.followingState = value;
            }
        }
```
		-Az eredeti program szerintem sok helyen hibás. Minimálisan belemódosítottam, hogy értelmesen lehessen implementálni az új funkciót. VS-ben jó debuggolni.