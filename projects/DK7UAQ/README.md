Varga Viktor - DK7UAQ, Prognyelvek és paradigmák 2. hf.

A feladat nem egészen derült ki egyértelműen számomra a task1.pdf-ből.
Az ábrán felrajzolt automatával ekvivalens automatát valósítok meg, azaz a tizedespont után 0 karakterek pl. nem engedélyezettek.
Kiírásnál a "0" inputra "0"-t adok vissza, egyébként ragaszkodom ahhoz az alakhoz, ahol a negatív előjel megjelenik, illetve
	tizedespont mindig van és annak mindkét oldalán van legalább 1-1 számjegy.
Az automata kicsit át van alakítva, hogy az epszilon átmeneteket elkerülje.
Mindkét program általánosabban került megvalósításra, mint a feladatban leírt speciális eset.

************ Java ************

Futtatás (Win):

	mkdir bin
	javac -d bin *.java fsm\*.java
	java -cp bin pny2.Pny2
	<lehet számokat írni>

Leírás:

	Java-ban tapasztaltabb vagyok.
	
	A Java kód a következőképpen épül fel:
	Az fsm csomagban találhatóak az FSM, State, Rule, Rule.RHS osztályok.
	Az FSM egy általános determinisztikus automatát valósít meg, aminek az átmenetei véges hosszú String-ek.
	Az automata eredménye egy boolean, ami jelzi, hogy az automata elfogadta-e az inputot, vagy nem.
	A State az állapot, a Rule az átmeneti szabály reprezentációja.
	A szabályokban egy baloldal (kiinduló állapot) és több jobboldal (RHS: célállapot és az átmenet közben felismert input) engedélyezett.
	A FloatParser az FSM kiterjesztése ami a szabály-illesztések közben összerakja a kimeneti szöveget is, amiből végül a createFloat() metódus
		hívásakor elkészíti a kiírandó sztringet.
	A Pny2 a main class ami inicializálja és futtatja az automatát.


************ Haskell ************

Futtatás (Win):

	ghc pny2.hs
	pny2.exe
	<lehet számokat írni>

Leírás:

	Haskellben az összes tapasztalatom a Bsc-s funkcprog1-ből, illetve az Msc-s funkcprog1 Clean feladataiból ered.

	A Haskell kód egy fájlból áll.
	A kód, egy a Java megvalósítással megegyező erejű determinisztikus automatát valósít meg, aminek az átmenetei véges hosszú String-ek.
	A kiírandó szöveg előállítása itt paraméterezhető: az automata egy Parser = String -> State -> String -> String típusú
		függvényt használ fel az átmenetek során, aminek paraméterei: 
		<az eddig elkészült kiírandó szövegrészlet> <az átmenet kiinduló állapota> <az átmenet során felismert terminális>
		-> <az ezekből elkészített kiírandó szövegrészlet>
	Az elemzés végeztével a speciális esetek kezelésére szükség van még egy függvényre ami módosíthatja a kiírandó szöveget ha szükséges.
	Az átláthatóság kedvéért még a Terminals és RHSS szinonímák, illetve a State, Rule, RHS, FSM algebrai adattípusok lettek definiálva,
		ezek jelentése hasonló a Java megoldásban szereplő osztályokéhoz.
	
	El tudom képzelni, hogy több monáddal egyszerűbb/rövidebb/szebb lenne a kód, de azokhoz egyelőre még nem értek igazán.
	
