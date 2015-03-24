Készítette: Viola Anikó
Neptun-kód: UDB51N

# HASKELL

# Futtatás
- a programot a forrásfájl mappájában kiadott "ghc fsm.hs" ill. "fsm <inputfile_name>" parancsokkal futtaható, ahol
- az input fájl a forrásfájl mappájában kell legyen

# Tapasztalatok
- A program 70%-a nagyon hamar elkészült, de volt néhány kritikus pont, amivel rengeteg időm ment el
- Sokat küzdöttem a fájl beolvasásával, a konzolra írással, és az állapotváltással
- Jó érzés volt, hogy fel tudtam használni a félév során tanult monádokat az aktuális állapot tárolásához
- Tetszik, hogy rekurzióval milyen elegánsan lehet kiváltani a ciklusokat
- Átláthatóbbnak tűnik a kód a Javásnál, lehet, hogy azért, mert tömörebb, vagy az indentálás miatt, vagy a "where" kulcsszóval bevezetett lokális függvények miatt


# JAVA

# Futtatás
- a feladatot egyszerű Java Application-ként valósítottam meg, így
- a program a forrásfájl mappájában kiadott "javac FSM.java" ill. "java FSM <inputfile_name>" parancsokkal futtaható, ahol
- az input fájl a forrásfájl mappájában kell legyen

# Tervezés, gondolatmenet
- Először az állapotsorozatokat akartam a konzolon látni, amikor ezt elértem, akkor kezdtem el vizsgálni, melyek az elfogadó-, és melyek az elutasító állapotok
- Ezután az outputot a példáknak megfelelő formára hoztam
- Végül megoldottam a fájlból olvasást
- Volt némi problémám a feladat megértésével, nem voltam teljesen biztos abban, hogy milyen Java könyvtárakat szabad használni, illetve hogy helyes-e az, hogy a folyamat során végig karaktersorozatként kezelem a számokat

# Tapasztalatok - Java
- Mivel ebben a nyelvben több a tapasztalatom, ráadásul mivel Haskellben már implementáltam a feladatot, nagyon gyorsan sikerült megírni ugyanazt Javában
- A Haskellhez hasonló gondolatmenetet követtem a megvalósításban: ugyanazokat a függvényeket írtam meg, ugyanúgy listákat használtam, ugyanúgy integerekkel reprezentáltam az egyes állapotokat
- A Haskellhez képest sokkal könnyebb volt megoldani a fájlból olvasást és a konzolra írást, valamint az állapotváltást
- Először tömbökkel akartam reprezentálni az állapotsorozatot és a karaktersorozatot is, de úgy éreztem, a listák sokkal több szabadságot adnak, úgyhogy átírtam


