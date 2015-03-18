Simon Bence

# Tapasztalatok
- A Java verzióval ellentétben itt nem törekedtem maximalizmusa, mindössze szerettem volna működni látni
- Ez leginkább azért van mert a nyelvben való bizonytalanságom végett egyszerű elemeket próbáltam használni
- E miatt sokszor úgy éreztem nem azért kell próbálkozzak hogy megtaláljam a legjobb struktúrát hanem hogy működjön
- A felépítés ennek mentén egyszerű, minden függvény egy állapotot és a belőle kimenő tranzíciókat reprezentálja
- Érdekes következménye ennek az egyszerűségnek, hogy rövidebb idő alatt kész is voltam
- Ehhez hozzátartozik, hogy a Java verzióval sokkal előbb kész lehettem volna, de éreztem a késztetést a szépítgetésre
- Az nagyon kellemes ugyanakkor, hogy a funkcionális nyelv milyen jól passzol a feladathoz

# Futtatás
- A futtatáshoz GHCi Haskell futtató környezet szükséges. Ebbe belépve a modult be kell tölteni
- A :cd parancs segítségével lépkedjünk a megfelelő mappába, majd a :load StateMachine.hs segitségével töltsük be
- Ekkor lehetőségünk nyílik a test paranccsal a megadott teszteseteket (és néhány egyebet is) futtatni
- Valamint a runner paranccsal az elvárt interaktív felületet elindítani
- A runner futásából egy üres sor (csak enter) bevitelének segítségével léphetünk ki
