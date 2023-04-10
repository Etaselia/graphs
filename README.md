# graphentool
This Project is built to satisfy these Requirements:

- Java-Programm
- Eingabe: Adjazenzmatrix eines Graphen aus einer Datei (CSV-Format)
für Graphen beliebiger Größe

- Adjazenzmatrix kann vorerst auch im Programm codiert sein (s. TestMatrizen.java)

Berechnungen (Minimalanforderungen):
- Bestimmung der Distanzen und Exzentrizitäten aller Knoten
- Radius, Durchmesser, Zentrum
- Komponenten, Artikulationen, Brücken

Testklasse(n), ausreichend Testfälle/Testdaten
Mögliche Erweiterungen für „Sehr Gut“
- Benutzeroberfläche (GUI), Eingabe per Maus
- Eulersche Linien/Zyklen
- Spannbäume/Gerüste
- Starke Zusammenhangskomponente
- Blöcke (schwierig!)
Abgabegespräch auf Basis von Programmcode ohne Kommentare

# Implementation:
## Matrix Class:
The Class Matrix is used to handle the Graph, it stores the following data:
- int sideLength  --> the side lenght of the graph
- int sideWidth   --> the side width of the graph
- int[][] matrix  --> a 2D array containing the graph

Most functions within the Class return a new Matrix Object, containing the resulting Graph
This also means that actually using the Matrix Object is pretty self explanatory in most cases
For Example:

  matrixA.distance().print();
 
  Matrix A's distance gets calculated and returned as a Matrix Object, which in turn gets printed to console
  
# State: WIP
