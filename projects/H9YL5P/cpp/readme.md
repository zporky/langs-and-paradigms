## Tapasztalatok a módosítás során
	-Gyorsan átlátható volt a kód.
	-A két fájlban sok módosítást kellett végrehajtani ahhoz, hogy működjön az új specifikáció szerint az állapotgép
	-A state-machine.cc fájl mérete már most is 100+ sor, így egy nagyobb állapotgép-implementációnál elég nehézkes lenne átlátni.
	-Az állapotok nem önálló elemei a rendszernek, ebből adódóan sokkal tömörebb implementáció az enyémnél
	-Biztonságosabb az én implementációmnál, viszont kevésbé bővíthető( gondoljunk bele egy olyan állapotba, amely nem csak egy karaktert dolgoz fel, vagy hosszabb funckcionalítása van 3-4 sornál)
