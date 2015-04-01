Simon Bence

# Futtatás
- Az alkalmazás Maven projektként lett megvalósítva
- A fordítás a projekt gyökerében kiadott "mvn clean install" parancsal történik (a pom.xml file mappájában)
- A fordított alkalmazás filejai a target mappába kerülnek, az osztályok binárisai a target/src-be
- Legegyszerűbben innen (target/src) futtatható a következő paranccsal: java -cp . bence.prognyelvek.Main
- További lehetőség: valamely megfelelő IDE-ben (pl. Intellij IDEA) a StateMachineRunnerTest futtatása tesztként

# Tervezési döntések
- Az implementáció során semmilyen keretrendszert nem használtam, de a teszthez és fordításhoz igen (JUnit és Maven)
- Mikor elkezdtem a feladatot, célom volt, hogy ésszerű mértékben általános megoldást adjak
- Ennek eredménye egy olyan keret, ami naiv implementáció egy általáns állapotgéphez, valamint alkalmazása a problématérre
- E mentén három részre bomlik a megoldásom: általános állapotgép, a konkrét állapotképhez tartozó konfig, és a futtatás
- Az általános részbe esik az összes interface, a StateMachine, SimpleState, a feltételek illetve az akciók
- A konkrét problématérhez csak a StateMachineBuilder tartozik, amely az elemi lépésekből összekomponálja a programot
- Ez nem volt így az elejétől, de végül sikerült minden lépést általános atomi elemekre bontani.
- A futtatáshoz szükséges minden egyéb, mint a Main és a StateMachineRunner, maga az állapotgép bármivel meghajtható
- Ezeket Maven segítségével egyszerű lenne külön modulokra bontani, de a projekt mérete / felhasználása ezt nem indokolja
- Az osztályok alkalmazássá komponálását (nagyrészt) dependency injection-el csináltam, de keretrendszer nélkül

# Felépítés
- Az egész futást a StateMachine indítja. Ő rendelkezik ismerettel a kezdeti állapotról, illetve a tokenizálásról
- Az állapoton hívott applikáció megadja a következő állapotot, ez addig zajlik, míg a "szalagról" el nem fogy a token
- Minden applikálás két részből áll: feltétel, ami megmondja, hogy az adott állapot aktív e, illetve az akció
- Az akció fogja a kimenetet formálni
- A futás leáll, ha a szalag kifogy, vagy hiba van (nincs megfelelő lépés, az utolsó állapot nem aktív)

# Tapasztalatok
- Számomra ismert nyelvként valahol igyekeztem maximumra törekedni
- Mivel nem tudtam pontosan, mi lesz a végkimenet, fokozatosan (iteratívan) fejlesztettem
- Próbáltam elképzelni, hogy az adott elemek hogyan lesznek hívva, ahhoz próbáltam igazítani őket
- A fentiek miatt automata tesztek későn és viszonylag kis számban kerültek be, ezzel elégedetlen vagyok
- Az eredménnyel nem vagyok száz százalékig elégedett, főleg az Action-öknél úgy érzem, nem teljesen kerek