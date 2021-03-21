# Libary um Programm stoppen zu können und Übergabe von Kommandozeilenargumenten
import sys

# Prüfe ob eine Argument angegeben wurde
if len(sys.argv) != 2:
    print("Benutzung: python3 woerterAufraeumen.py <Textdatei>")
else:
    try:
        file = open(sys.argv[1])
    except Exception:
        print("Benutzung: python3 woerterAufraeumen.py <Textdatei>")
        sys.exit(0)

    # Es werden beide Zeilen aus der Datei ausgelesen
    content = file.readlines()
    # Der gesuchte Satz wird gespeichert
    satz = content[0]
    # Sowie alle Wörter die eingesetzt werden können
    moeglicheWoerter = content[1].replace("\n","").split(" ")

    # Es wird ein Dictionary aufgestellt, in welchem alle unbekannten Wörter einzeln gespeichert werden sollen
    # Key: Das gesuchte Wort
    # Value: Eine Liste, in welche mögliche Einsetzungen für das unbekannte Wort gespeichert werden
    unbekannteWoerter = satz.replace(".","").replace(",","").replace("!","").replace("\n","").split(" ")
    gesuchteWoerter = {}
    for i in range(0,len(unbekannteWoerter)):
        # i * ' ', deswegen, da es möglich ist, dass unbekannte Wörter doppelt in einem Satz auftreten. So kann ein Wort klar definiert werden
        # Aufgrunddessen wird auch beim Arbeiten mit einem zu suchendem Wort dann die Methode .strip() verwendet, um diese Leerzeichen zu entfernen
        gesuchteWoerter[unbekannteWoerter[i] + i * " "] = []


    # In diesem for-Loop werden nun für jedes Wort die Wörter gesucht, die zumindest von der Länge und den bekannten Buchstaben her passen.
    for gesuchtesWort in gesuchteWoerter:
        # Dafür wird durch jedes mögliche Wort iteriert und ...
        for moeglichesWort in moeglicheWoerter:
            moeglich = False
            # Geprüft, ob die Länge übereinstimmt und ...
            if(len(gesuchtesWort.strip()) == len(moeglichesWort)):
                moeglich = True
                for i in range(0,len(gesuchtesWort.strip())):
                    # Geprüft, ob die bekannten Buchstaben übereinstimmen
                    if(gesuchtesWort[i] != "_" and gesuchtesWort[i] != moeglichesWort[i]):
                        moeglich = False

            # Wenn das Wort möglich ist, wird es zu der Liste im Dictionary hinzugefügt
            if(moeglich):
                gesuchteWoerter[gesuchtesWort].append(moeglichesWort)

    # Nun müssen die Wörter passend verteilt werden, sodass zu jedem unbekannten Wort ein passendes Wort gefunden wurde
    for gesuchtesWort in gesuchteWoerter:
        
        # Wörter, bei denen nur ein Wort möglich ist, wird dieses Wort direkt zugeordnet
        if(len(gesuchteWoerter[gesuchtesWort]) == 1):
            # Dazu wird den anderen unbekannten Wörtern, bei denen dieses Wort möglich ist, das Wort entzogen
            for andereGesuchteWoerter in gesuchteWoerter:
                if(andereGesuchteWoerter != gesuchtesWort and gesuchteWoerter[gesuchtesWort][0] in gesuchteWoerter[andereGesuchteWoerter]):
                    gesuchteWoerter[andereGesuchteWoerter].remove(gesuchteWoerter[gesuchtesWort][0])
        else:
            # Andernfalls wird geprüft, ob dieses unbekannte Wort das einzige unbekannte Wort ist, welches ein mögliches Wort als Möglichkeit besitzt
            # Dann nämlich muss dieses mögliche Wort die Einsetzungen für das unbekannte Wort sein 
            for moeglichkeit in gesuchteWoerter[gesuchtesWort]:
                moeglich = True
                # Dazu wird durch die anderen möglichen Wörter iteriert und geprüft, ob diese dieses mögliche Wort besitzen
                for andereGesuchteWoerter in gesuchteWoerter:
                    if(andereGesuchteWoerter != gesuchtesWort and moeglichkeit in gesuchteWoerter[andereGesuchteWoerter]):
                        # Ist das der Fall, so ist das Wort nicht möglich
                        moeglich = False
                
                # Andernfalls muss dieses mögliche Wort die Einsetzung für das unbekannte Wort sein
                if(moeglich):
                    # Das wird auch so gespeichert
                    gesuchteWoerter[gesuchtesWort] = [moeglichkeit]


    # Jetzt wo jedes unbekannte Wort einer eindeutigen Einsetzung zugeordnet wurde, kann der Satz gebildet werden
    # Dazu wird durch die gesuchten Wörter iteriert
    for gesuchtesWort in gesuchteWoerter:
        # Und das gesuchte Wort wird durch das gefundene Wort ersetzt
        # Da es möglich ist, dass im Satz ähnliche Zeichenketten vorhanden sind, wird nur das erste Auftreten des zu suchenden Wortes ersetzt
        # Außerdem werden die Leerzeichen entfernt, die zur Speicherung notwendig waren
        satz = satz.replace(gesuchtesWort.strip(), gesuchteWoerter[gesuchtesWort][0], 1)

    # Der Satz kann nun ausgegebn werden
    print(satz, end="")
