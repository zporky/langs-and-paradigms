Erlang:

- Rekurzív függvényhívással valósítom meg az automatát.
- Egy függvény egy szabálynak felel meg.
- A bemeneti stringet egy listával ábrázolom, melybol az első elemet a soron következő szabály alkalmazása után elhagyom. - A következö függvényt (szabályt) a hátralevő listával hívom.

Funkcionális nyelvekben viszonylag könnyen meg lehet valósítani ezt az automatát.

Nehézséget az első szabály alkalmazása okozotott, hiszen ettől függ, hogy a lista első karakterét is kiírjuk-e elfogadás esetén. Emiatt az S függvény által visszaadott listát egy változóba mentettem el.


Java (kezdo):

- Legfőbb nehézséget az okozta, hogy a közelmúltban inkább funkcionális nyelveket használtam és az objektumorientált szemlélet háttérbe szorult. 
- A BSc első félévében tanultak segitségével egy felsoroló felhasználásával implementáltam a string olvasását. 
- Az automatát egy osztály valósítja meg, mely a felsorolót használva olvassa végig a stringet.
- Egy enum típust hoztam létre a szabályok jelölésére, egy változóban tárolom el a soron következő szabályt.
- Egy szabály használatának egy függvényhívás felel meg, amely megvizsgálja az aktuális karaktert és megadja a következő használandó szabályt. Amennyiben nem használható a szabály, hibát dob.
