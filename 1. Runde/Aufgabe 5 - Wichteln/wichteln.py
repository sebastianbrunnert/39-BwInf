# Libary um Programm stoppen zu können und Übergabe von Kommandozeilenargumenten
import sys
# Libary zum Mischen der Liste
import random
# Libary zum Kopieren der Liste
import copy

# Prüfe ob ein Argument angegeben wurde
if len(sys.argv) != 2:
    print("Benutzung: python3 wichteln.py <Textdatei>")
else:
    try:
        file = open(sys.argv[1])
    except Exception:
        print("Benutzung: python3 wichteln.py <Textdatei>")
        sys.exit(0)

    # Es werden alle Zeilen aus der Textdatei kipiert
    content = file.readlines()

    # Eine Vorlage wird erstellt, in der alle Schüler gespeichert werden, samt Geschenk, welches zugeteilt wurde und einem Array mit den Wünschen des Schülers
    schuelerArrVorlage = []
    for i in range(1,len(content)):
        schuelerArrVorlage.append({
            "zugeteilt": None,
            "wuensche": [int(s) for s in content[i].split() if s.isdigit()]
        })

    # Hilfsmethode um zu prüfen, ob innerhalb einer möglichen Verteilung ein Geschenk schon verteilt wurde
    # schuelerArr ist das Verteilungs-Schema
    def istVerteilt(schuelerArr,geschenkId):
        for schueler in schuelerArr:
            if(schueler["zugeteilt"] == geschenkId):
                return True
        return False

    # In diesen beiden Variablen werden die (jedenfalls bisher) besten Verteilungen zwischengespeichert
    besteVerteilung = None
    besteErfuellteWuensche = None

    print("Berechnen...")

    # Nun wird 1000 mal eine mögliche Verteilung geniert
    # Jedesmal wird geschaut, wie "gut" diese Verteilung ist
    # Wenn sie die (jedenfalls bisher) beste ist, wird sie gespeichert
    for z in range(0,1000):

        print(str(z) + "/1000")

        # Die Vorlage wird kopiert und zufällig gemischt
        schuelerArr = copy.deepcopy(schuelerArrVorlage)
        random.shuffle(schuelerArr)

        # Die Erst-Wünsche von gewissen Schülern werden erfüllt.
        # Dabei wird nicht berücksichtigt, welchen Einfluss die Verteilung auf die Verteilung von Zweit-Wünschen hat
        for schueler in schuelerArr:
            if(not istVerteilt(schuelerArr,schueler["wuensche"][0])):
                schueler["zugeteilt"] = schueler["wuensche"][0]

        # Zweitwünsche werden verteilt
        for schueler in schuelerArr:
            if(schueler["zugeteilt"] is None):
                if(not istVerteilt(schuelerArr,schueler["wuensche"][1])):
                    schueler["zugeteilt"] = schueler["wuensche"][1]

        # Drittwünsche werden verteilt
        for schueler in schuelerArr:
            if(schueler["zugeteilt"] is None):
                if(not istVerteilt(schuelerArr,schueler["wuensche"][2])):
                    schueler["zugeteilt"] = schueler["wuensche"][2]


        # Es wird gemessen, wie oft Wünsche erfüllt wurden
        erfuellteWuensche = [0,0,0]
        for schueler in schuelerArr:
            for wunschId in range(0,3):
                if(schueler["wuensche"][wunschId] == schueler["zugeteilt"]):
                    erfuellteWuensche[wunschId] += 1

        # Es wird geprüft, ob diese Verteilung "besser" war als ein vorige bzw. die erste ist
        # Wenn ja wird diese gespeichert
        if(besteErfuellteWuensche == None):
            besteErfuellteWuensche = erfuellteWuensche
            besteVerteilung = schuelerArr
        else:
            if((erfuellteWuensche[1] > besteErfuellteWuensche[1]) or (erfuellteWuensche[1] == besteErfuellteWuensche[1] and erfuellteWuensche[2] > besteErfuellteWuensche[2])):
                besteErfuellteWuensche = erfuellteWuensche
                besteVerteilung = schuelerArr               
     


    # Nun kann die beste gefunden Verteilung ausgegeben werden
    print("Beste Verteilung:")
    for schuelerId in range(0,len(besteVerteilung)):
        # Der Effizienz des Programmes werden Geschenke, die sich ein Schüler sowieso nicht gewünscht hat, erst ganz zum Schluss bei der Ausgabe berechnet
        if(besteVerteilung[schuelerId]["zugeteilt"] is None):
            for i in range(1,len(besteVerteilung)+1):
                if(not istVerteilt(besteVerteilung,i)):
                    besteVerteilung[schuelerId]["zugeteilt"] = i
        print("Schüler " + str(schuelerId+1) + " wurde Geschenk " + str(besteVerteilung[schuelerId]["zugeteilt"]) + " zugeteilt.")

    # Ausgabe, weiviele Wünsche erfüllt wurden    
    print("Erfüllte Erst-Wünsche: " + str(besteErfuellteWuensche[0]))
    print("Erfüllte Zweit-Wünsche: " + str(besteErfuellteWuensche[1]))
    print("Erfüllte Dritt-Wünsche: " + str(besteErfuellteWuensche[2]))