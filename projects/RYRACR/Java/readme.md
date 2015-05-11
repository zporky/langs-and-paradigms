# Java
## Elsõ feladat - az ismertebb nyelv
- Default package-et használok, pedig az nem javasolt technikai okok miatt. Ilyen egyszerû feladatnál ez megteszi.
- Java alkalmazások tipikusan rengeteg apró forrásfájlokból állnak ami nehézkessé teszi a fordítást build eszközök nélkül. Én static nested inner class-okkal oldottam meg azt a gordiuszi csomót. Szintén használtam static initializereket. Egy pillanatra arra gondoltam, hogy a Java túl könnyen megengedi a nem javasolt dolgok elkövetését - gyanítom ezzel nincs egyedül a nyelvek között.
- Java extrém modellezõ erõvel rendelkezik, jó esetben csak a feladat kiírását Java nyelvre kell fordítani. Én is ezt tettem. Ész nélkül lemodelleztem a feladatot. Közben kiderült, hogy a feladat kiírása nem a legjobb: az állapotátmenet rajzot át kellett picit rajzolnom, hogy determinisztikus véges állapotgéprõl beszélhessünk.
- A direkt feladat-kiírás lekódolási megoldási módszer a feladatkiírás egy tényleges logikai hibáját egészen a végsõ program teszteléséig elodázta. Az állapotgép kiírás szerint az 1.01-et nem fogadhatja el. Számomra ez a jelenség megmutatatta, hogy a Java nagyon alkalmas komponens alapú szoftverfejlesztésre, ahol a komponensek fejlesztõinek csupán a specifikáció rájuk vonatkozó részével kell tisztában lennie.
- Implementáció közben a Java SE-bõl hiányoltam a többkulcsos map-ot, ami jól jött volna az állapotátmenet "függvény" implementációjához.
- Static initializer-ek használata nem javasolt, de csábító
- Egész kódban nincs switch és csak 3 db if elágazás!

## Második feladat - az ismertebb nyelv - saját program kibõvítése
- A meglévõ és mûködõ programot extrém egyszerû volt kibõvíteni az e-s formátummal. A kódhoz való hozzányúlás elõtt felrajzoltam magamnak, hogy milyen módosításokat kell bevinni az állapotátmenet-diagramba. A rajzolgatás eredménye az lett, hogy csak új átmeneteket kell felvenni, meglévõt egyáltalán nem is kell módosítani. Így végül csak pár sort hozzáadtam kódhoz, meglévõk módosítása nélkül. Illetve kiegészítettem a minta bemenetet - már elõ is állt a második feladat megoldása.

## Futtatás
javac Machine.java Runner.java
java Runner input.txt
