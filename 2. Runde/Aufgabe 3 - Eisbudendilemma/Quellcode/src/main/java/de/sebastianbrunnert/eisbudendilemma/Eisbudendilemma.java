package de.sebastianbrunnert.eisbudendilemma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Eisbudendilemma {

    public static void main(String[] args) {
        Dorf dorf = null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Datei angeben: ");

        // Solange nach einer passenden Datei fragen, bis diese exitiert
        File file = new File(scanner.next());
        while (!file.exists()){
            System.out.println("Die angegebene Datei existiert nicht. Versuche es erneut:");
            file = new File(scanner.next());
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            // Dorf wird anhand der Daten in Datei initialisiert
            dorf = new Dorf(Integer.parseInt(String.valueOf(bufferedReader.readLine().split(" ")[0])), Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray());

        } catch (Exception e) {
            System.out.println("Die angegebene Datei kann nicht eingelesen werden.");
            System.exit(0);
        }

        // In diese Liste werden später alle möglichen Eisbuden abgelegt
        List<Integer> results = new ArrayList<>();

        // Da für andere anfängliche Eisbuden bei diesem Algorithmus andere schlussendliche Eisbuden resultieren, werden diese iteriert
        // Das heißt, dass dieser Algorithmus letztlich für jeden Startwert durchgeführt wird
        for(int startWert = 0; startWert < dorf.getUmfang(); startWert++) {
            // Also wird die anfängliche Eisbude auch zu dieser gesetzt
            dorf.setEisbudenPosition(startWert);

            // Nun wird durch jede Position iteriert ...
            for(int i = 0; i < dorf.getUmfang(); i++) {
                // ... außer sie ist aktuell gesetzt ...
                if(i != dorf.getEisbudenPosition()) {
                    // ... wenn sie gewählt werden würde von den Dorfbewohnern ...
                    if(dorf.eisbudePruefen(i)) {
                        // ... wird sie auch gesetzt. Der Wert kann bzw. wird sich später noch verändern
                        dorf.setEisbudenPosition(i);
                    }
                }
            }
            // Dieses Verfahren findet also letztlich die letzte mögliche Eisbude. Es ist von Relevanz alle vorherigen auch durchzugehen,
            // da diese ja verglichen wird

            // Wenn diese Eisburde noch nicht gefunden wurde, wird diese gespeichert
            if(!results.contains(dorf.getEisbudenPosition())) {
                results.add(dorf.getEisbudenPosition());
            }
        }

        for(int result : results) {
            System.out.println("Hier kann eine Eisbude stehen: " + result);
        }
    }

}
