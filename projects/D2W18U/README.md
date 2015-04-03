Erlang:
Rekurzív függvényhívással valósítom meg az automatát.
Egy függvény egy szabálynak felel meg.
A bemeneti stringet egy listával ábrázolom, melybol az elso elemet a szabályalkalmazás után elhagyom, a következö függvényt a lista végével hivom. 
Egy ilyen automatát funkcionális nyelvekben viszonylag könnyen meg lehet valósítani.
Nehézséget az elso szabály alkalmazása okozta, hiszen az elso szabálytól függ, hogy a lista elso karakterét is kiírjuk-e elfogadás esetén. Emiatt az S függvény által visszaadott listát külön elmentem egy változóba.

Java (kezdo):
Legfobb nehézséget az okozta, hogy a közelmúltban inkább funkcionális nyelveket használtam és az objektumorientált szemlélet háttérbe szorult. 
A BSc elso félévében tanultak segitségével egy felsoroló felhasználásával implementáltam a string olvasását. Az automatát egy osztály valósítja meg, mely a felsoroló osztályt használva lekérdezheti a bemenet következo elemét.
A program a bemenet minden karakterére alkalmazza a soron következo szabályt. A szabályok itt helyes input esetén megadják a következo alkalmazandó szabályt.