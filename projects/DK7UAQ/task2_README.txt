Varga Viktor - DK7UAQ, Prognyelvek és paradigmák 2. hf., BÕVÍTÉS

Java - tapasztalt, Haskell - kevésbé tapasztalt

Minden bõvítésnél a task2_bovites.png automatát valósítom meg. Ez az elsõ feladat 
automatájára épül, ugyanolyan float számokat ismer fel, mint az (azaz pl. a törtrészben nem
lehet 0 és az egészrészben is csak az elsõ számjegy lehet), plusz az opcionális 
exponens tag. Az exponens tag kiírásakor szigorúan van elõjel. Az exponens tagban 
található szám egész és nem kezdõdhet nullával (de lehet benne nulla).

Az eredeti feladat, illetve a bõvítések során számomra a legnagyobb gondolkodnivalót az adta,
hogy hogyan egyeztessem össze az automatát a float számot elõállító parser függvénnyel.
Az automatát szimuláló kódot önmagában könnyû megvalósítani, illetve egy float parser 
algoritmust sem nehéz írni, de a kettõ ötvözete nem igazán szép.
Az tûnik értelemszerû megoldásnak, ha az outputot elõállító függvényt darabokra szedjük és
az automata egyes átmeneteire osztjuk le valahogy. Bence (EJULOK) Java kódja valami ilyesmit
valósít meg. Bár ez a megoldás nagyon szép, de a karbantartása, megértése jóval nehezebb, mint
a többi kódé és a float-parser specifikus kód elég hosszú.
Az én kódjaimban kicsit neccesnek érzem az outputot elõállító függvény elhelyezését. Nálam
ugyanis ez a függvény nincs szétszedve. A Java kódomban jól néz ki, hogy egy általános automata
csomagot terjesztek ki, amiben elhelyezésre kerül a float outputot elõállító függvény, de
úgy érzem, mintha maga a leszármazott osztály (ami tartalmazza a float specifikus dolgokat)
alig lenne kisebb, mint egy olyan osztály ami csak pusztán megvalósítaná a feladatot mindenféle
általánosságra való tekintet nélkül. A Haskell kódomban is hasonlóan problémásnak érzem az
outputot elkészítõ függvényeket, hiszen a bõvítés után ezek összesen közel 20 sort foglalnak,
ami szerintem nem sokkal kevesebb, mint egy bármilyen általánosságot mellõzõ megoldás.

vvinston/EJULOK, Haskell:

Futtatás (Win):

	ghc StateMachine.hs
	StateMachine.exe
	<lehet számokat írni>

Bár nem túlzottan általános megvalósítás, de logikusan felépített. Ezért is könnyû volt kibõvíteni,
kb. 20 percbe került.

vvinston/EJULOK, Java:

Bonyolult, valószínûleg elég általános megoldás. 
Tökéletes példája az egy-két soros metódusok irányzatnak, ennél több osztályra szétszedni
valószínûleg nem is lehetne a kódot.
Az, hogy a parse-olás különbözõ mechanizmusaira mind egy-egy feltétel, akció, stb. osztályt 
származtat érdekes, szép, de a bõvítéshez szükséges volt ezeket mind átnéznem és kitalálnom,
hogy legózzam össze a létezõkbõl a kívánt funkcionalitást (2 osztályt még hozzá is adtam).
Mivel én az eredeti feladatot is kissé máshogy értelmeztem, így tovább tartott a bõvítés és 
körülbelül 90 perc alatt lett kész.

saját, Haskell:

Futtatás (Win):

	ghc pny2.hs
	pny2.exe
	<lehet számokat írni>

A módosítás kb. 20 percbe telt.
Az általános automata futtató mechanizmust nem kellett módosítani.
Az új állapotokat és szabályokat kellett hozzáadni: kb. 10 sor.
A floatParser és finalizeParsed függvényekben néhány sort kellett átírni.

saját, Java:

Futtatás (Win):

	mkdir bin
	javac -d bin *.java fsm\*.java
	java -cp bin pny2.Pny2
	<lehet számokat írni>

Csak az inicializáláson és a FloatParser.java-n kellett változtatni, az általános automatát 
megvalósító csomagon egyáltalán nem. Kb. 10 percbe került.
