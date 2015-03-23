__Név:__ Rónai Péter  
__Neptun:__ KD1OUR  
__GitHub:__ conTroll

Java megoldás
=============
__Követelmény:__ JDK 7, vagy újabb  
A forráskódok az _automata/automata-java/src_ mappában találhatók.  
__Fordítás:__ <code>javac Main.java</code>  
__Futtatás:__ <code>java Main</code>  

Scala megoldás
==============
__Követelmény:__ Scala 2.11  
A forráskódok az _automata/automata-scala/src_ mappában találhatók.  
__Fordítás:__ `scalac *.scala statemachine/scala/model/*.scala statemachine/scala/*.scala`  
__Futtatás:__ <code>scala Main</code>  

Észrevételek
============

Felsorolásos típus
------------------

Több helyen használtam (pl. szabályok és terminálisok neveit ezzel reprezentálom). Java-ban nyelvi szinten támogatott (egy speciális osztály típus), míg Scala-ban a standard könyvtárba beépített közönséges osztály, melyből lehet származni. A definíciója Java-ban véleményem szerint intuitívabb, az ember ránéz, és tudja miről van szó:

```java
public enum TerminalSymbol {
    DIGIT, POINT, SIGN, ZERO;
}
```
Scala-ban viszont ismerni kell a beépített <code>Enumeration</code> osztály felépítését:

```scala
object TerminalSymbol extends Enumeration {
  type TerminalSymbol = Value
  val DIGIT, POINT, SIGN, ZERO = Value
}
```
Itt a <code>Value</code> egyszer típusként (első előfordulás), egyszer pedig függvényként (második előfordulás) szerepel, amely hozzáadja a lehetséges értékeket a típushoz. A definiálással szemben a használat viszont rugalmasabb Scala-ban: például az import utasítás segítségével minden belső tagot behúzhatunk a kódba, így szimplán használhatók az értékek a típus leírása nélkül:

- Java

```java
TerminalSymbol symbol = TerminalSymbol.POINT;
if(symbol == TerminalSymbol.POINT){ result = ... } else { result = ... } // utasítások
```

- Scala

```scala
val symbol = POINT
val result = if(symbol == POINT) ... else ... 
```

Singleton
---------
Érdekesség, hogy __mindkét fenti példa egyke__: a __Java-ban a felsorolás típus definíció szerint egyke__ (pontosabban minden egyes lehetséges érték egyetlen példányt definiál a felsorolás típus nevével megegyező osztályból), __Scala-ban__ pedig __az__ <code>object</code> __kulcsszó egy egyke osztályt definiál__. Ez utóbbi általánosabb, segítségével tetszőleges egyke osztályt definiálhatunk, a Java-ban nem létezik ennek megfelelő egyszerű nyelvi feature.

Produkciós szabályok reprezentációja
------------------------------------
További érdekesség, hogy az egyes nyelveken hogyan reprezentáltam a produkciós szabályokat. Java-ban volt nagyobb tapasztalatom, de __úgy érzem Scala-ban sikerült elegánsabb megoldást adni ezen feladatra__. Java-ban úgy éreztem, a legegyszerűbb, legtömörebb megoldás, ha egy statikus <code>Map</code> adatszerkezetet definiálok, és töltök fel a megfelelő szabályokkal. Ezzel szemben Scala-ban úgy éreztem, egyszerű függvényekkel definiálható legegyszerűbben egy-egy állapotátmenet, a beépített **mintaillesztés**nek köszönhetően (pattern matching).

Objektum-orientált felépítés
----------------------------
Mindkét nyelv alapvetően objektum-orientált. __A Scala-nál__ külön büszkék rá, hogy itt tényleg __minden objektum__ (a beépített típusok, primitívek, de még a függvények is). __A származtatás__ használata **természetes**nek hat (kényelmes, és egyszerű megoldásnak tűnik), viszont úgy érzem, hogy nagyon sokat használtam egy olyan mintát, hogy létrehoztam egy singleton (object) típust, majd ebben definiáltam függvények halmazát, és nem pedig példányosítható osztályokra definiáltam metódusokat, majd hoztam létre egy példányt az osztályból. Azt a nyelvben levő viszonylag kevés tapasztalatom miatt nem tudom biztosan megítélni, hogy helyesen jártam-e el ezzel, valahogy ezt tűnt a legkézenfekvőbb megoldásnak, de az is lehet hogy a feladat jellegéből fakadt ez.

Adatok reprezentációja
----------------------
__Itt__ egyértelműen __a Scala nyer__. A __Java-ban erre van egy jól bevált minta__, amelyet __Bean__-nek neveznek: ha adatokat szeretnénk reprezentálni, definiáljunk egy osztályt, vegyük fel benne a reprezentálandó adatokat, mint belső mezőket, írjunk hozzá gettereket, igény esetén settereket, konstruktorokat, equals (egyenlőségvizsgálat) és hashCode függvényeket, toString függvényt, igény esetén tegyük szerializálhatóvá, stb. __Scala-ban ezzel ekvivalens funkcionalitást egyetlen sorban elérünk__, ha ún. <code>case class</code>-t definiálunk: 

```scala
case class RuleResult(newState: Option[StateMachineState], terminalProcessed : Boolean = true)
```
Tömörség
--------
Általánosan kijelenthető, hogy a Scala tömörebben fejezi ki az algoritmusokat, a funkcionális paradigma miatt. Az automata algoritmusának lényegi definíciója is nagyon kompakt (kb. 10 sorban kifejezhető). A tömörség vizsgálatára ajánlom még figyelembe a <code>main</code> függvényeket is. Íme egy program amely a standard inputról olvas <code>EOF</code>-ig, és kiírja az olvasott adatokat a standard outputra. 

- Java

```java
public class Main {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            String input;
            while(sc.hasNext()){
                input = sc.nextLine();
                System.out.println(input);
            }
        }
    }
}
```
Megjegyzés: ez már egy kompaktabb változat, amely használja a 7-es Java újdonságait (<code>java.util.Scanner</code>, és try-with-resources konstrukció)
- Scala

```scala
object Main {
  def main(args : Array[String]): Unit = {
    for(ln <- Source.stdin.getLines()) println(ln)
  }
}
```
