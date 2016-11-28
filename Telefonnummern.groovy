#!/bin/env groovy

/*
  (c) 2016 Th. Donath

  Telefonnummern in verschiedenen Systemen sollen verglichen werden.

  Da es keinen sicheren Algorithmus gibt, Zeichenketten in irgendeiner Form zu einer Telefonnummer zu formatieren
  (die Bestandteile Landes-/Ortsvorwahl können nicht zuverlässig identifiziert werden)
  werden die Zeichenketten soweit möglich normiert (Trennzeichen entfernen) und ggf. Landes- und/oder Ortsvorwahl ergänzt.

  Das Normieren/Expandieren realisiert die Funktion "normieren" - auf die kommt es an; kann leicht in finale Implementierung übertragen werden.

  In Groovy, weil einfach zu testen - die Testcases siehe unten...
*/

println "\nTelefonnummern vergleichen\n"

class Test {
  boolean vergleichen(String lokalLand, String lokalOrt, String nummer1, String nummer2) {

    def normieren = {String nummer ->
        def String n = nummer
        n = n.replaceAll(/^ */, '')

        // Durchwahl normieren
        n = n.replaceAll(/ *-* *\(0\) *$/, '0')

        // Landesvorwahl enthalten? Dann ist automatisch auch die Ortsvorwahl enthalten
        //    kann nicht sicher getrennt werden, da auch Landesvorwahlen eine Null enthalten können)
        //    eventuell eingeschlossene (0) - die ist zu entfernen
        if (n ==~ /^00.*|^\++.*/) {
          n = '+' + n.replaceAll(/^00|^\++/) {m -> ""}
          n = n.replaceAll(/\(0\)/, '')
        }
        else {
          // Ortsvorwahl enthalten? startet mit 0
          if (n ==~ /^0.*/) {
            n = lokalLand +  n.replaceAll(/^0/, '')
          }
          else {
            n = lokalLand + lokalOrt + n
          }
        }

        // abschliessend alle Füller (Space, -, /) entfernen
        n = n.replaceAll(' ', '')
        n = n.replaceAll('-', '')
        n = n.replaceAll('/', '')

        //println "final string:" + n
        return n
    }

    return normieren(nummer1) == normieren(nummer2)
  }
}

def Test test = new Test()

assert test.vergleichen('+49', '40', '85390', '  85390  ')
assert test.vergleichen('+49', '40', '85390', '85390')
assert test.vergleichen('+49', '40', '85390', '8539 0')
assert test.vergleichen('+49', '40', '85390', '8539-0')
assert test.vergleichen('+49', '40', '85390', '8539/0')
assert test.vergleichen('+49', '40', '85390', '8539(0)')
assert test.vergleichen('+49', '40', '85390', '8539 (0)')
assert test.vergleichen('+49', '40', '85390', '8539-(0)')

assert test.vergleichen('+49', '40', '85390', '0408539-0')
assert test.vergleichen('+49', '40', '85390', '040 8539-0')
assert test.vergleichen('+49', '40', '85390', '040-8539-0')
assert test.vergleichen('+49', '40', '85390', '040/8539-0')

assert test.vergleichen('+49', '40', '85390', '+4940-8539-0')
assert test.vergleichen('+49', '40', '85390', '+49 40-8539-0')
assert test.vergleichen('+49', '40', '85390', '+49-40-8539-0')
assert test.vergleichen('+49', '40', '85390', '+49/40-8539-0')

assert test.vergleichen('+49', '40', '85390', '+49(0)40-8539-0')

assert test.vergleichen('+49', '40', '85390', '++4940-8539-0')
assert test.vergleichen('+49', '40', '85390', '++49 40-8539-0')
assert test.vergleichen('+49', '40', '85390', '++49-40-8539-0')
assert test.vergleichen('+49', '40', '85390', '++49/40-8539-0')

assert test.vergleichen('+49', '40', '85390', '004940-8539-0')
assert test.vergleichen('+49', '40', '85390', '0049 40-8539-0')
assert test.vergleichen('+49', '40', '85390', '0049-40-8539-0')
assert test.vergleichen('+49', '40', '85390', '0049/40-8539-0')

assert !test.vergleichen('+49', '40', '85390', '085390')
assert !test.vergleichen('+49', '40', '85390', '0049040-8539-0')

println "\nall Tests succeeded\n"
