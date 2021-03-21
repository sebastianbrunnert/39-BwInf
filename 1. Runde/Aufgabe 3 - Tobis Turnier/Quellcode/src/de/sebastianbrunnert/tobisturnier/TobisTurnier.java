package de.sebastianbrunnert.tobisturnier;

import de.sebastianbrunnert.tobisturnier.turnierformen.KoTurnier;
import de.sebastianbrunnert.tobisturnier.turnierformen.Kox5Turnier;
import de.sebastianbrunnert.tobisturnier.turnierformen.Liga;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TobisTurnier {

    private static Random random;
    private static List<Spieler> spieler;
    private static Spieler staerksterSpieler;

    public static void main(String[] args){
        // Da in diesem Programm oftmals eine zufällige Zahl benötigt wird, lege ich direkt eine Instanz von Random an
        TobisTurnier.random = new Random();

        // Einlesen der Spieler Liste, alle Spieler werden in einer Liste gespeichert
        TobisTurnier.spieler = new ArrayList<>();
        System.out.println("Pfad zu Spieler-Liste:");

        Scanner scanner = new Scanner(System.in);

        // Solange nach einer passenden Datei fragen, bis diese exitiert
        File spielstarkenFile = new File(scanner.next());
        while (!spielstarkenFile.exists()){
            System.out.println("Die angegebene Datei existiert nicht. Versuche es erneut:");
            spielstarkenFile = new File(scanner.next());
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(spielstarkenFile));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                // Für jede Zeile die eingelesen wird, wird ein neuer Spieler erzeugt
                try {
                    spieler.add(new Spieler("Spieler " + (spieler.size()+1), Integer.parseInt(line)));
                } catch (Exception exception){
                    System.out.println("Die angegebene Datei kann nicht eingelesen werden.");
                    System.exit(0);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        System.out.println("Es wurden erfolgreich " + spieler.size() + " Spieler eingelesen worden.");

        // Nun, wo alle Spieler erfolgreich eingelesen worden sind, soll ermittelt werden, welcher der spielstärkste Spieler ist. Also der,
        // der über viele Wiederholungen am häufigsten gewinnen soll. Dafür wird durch alle Spieler einmal durchgegangen und ...
        for(Spieler spieler : getSpieler()){
            if(staerksterSpieler == null) {
                // ... wenn noch kein Spieler als stärkster Spieler definiert wurde, wird es
                // Hier wird also der Spieler, der in der Liste an vorderster Stelle steht erstmal definiert
                staerksterSpieler = spieler;
            } else {
                // ... nun wird überprüft ob ein Spieler nach dem ersten Spieler eine höhere Spielstärke hat als der gerade stärkste Spieler
                if(staerksterSpieler.getSpielerStaerke() < spieler.getSpielerStaerke()){
                    staerksterSpieler = spieler;
                }
            }
        }

        System.out.println("Spielstärkster Spieler ist: " + staerksterSpieler.getName());

        // Nun wird jede Turnierform 1000 mal durchgeführt, dabei wird gezählt wie oft bei der Turnierform jeweils der spielstärkste Spieler gewinnt:

        double siegeFürSpielstaerkstenSpieler = 0;

        System.out.println("\n100000 mal K.O.-Turnier durchführen...");
        for(int i = 0; i < 100000; i++){
            KoTurnier koTurnier = new KoTurnier();
            // Wenn bei dieser Turnier-Form der spielstärkste Spieler gewinnt wird siegeFürSpielstaerkstenSpieler um 1 erhöht
            if(koTurnier.ermittleSieger().equals(staerksterSpieler)){
                siegeFürSpielstaerkstenSpieler++;
            }
        }
        System.out.println("In " + (siegeFürSpielstaerkstenSpieler / 100000) * 100 + "% der Fällen gewinnt der spielstärkste Spieler bei der K.O.-Turnierform.");


        siegeFürSpielstaerkstenSpieler = 0;
        System.out.println("\n100000 mal K.O.x5-Turnier durchführen...");
        for(int i = 0; i < 100000; i++){
            Kox5Turnier kox5Turnier = new Kox5Turnier();
            if(kox5Turnier.ermittleSieger().equals(staerksterSpieler)){
                siegeFürSpielstaerkstenSpieler++;
            }
        }
        System.out.println("In " + (siegeFürSpielstaerkstenSpieler / 100000) * 100 + "% der Fällen gewinnt der spielstärkste Spieler bei der K.O.x5-Turnierform.");


        siegeFürSpielstaerkstenSpieler = 0;
        System.out.println("\n100000 mal Liga durchführen...");
        for(int i = 0; i < 100000; i++){
            Liga liga = new Liga();
            if(liga.ermittleSieger().equals(staerksterSpieler)){
                siegeFürSpielstaerkstenSpieler++;
            }
        }
        System.out.println("In " + (siegeFürSpielstaerkstenSpieler / 100000) * 100 + "% der Fällen gewinnt der spielstärkste Spieler bei Liga-Turnierform.");


    }

    public static List<Spieler> getSpieler() {
        return spieler;
    }

    public static Random getRandom() {
        return random;
    }
}
