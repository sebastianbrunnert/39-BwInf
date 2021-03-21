package de.sebastianbrunnert.tobisturnier;

public class Spieler {

    private String name;
    private int spielerStaerke;

    // Der Spieler wird mit einem gewissen Namen und der Spieler Stärke (aus der Datei ausgelesen) erstellt
    public Spieler(String name, int spielerStaerke){
        this.name = name;
        this.spielerStaerke = spielerStaerke;
    }

    public String getName() {
        return name;
    }

    public int getSpielerStaerke() {
        return spielerStaerke;
    }

    // Der Spieler besitzt eine Methode mit der direkt ein Spiel gegen einen anderen Spieler simuliert werden kann
    public Spieler gegenSpieler(Spieler gegner){

        // Es wird dabei erst die gesammte Anzahl an Kugeln (die gezogen werden können) errechnet
        // Nun wird eine zufällige Zahl von 0-Total generiert
        int random = TobisTurnier.getRandom().nextInt(getSpielerStaerke() + gegner.getSpielerStaerke()) + 1;

        // Nun wird geschaut in welchem Intervall diese zufällig gezugene Kugel liegt
        if(random > this.getSpielerStaerke()){
            // Außerhalb: Gegner hat gewonnen und wird zurückgegeben
            return gegner;
        } else {
            return this;
        }
    }

    // Diese Funktion gibt im Gegensatz zu der oberen FUnktion gegenSpieler nun den Verlierer einer Partie an
    public Spieler gegenSpielerGibVerliererZurueck(Spieler gegner){
        if(gegenSpieler(gegner).equals(this)){
            return gegner;
        } else {
            return this;
        }
    }

}