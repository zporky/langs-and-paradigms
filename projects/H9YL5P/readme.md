Név: Molnár András
Neptun: H9YL5P
github: andras1molnar

###C++ megoldás
Az első megoldást C++ -ban készítettem, és bár nem mondanám profinak magam a nyelvben, de sok szempontból a "legalkalmasabb" választás a feladat megoldására. A tervezésnél megpróbáltam figyelni az OOP és az STL használatára, szem előtt tartva a kód átláthatóságát. Egy egyszerű megoldásra törekedtem, ami pontosan a feladatot oldja meg.

####Fordítás
- a make utasítást kell kiadni a shellben (bash).

###Java megoldás
Második nyelvnek Java-t választottam, mert ebben a nyelvben még kevésbé vagyok jártas, és kíváncsi voltam, hogy mennyire különbözne a megoldás (annak tudatában, hogy a két nyelv nagyon hasonló). Java esetén szerintem jobban érvényesült az objektum orientált tervezés, ennek ellenére meglepődtem, hogy mennyire különbözik a C++ -tól. (például a C++ -ban az állapotokat névtérként definiáltam, míg Java-ban erre a célra jobbnak láttam egy osztályt létrehozni, ahol a private konstruktor gondoskodik arról, hogy az osztályt ne lehessen példányosítani)
Java esetén nagy segítséget jelentett még az Eclipse fejlesztőkörnyezet támogatása a fejlesztés során.

####Fordítás
- előtte létre kell hozni egy bin almappát
- a javac -d bin *.java paranccsot kiadva
- futtatás a bin mappában a finiteStateMachine.App utasítással
