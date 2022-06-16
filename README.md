# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Enes Ergin, s351880, s351880@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så fikk jeg beskjed om at jeg kan kopiere programkode fra online-kompendiet, og da gjorde jeg det. Eneste endringen jeg gjorde fra koden fra kompendiet var slik at referansen forelder fikk riktig verdi i hver node.

I oppgave 2 starter jeg med å sjekke om treet er tomt eller ikke. Hvis treet er tomt, kastes det en exception. Deretter definerer jeg en hjelpevariabel antall. Så kjører jeg en loop som starter fra roten av treet, og plusser på antall for hver gang den gitte verdien finnes i treet. Også returneres antall ganger den gitte verdien ble funnet i treet.

I oppgave 3 skal det lages 2 forskjellige hjelpemetoder. I hjelpemetoden førstePostorden kjører jeg en loop som skal returnere første node postorden med p som rot. Andre hjelpemetode nestePostorden skal returnere den noden som kommer etter noden i førstePostorden. 

I oppgave 4 skal det også lages 2 hjelpemetoder. I den første metoden som skal lages sjekker jeg først om roten er null. Så starter jeg med å finne den første noden p i postorden. Deretter vil setningen: p = nestePostorden(p); gi den neste til p blir null. I den andre hjelpemetoden lager jeg et rekursivt kall som traverserer treet i postorden rekkefølge

I oppgave 5 skal det også lages 2 metoder, altså serialize og deserialize. I metoden serialize starter jeg med å definere en liste som skal returneres til slutt og en kø som skal brukes for å legge til verdier i listen. Deretter kjører jeg en loop som sørger for at alle verdier i treet blir lagt inn i køen en eller annen gang, og deretter lagt inn i listen som returneres til slutt. I metoden deserialize starter jeg med å definere et nytt tre. Deretter looper jeg gjennom arrayListen som blir gitt inn og bruker leggInn metoden for å legge til verdier fra arrayListen inn i treet; og til slutt returnerer jeg treet.

I oppgave 6 skulle jeg først lage metoden fjern. Her får jeg også beskjed om at jeg kan kopiere programkode fra online kompendiet, og jeg tar derfor utgangspunkt fra den koden. Men som oppgaveteksten sier må jeg gjøre de endringene som trengs for at pekeren forelder får korrekt verdi i alle noder etter en fjerning. Deretter lager jeg metoden fjernAlle som skal fjerne alle forekomster av en gitt verdi i treet. Metoden skal returnere antallet som ble fjernet. Så lager jeg metoden nullstill som traverserer treet og sørger for at nodeverdier og pekere blir nullet. 


