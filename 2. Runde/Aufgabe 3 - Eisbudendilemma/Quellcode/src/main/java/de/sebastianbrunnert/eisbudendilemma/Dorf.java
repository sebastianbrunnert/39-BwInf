package de.sebastianbrunnert.eisbudendilemma;

public class Dorf {

    // Daten, die aus der Datei hervor gehen
    private int umfang;
    private int[] haueser;

    // Für den Algorithmus wird die aktuelle Eisbude gesichert
    private int eisbudenPosition;

    public Dorf(int umfang, int[] haueser) {
        this.umfang = umfang;
        this.haueser = haueser;
        this.eisbudenPosition = 0;
    }

    public int getUmfang() {
        return umfang;
    }

    public int[] getHaueser() {
        return haueser;
    }

    // Verfahren zum Ermitteln der Distanz zweier Positionen
    private int distanz(int pos1, int pos2) {
        int a = Math.abs(pos2-pos1);
        // Wenn z.B. beim Umfang 200 die Distanz 101 beträgt wird die Distanz zu 99 gesetzt, da "in die andere Richtung" es schneller geht
        if(a > this.umfang/2) {
            return this.umfang - a;
        } else {
            return a;
        }
    }

    // Algorithmus zum schauen, ob eine Eisbude (im Vergleich zur vorherigen) akzeptiert wird
    public boolean eisbudePruefen(int eisbudenPosition) {
        int vetos = 0;
        // Jedes Haus wird iteriert
        for(int haus : getHaueser()) {
            // Und anhand der Regeln wird ermittelt, ob dieses sein "Veto" einlegt
            if(distanz(haus,eisbudenPosition) >= distanz(haus,this.eisbudenPosition)) {
                vetos++;
            }
        }
        // Wenn alle "Vetos" ermittelt wurden wird überprüft, ob die Eisbude gesetzt werden kann
        return vetos < this.haueser.length-vetos;
    }

    public void setEisbudenPosition(int eisbudenPosition) {
        this.eisbudenPosition = eisbudenPosition;
    }

    public int getEisbudenPosition() {
        return eisbudenPosition;
    }
}
