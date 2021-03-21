console.log("Datei: ")

var stdin = process.openStdin();

// Hier wird nach input gefragt
stdin.addListener("data", function(d) {
   // Hier wird die Datei eingelesen
    require("fs").readFile(d.toString().trim(), (err, data) => {

        if(err) {
            console.log("Datei nicht gefunden.")
            process.exit()
        }

        // In einem Array wird jede Zeile gespeichert ...
        var zeilen = data.toString().split("\n"); 
        zeilen.forEach((e,i) => {
            if(e.substring(e.length-1) == " "){
                // ... für spätere Algorithmen werden die Zeilen noch richtig formatiert
                zeilen[i] = e.substring(0,e.length-1);
            }
        });
        
        // Ein Objekt wird erstellt. In diesem:
        // - Gibt es für jede Obstsorte einen Key 
        // - Value ist dann jeweils jede mögliche Schüssel
        var obstsorten = {};

        // Nun wird die beschriebene Datenstruktur angelegt
        // Dazu werden überall wo möglich Obstsorten gesucht, die angeboten werden
        zeilen[1].split(" ").forEach(e => {
            // Zu Anfang ist jede Schüssel möglich, also wird ein Array als Value angegebn mit Elementen von 1-Anzahl Schüsseln
            obstsorten[e] = Array.from({length: parseInt(zeilen[0])}, (_, i) => i + 1);
        });
        // Hier (in den Resultaten der Partyteilnehmer) wird ebenfalls gesucht
        for(var i = 4; i < zeilen.length; i+=2) {
            zeilen[i].split(" ").forEach(e => {
                obstsorten[e] = Array.from({length: parseInt(zeilen[0])}, (_, i) => i + 1);
            });
        }

        // Nun wird anhand der Ergebnisse und der besuchten Schüsseln der Partybesucher jede Schüssel für jedes
        // Obst entfernt, die nach den Beobachtungen definitiv nicht möglich ist entfernt.
        for(var i = 4; i < zeilen.length; i+=2) {
            zeilen[i].split(" ").forEach(e => {
                obstsorten[e] = obstsorten[e].filter(e => zeilen[i-1].split(" ").map(x=>+x).includes(e))
            });
        }

        // Nun müssen alle möglichen Schüsseln zu einer Obstsorte zugeordnet werden mittels eines Algorithmus
        // Dazu wird jede Obstsorte iteriert, um für diese die die Schüssel zu finden
        var aktiv = true;
        while(aktiv) {
            aktiv = false;
            for(obstsorte in obstsorten) {
                // Obstsorten, bei denen nur eine Schüssel möglich ist, wird diese zugeteilt
                if(obstsorten[obstsorte].length == 1) {
                    for(andereObstsorte in obstsorten) {
                        if(andereObstsorte != obstsorte && obstsorten[andereObstsorte].includes(obstsorten[obstsorte][0])) {
                            // Allen anderen wird diese Schüssel entzogen
                            obstsorten[andereObstsorte].splice(obstsorten[andereObstsorte].indexOf(obstsorten[obstsorte][0]),1);
                            aktiv = true;
                        }
                    }
                } else {
                    // Andernfalls wird geprüft, ob dieses Obst eine Schüssel besitzt, die bei keinem anderen Obst in Betracht
                    // kommt. Dann nämlich ist klar, dass dieses Obst in dieser Schüssel ist.
                    // Dazu werden alle Möglichkeiten einer Obstsorte iteriert
                    obstsorten[obstsorte].forEach(moeglichkeit => {
                        var moeglich = true;
                        // Dazu werden alle anderen Obstsorten iteriert
                        for(andereObstsorte in obstsorten) {
                            if(andereObstsorte != obstsorte && obstsorten[andereObstsorte].includes(moeglichkeit)) {
                                // Wenn diese Obstsorte diese Schüssel hat, ist sie nicht mehr möglich
                                moeglich = false;
                            }
                        }
    
                        // Falls sie möglich ist, wird das auch so gekennzeichnet
                        if(moeglich) {
                            obstsorten[obstsorte] = [moeglichkeit];
                            aktiv = true;
                        }
                    });
                }
            }
        }

        // Duplikate werden gesucht
        for(obstsorte in obstsorten) {
            var count = 0; 

            for(andereObstsorte in obstsorten) {
                if(obstsorten[andereObstsorte].equals(obstsorten[obstsorte])) {
                    // Bei gefundem Duplikat counter um 1 erhöhen
                    count++;
                }
            }

            // Wenn ausreichen viele Duplikate gefunden wurden, können bei anderen die Schüsseln entfernt werden
            if(count >= 2 && count >= obstsorten[obstsorte].length) {
                for(andereObstsorte in obstsorten) {
                    if(!obstsorten[andereObstsorte].equals(obstsorten[obstsorte])) {
                        obstsorten[andereObstsorte] = obstsorten[andereObstsorte].filter(ele => !obstsorten[obstsorte].includes(ele));
                    }
                }
            }
        }


        // Nun muss nur noch ermittelt werden, welche Warteschlangen besucht werden müssen
        warteschlangen = [];
        // Dazu wird durch alle Wünsche iteriert ...
        zeilen[1].split(" ").forEach(wunsch => {
            // ... und alle möglichen Schüsseln einer Obstsorte (es kann vorkommen, dass das mehr als eine sind, 
            // hebt sich aber bei den Beispielen auf) müssen zu ener Liste mit zu besuchenden Warteschlangen hinzugefügt werden
            warteschlangen.push(...obstsorten[wunsch]);
        });
        // Duplikate werden entfernt und das Ergebnis wird ausgegeben
        warteschlangen = [...new Set(warteschlangen)];
        if(warteschlangen.length > zeilen[1].split(" ").length) {
            console.log("Anhand der Daten kann nicht eindeutig gesagt werden, welche Schüsseln besucht werden müssen. Aber unter diesen Schüssel befinden sich die gewünschten Obstsorten: ")
        } 
        console.log(warteschlangen);

        process.exit()

    }); 
});

Array.prototype.equals = function(array) {
    return this.length == array.length && this.every(ele => array.includes(ele));
}