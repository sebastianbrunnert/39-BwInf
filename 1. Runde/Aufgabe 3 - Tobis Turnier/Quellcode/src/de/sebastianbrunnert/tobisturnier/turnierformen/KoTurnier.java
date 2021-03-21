package de.sebastianbrunnert.tobisturnier.turnierformen;

import de.sebastianbrunnert.tobisturnier.Spieler;
import de.sebastianbrunnert.tobisturnier.TobisTurnier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KoTurnier {

    private List<Spieler> aktiveSpieler;

    public KoTurnier() {
        // Kopiere alle Spieler in die Liste mit den (noch) "überlebenden" Spielern
        this.aktiveSpieler = new ArrayList<>(TobisTurnier.getSpieler());
        // Diese Liste wird durchgemischt, um zufällige Gegner am Anfang zu ermitteln.
        Collections.shuffle(this.aktiveSpieler);
    }

    public Spieler ermittleSieger() {
        // Ein Schleifendurchgang symboliesiert hier eine Runde
        // Solange bis nur noch zwei Finalisten überleben...
        while (aktiveSpieler.size() > 2){

            // Da die Verlierer erst entfert werden dürfen, nachdem alle Verlierer einer Runde klar sind, wird eine Buffer-Liste eingeführt
            List<Spieler> verliererListe = new ArrayList<>();

            // Jede Partie in dieser Runde symbolisiert einen Schleifendurchgang, deshalb wird jeweils +2 zu i gerechnet
            for(int i = 0; i < aktiveSpieler.size()-1; i += 2){
                verliererListe.add( aktiveSpieler.get(i).gegenSpielerGibVerliererZurueck(aktiveSpieler.get(i+1)) );
            }

            // Verlierer werden entfernt
            for(Spieler verlierer : verliererListe){
                aktiveSpieler.remove(verlierer);
            }

        }

        // Der Gewinner des Finales wird zurückgegeben
        return aktiveSpieler.get(0).gegenSpieler(aktiveSpieler.get(1));

    }

}
