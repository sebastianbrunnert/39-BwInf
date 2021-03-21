package de.sebastianbrunnert.tobisturnier.turnierformen;

import de.sebastianbrunnert.tobisturnier.Spieler;
import de.sebastianbrunnert.tobisturnier.TobisTurnier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Liga {

    // HashMap mit den Spielern in dieser Liga
    // Key: Spieler-Instanz
    // Value: Siege
    private HashMap<Spieler, Integer> spieler;

    public Liga() {
        this.spieler = new HashMap<Spieler,Integer>();
        // Alle Spieler werden in die HashMap geladen
        for(Spieler spieler : TobisTurnier.getSpieler()){
            this.spieler.put(spieler,0);
        }
    }

    public Spieler ermittleSieger() {
        List<String> durchgeführtePartien = new ArrayList<>();

        // Durch die Map wird zweimal durchgegangen
        for(Spieler spieler1 : this.spieler.keySet()){
            for(Spieler spieler2 : this.spieler.keySet()){
                // Da ein Spieler nicht gegen sich selbst spielen kann, wird geprüft, ob spieler1 ungleich spieler2
                if(spieler1 != spieler2){
                    // Außerdem wird geprüft, ob diese Partie schon durchgeführt wurde
                    if(!(durchgeführtePartien.contains(spieler1.getName() + " vs " + spieler2.getName()) || durchgeführtePartien.contains(spieler2.getName() + " vs " + spieler1.getName()))){
                        // Jede Partie bekommt einen nachvollziehbaren Namen und wird zur Liste durchgeführtePartien hinzufügegt
                        durchgeführtePartien.add(spieler1.getName() + " vs " + spieler2.getName());

                        // Der Gewinner der Partie wird ermittelt und ein Sieg wird hinzugefügt
                        Spieler gewinnerPartie = spieler1.gegenSpieler(spieler2);
                        this.spieler.put(gewinnerPartie, this.spieler.get(gewinnerPartie)+1);

                    }
                }
            }
        }

        // Ermitteln des Spielers mit den meisten Siegen bzw. mit der kleinsten Spielernummer nach Verfahren wie in TobisTurnier:55 beschrieben
        Spieler meisteSiege = null;
        for(Spieler spieler : this.spieler.keySet()){
            if(meisteSiege == null){
                meisteSiege = spieler;
            }
            // Die Spielernummer braucht nicht verglichen werden, da die Map der Spieler bereits sortiert ist, da sie nie gemischt wurde
            if(this.spieler.get(spieler) > this.spieler.get(meisteSiege)){
                meisteSiege = spieler;
            }
        }

        return meisteSiege;

    }

}
