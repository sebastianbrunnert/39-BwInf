package de.sebastianbrunnert.tobisturnier.turnierformen;

import de.sebastianbrunnert.tobisturnier.Spieler;
import de.sebastianbrunnert.tobisturnier.TobisTurnier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kox5Turnier {

    private List<Spieler> aktiveSpieler;

    // Für sonstige Kommentare in dieser Klasse siehe KoTurnier
    public Kox5Turnier() {
        this.aktiveSpieler = new ArrayList<>(TobisTurnier.getSpieler());
        Collections.shuffle(this.aktiveSpieler);
    }

    public Spieler ermittleSieger() {
        while (aktiveSpieler.size() > 2){

            List<Spieler> verliererListe = new ArrayList<>();

            for(int i = 0; i < aktiveSpieler.size()-1; i += 2){
                // Hier wird nun (anstatt einmal) fünf mal die Partie ausgetragen
                // Dabei wird im int spieler1 gesichert, wie oft der Spieler mit dem Index i gewinnt

                int spieler1 = 0;
                for(int a = 0; a < 5; a++){
                    if(aktiveSpieler.get(i).gegenSpieler(aktiveSpieler.get(i+1)).equals(aktiveSpieler.get(i))){
                        spieler1++;
                    }
                }
                // Nun wird ermittelt, welcher Spieler verloren hat
                // Dieser wird zur Buffer-Liste hinzugefügt
                if(spieler1 > 2){
                    verliererListe.add(aktiveSpieler.get(i+1));
                }else{
                    verliererListe.add(aktiveSpieler.get(i));
                }

            }

            for(Spieler verlierer : verliererListe){
                aktiveSpieler.remove(verlierer);
            }

        }

        return aktiveSpieler.get(0).gegenSpieler(aktiveSpieler.get(1));

    }

}
