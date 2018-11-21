# BetterTerminal

Servus alle zusammen,
ich habe mal die Terminal-Klasse umgeschrieben, dass man mit ihr auch Testen kann.

# "Installation"
 1. Terminal.java aus dem Ilias gegen die Neue austauschen
 2. In Zeile 30 den Pfad zur Test-Datei eintragen (Mit TODO markiert)
 2. In Zeile 44 den Namen eurer Hauptklasse (mit der main-Methode) eintragen (Mit TODO markiert)

# Verhalten/Funktion
*  Bei Starten eurere Main verhält sich die Terminal.java genau so wie die aus dem Ilias
*  Beim Starten der Terminal.java (ja, die hat jetzt eine main) wird der Test aus der festgelegten Test-Datei abgearbeitet
*  Nach Ende eines Tests kann weiter über die Konsole eingegeben werden
	 
# Die Testdatei(en)
In den Testdateien werden die Tests definiert.
Codierung (auf Leerzeichen achten!):
 *  "# \<Argumente\>" startet die main-Methode mit den Argumenten \<Argumente\>
 *  "> \<Befehl\>" übergibt den Befehl, wenn das nächste mal nachgefragt wird
 *  "$ \<Botschaft\>" Gibt die Botschaft in die Konsole aus (Zur Rückmeldung)
 *  "//\<Kommentar\>" Unbeachteter Kommentar
 *  der Rest wird als erwartete Ausgabe (als Pattern!!!) angesehen
Wenn Ein/Ausgaben nicht stimmen wird das mit Zeilenangabe (und selten auch begründung) ausgegeben.
	 
# FAQ:
1. Kann man auch eigene Test-Dateien schreiben?
	- Natürlich!
2. Ist das alles garantiert sicher?
	- Nein?! Take it or leave it!
3. Das kann man doch viel schöner machen? / Ich könnt' das besser?!
	- I don't care. (vgl 2.)
4. Ich vermisse eine Funktion!
	- Schreib mir, vlt finde ich die Zeit es rein zu coden.
