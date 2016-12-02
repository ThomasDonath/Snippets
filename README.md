# Snippets #

Code-Snippets usefully for me

## Telefonnummern (groovy) ##

Da man Telefonnummern nicht automatisch formatieren kann, will ich sie vergleichen können. Dummerweise ist

Landesvorwahl+Ortskennzahl+Nummer für das Telefon identisch mit Nummer oder Ortskennzahl+Nummer

Deshalb implementiert die Funktion "normieren" in der Testklasse einen Algorithmus, der die eingebenen Nummern entsprechend expandiert, um sie vergleichbar zu machen.

Die Grundidee ist, eine Telefonnummer falls (vermutlich) notwendig um Orts- und/oder Landesvorwahl zu ergänzen sowie alle Nicht-Ziffern zu entfernen. Das wird mit den beiden zu vergleichenden Telefonnummern getan - damit sollte eine recht gute Trefferquote erzielt werden können.

Kritisch ist
* wenn die Ortsvorwahl nach der Landesvorwahl ohne Trenner mit der Null angegeben wurde (Bsp. +49 040 233445)
* die lokalen Vorwahlen nicht bestimmt werden können (dann könnten Nummern ohne Vorwahl zwar prinzipiell mit Ersatzwerten expandiert werden - das funktioniert aber nicht, wenn eine der beiden zu vergleichenden Nummern mit der zutreffenden Vorwahl erfasst wurde).

Dieser Code ist leicht in andere Sprachen oder produktive Implementierungen zu übertragen.
