
package util;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 
 * 
 * 
 * aStar aehnlicher Algorithmus zur findung eines Weges in einem Gebiet.
 * Kann fuer eine gewoenliche Wegsuche verwendet werden, oder auch fuer berechnung eines zufaelligen Weges.
 * groesstenteils auf rudimentaeren Datenstruckturen aufgebaut(Arrays) um die Performanz zu steigern.
 * <br>
 * Kann auch wege Finden die mehrere Felder gross sind.
 * z.b. Ein held steht auf 4 mal 4 Feldern.
 * <br>
 * Quelle von der ich Informationen ueber Astar bezogen habe:
 * http://memoization.com/2008/11/30/a-star-algorithm-in-java/
 * 
 * @author Marius
 */
public class AStarFieldSet {
	

	/**
	 * falls false wird nicht der beste Weg gefunden, allerdings geht es so schneller.
	 */
	private static final boolean UPDATE_ALREADY_VISITED = true;

	/**
	 * schenll nicht optimaler Weg(sollte verwendet werden, wenn das komplette area sehr gross ist und es wenige Wegbegrenzungen gibt).
	 */
	private static final String HEURISTIK_A = "A";
	/**
	 * langsam optimaler Weg(nur zu Wegsuche verwenden).
	 */
	@SuppressWarnings("unused")
	private static final String HEURISTIK_B = "B";
	/**
	 * Welche benutzt werden soll.
	 */
	private static final String HEURISTIK_TO_USE = "B";


	/**
	 * Position der Kosten im Array.
	 */
	private static final int POSITION_OF_COST = 0;
	
	/**
	 * Position der Kosten um zu diesem Feld zu gelangen im Array.
	 */
	private static final int POSITION_OF_COST_TO_GET_HERE = 1;
	
	/**
	 * Wird nicht gebaucht.
	 * @deprecated
	 */
	private static final int POSITION_OF_COST_OF_HEURISTIK = 2;
	
	/**
	 * Die Kosten noch der Heuristik.
	 */
	private static final int POSITION_OF_COST_FROM_START_TO_GOAL = 3;
	
	/**
	 * Die Id des Knotens, durch welchen der aktuelle besucht worden ist.
	 */
	private static final int POSITION_OF_PARENT_ID = 4;
	
	/**
	 * ab dieser Position, findet man die X Coordinaten im Array.
	 */
	private static final int POSITION_OF_FIRST_X_COORD = 5;
	
	/**
	 * Ab dieser Position findet man die Y Koordinaten im Array.
	 */
	private static final int POSITION_OF_FIRST_Y_COORD = 6;
	
	/**
	 * <p> Speichert Informationen ueber ein FieldSet als integer Array </p>
	 * <b>[0] </b>Kosten aller Felder im set addiert
	 * <br>
	 * <b>[1]</b> Kosten um vom Start auf dieses Feld zu gelangen
	 * <br>
	 * <b>[2] </b>Kosten um vom diesem Feld zum ende zu gelangen (nach einer Heuristik) !!WIRD MOMENTAN NICHT GEBRAUCHT!!
	 * <br>
	 * <b>[3] </b>Kosten um vom Start ueber dieses Feld zum ende zu gelangen (nach einer Heuristik)
	 * <br>
	 * <b>[4] </b>Id des Fiedsets von welchem aus das aktuelle betreten worden ist (Vorgaenger)
	 * <br>
	 * <b>[5] </b>x Koordinate des ersten Feldes im FieldSet
	 * <br>
	 * <b>[6] </b>y Koordinate des ersten Feldes im FieldSet
	 * <br>
	 * ...
	 * <br>
	 * <b>[n-1] </b>x Koordinate des letzten Feldes im FieldSet
	 * <br>
	 * <b>[n] </b>x Koordinate des letzten Feldes im FieldSet
	 *
	 */
	private ArrayList<int[]> fieldSetInformation = new ArrayList<int[]>();

	/**
	 * Die groesse die ein Array in der Variable fieldSetInformation haben muss um alle Informationen speichern zu koennen.
	 */
	private int fieldSetInformationArraySize;
	/**
	 * beschreibt die Nachbarschaften zwischen zwei FieldSet. FieldSet A hat einen Nachbar der durch die Addition aller Punkte mit einem Eintrag in diesem Array definiert ist
	 */
	private Point[] legalMovements = {new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)  , new Point(-1, -1),  new Point(1, 1), new Point(1, -1), new Point(-1, 1) };
	/**
	 *
	 * Speichert die Id des FieldSets, welche auf den gegebenen koordinaten ihren startpunkt hat(oben links).
	 * <b>[x][y]</b> = FieldSetId
	 */
	private static final Point[] DIRECTION_TO_CREATE_FIELDSET = {new Point(1, 1), new Point(-1, 1), new Point(-1, -1), new Point(1, -1)};
	
	/**
	 * ordnet eine Koordinate ein Fieldset zu.
	 * Das Fieldset was gefunden wurde ist jenes, welches an der angegebenen Position die untere linke Ecke hat.
	 */
	private int[][] fieldSetPositions;
	/**
	 * die Groese die ein FieldSet haben muss.
	 */
	private Dimension pathSize;
	/**
	 * die gesamtgroesse des gebietes auf dem ein Weg gesucht wird.
	 */
	private Dimension areaSize;

	/**
	 * Die anzahl der FieldSets.
	 */
	private int fieldSetCounter = 0;
	
	/**
	 * der Punkt von dem das StartFieldSet aus aufgespannt wird.
	 */
	private Point start;
	
	/**
	 * Das FieldSet vom dem die Suche aus gestartet wird.
	 */
	private int[] startFieldSet;
	
	/**
	 * Das Ziel zu dem es eine Route zu finden gilt.
	 */
	private Point goal;
	
	/**
	 * Sollte genau das selbe beinhalten wie OpenList. Aber man kann viel schneller in einem HashSet suchen als in einer PQ
	 * zum schnellen ueberpruefen ob ein Element in der OpenList ist O(1).
	 */
	private HashSet<Integer> openListContains = new HashSet<Integer>();
	
	/**
	 * Hier werden alle FieldSets (ids) gespeichert die vom Algorithmus schon untersucht worden sind.
	 */
	private HashSet<Integer> closedList = new HashSet<Integer>();
	
	/**
	 * spiegelt das Area wieder, die int werte beschreiben hierbei die Kosten dieses feld zu betreten oder -1 falls es nicht begehbar ist. 
	 */
	private int[][] fields;
	
	/**
	 * Die Priority Queue die Die Fieldsset nach der Prioritaet speichert, am weitesten vorne steht das FieldSet was geschaetzt am naechsten am ziel ist.
	 */
	private PriorityQueue<Integer> openList = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
		@Override
		/**
		 * ueberprueft erst welches der beiden FieldSets nach der Heuristik am naesten ist, wenn beide gleich nah sind, ist das FieldSet welches naeher am Ziel ist hoeher priorisiert
		 * @param id1
		 * @param id2
		 * @return
		 */
		public int compare(final Integer id1, final Integer id2) {
			int[] f1 = fieldSetInformation.get(id1);
			int[] f2 = fieldSetInformation.get(id2);
			
			if (f1[POSITION_OF_COST_FROM_START_TO_GOAL] < f2[POSITION_OF_COST_FROM_START_TO_GOAL]) {
				
				return -1;
				
			} else if (f1[POSITION_OF_COST_FROM_START_TO_GOAL] > f2[POSITION_OF_COST_FROM_START_TO_GOAL]) {
				
				return +1;
				
			} else if (Math.abs(f1[POSITION_OF_FIRST_X_COORD] - goal.x) + Math.abs(f1[POSITION_OF_FIRST_Y_COORD] - goal.y) 
					< Math.abs(f2[POSITION_OF_FIRST_X_COORD] - goal.x) + Math.abs(f2[POSITION_OF_FIRST_X_COORD] - goal.y)) {
				
				return -1;
				
			} else if (Math.abs(f1[POSITION_OF_FIRST_X_COORD] - goal.x) + Math.abs(f1[POSITION_OF_FIRST_Y_COORD] - goal.y)
					> Math.abs(f2[POSITION_OF_FIRST_X_COORD] - goal.x) + Math.abs(f2[POSITION_OF_FIRST_X_COORD] - goal.y)) {
				
				return +1;
			}
			return 0;
		}
    });
	
	/**
	 * 
	 * @param area Das zugrundeliegende Gebiet in dem gesucht wird
	 * @param pathS die Dimension des Pfades
	 * @param startFieldS das StartFieldSet
	 * @param goalPos der ZielPunkt
	 */
	public AStarFieldSet(final int[][] area, final Dimension pathS, final int[] startFieldS, final Point goalPos) {
		pathSize = pathS;
		areaSize = new Dimension(area.length, area[0].length);
		fieldSetInformationArraySize = pathSize.height * pathSize.width * 2 + POSITION_OF_FIRST_X_COORD;  
		fieldSetPositions = new int[areaSize.width][areaSize.height];
		fields = area;
		startFieldSet = startFieldS;
		goal = goalPos;
	}
	
	/**
	 * 
	 * @param costOfMoveableFields Die Begehbaren Punkte auf einer Karte
	 * @param areaS Die Groese des Gebiets
	 * @param pathS Die Dimension des Pfades
	 * @param startLine Die Linie von der aus das FieldSet aufgespannt wird.
	 * @param goalPos der Zielpunkt
	 */
	public AStarFieldSet(final ArrayList<Field<Integer>> costOfMoveableFields, final Dimension areaS, final Dimension pathS, final ArrayList<Point> startLine, final Point goalPos) {
		areaSize = areaS;
		fields = new int[areaSize.width][areaSize.height];
		fieldSetPositions = new int[areaSize.width][areaSize.height];
		
		//Alle Felder sind nicht begehbar ausser die in pointsCanMoves angegebenen. Hier werden erstmal alle als nicht begehbar definiert
		for (int i = 0; i < areaSize.height; i++) {
			for (int j = 0; j < areaSize.width; j++) {
				this.fields[j][i] = -1;
				this.fieldSetPositions[j][i] = -1;
			}
		}
		
		//Hier werden die begehbaren Felder als solche gekennzeichnet
		for (Field<Integer> f : costOfMoveableFields) {
			fields[f.getCoord().x][f.getCoord().y] = f.getValue();
		}
		
		pathSize = pathS;
		fieldSetInformationArraySize = pathSize.height * pathSize.width * 2 + POSITION_OF_FIRST_X_COORD;  
		
		//startLine ArrayList in Array umwandeln
		Point[] sl = new Point[startLine.size()];
		for (int i = 0; i < startLine.size(); i++) {
			sl[i] = startLine.get(i);
		}
		
		this.createFieldSetFromLine(sl);
		goal = goalPos;
	}
	
	/**
	 * Konstrukor.
	 * @param a Eine Klasse die das Interface AStarFieldSetAble implementiert.
	 */
	public AStarFieldSet(final AStarFieldSetAble a) {
		areaSize = a.getAreaDimension();
		pathSize = a.getPathSize();
		fields = new int[areaSize.width][areaSize.height];
		fieldSetPositions = new int[areaSize.width][areaSize.height];
		fieldSetInformationArraySize = pathSize.height * pathSize.width * 2 + POSITION_OF_FIRST_X_COORD;
		
		//Alle Felder sind nicht begehbar ausser die in pointsCanMoves angegebenen. Hier werden erstmal alle als nicht begehbar definiert
		for (int i = 0; i < areaSize.height; i++) {
			for (int j = 0; j < areaSize.width; j++) {
				this.fields[j][i] = -1;
				this.fieldSetPositions[j][i] = -1;
			}
		}
		
		//Hier werden die begehbaren Felder als solche gekennzeichnet
		for (Field<Integer> f : a.getMovableFields()) {
			fields[f.getCoord().x][f.getCoord().y] = f.getValue();
		}
		
		
		arrayListFieldSetToFieldSet(a.getStartFieldSet());
		
		
		
		
		goal = a.getGoal();
	}
	
	/**
	 * 
	 * @param areaS Die Dimension des Gebietes auf dem gesucht wird
	 * @param pathS Die Dimension des Pfades den es zu suchen gilt
	 * @param startPos Der startpunkt
	 * @param goalPos Der Zielpunkt
	 */
	public AStarFieldSet(final Dimension areaS, final Dimension pathS, final Point startPos, final Point goalPos) {
		
		pathSize = pathS;
		areaSize = areaS;
		fieldSetInformationArraySize = pathSize.height * pathSize.width * 2 + POSITION_OF_FIRST_X_COORD;  
		fieldSetPositions = new int[areaSize.width][areaSize.height];
		fields = new int[areaSize.width][areaSize.height];
		start = startPos;
		goal = goalPos;

		//Das zugrundeliegende Gebiet mit Feldern fuellen, ein Feld ist nur definiert durch die Kosten dieses zu betretten
		for (int i = 0; i < areaSize.height; i++) {
			for (int j = 0; j < areaSize.width; j++) {
				this.fields[j][i] = 1;
				this.fieldSetPositions[j][i] = -1;
			}
		}
	}
	
	/**
	 * uebersetzt ein Liste von Punkten, welche ein Fieldset darstellt und macht aus ihr ein FieldSet, wie es im AStar algo verwendet wird.
	 * @param fs Die Liste an punkten die umgeformt werden muss.
	 */
	private void arrayListFieldSetToFieldSet(final ArrayList<Point> fs) {
		int[] fieldSet = new int[this.fieldSetInformationArraySize];
		int fieldSetId = this.fieldSetCounter;
		this.fieldSetCounter++;
		
		fieldSet[POSITION_OF_COST] = 0;
		fieldSet[POSITION_OF_COST_TO_GET_HERE] = 0; //Kosten um hierher zu gelangen = 0 da startKnoten
		fieldSet[POSITION_OF_COST_OF_HEURISTIK] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_COST_FROM_START_TO_GOAL] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_PARENT_ID] = -1; //Startknoten hat keinen Vater
		
		int counter = POSITION_OF_FIRST_X_COORD; // ab der fuenften Position werden erst die Koordinaten gespeichert
		for (Point p : fs) {
			fieldSet[counter] = p.x;
			fieldSet[counter + 1] = p.y;
			
			fieldSet[POSITION_OF_COST] += this.fields[p.x][p.y];
			counter += 2;
			
		}
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		Point tL = null;
		for (int i = POSITION_OF_FIRST_X_COORD; i < fieldSet.length; i += 2) {
			if (fieldSet[i] <= minX && fieldSet[i + 1] <= minY) {
				tL = new Point(fieldSet[i], fieldSet[i + 1]);
			}
		}
		this.fieldSetPositions[tL.x][tL.y] = fieldSetId;
		this.fieldSetInformation.add(fieldSetId, fieldSet);
		startFieldSet = fieldSet;
	}
	

	
	/**
	 * Erstellt ein FieldSet von einem angegebenen Punkt aus, welches der Vorgegebenen Wegdimension entspricht. spannt das FieldSet in +x+y Richtung auf.
	 * <br>
	 * Wenn kein FieldSet mit diesen Dimensionen Aufgespannt werden kann, bricht der Algo ab.
	 * @param p Der Punkt von dem das FieldSet aus aufgespannt werden soll
	 * @return FieldSetId
	 */
	private int createFieldSetFromPoint(final Point p) {
		
		int[] fieldSet = new int[this.fieldSetInformationArraySize];
		int fieldSetId = this.fieldSetCounter;
		this.fieldSetCounter++;
		
		fieldSet[POSITION_OF_COST] = 0;
		fieldSet[POSITION_OF_COST_TO_GET_HERE] = 0; //Kosten um hierher zu gelangen = 0 da startKnoten
		fieldSet[POSITION_OF_COST_OF_HEURISTIK] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_COST_FROM_START_TO_GOAL] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_PARENT_ID] = -1; //Startknoten hat keinen Vater
		
		int counter = POSITION_OF_FIRST_X_COORD; // ab der fuenften Position werden erst die Koordinaten gespeichert
		boolean created = false;
		directions:
		for (Point dir : DIRECTION_TO_CREATE_FIELDSET) {
			for (int i = p.y; i < p.y + pathSize.height; i += 1 * dir.y) {
				for (int j = p.x; j < p.x + pathSize.width; j += 1 * dir.x) {
					if (!this.isFieldInFields(j, i)) {
						continue directions;
					}
					fieldSet[counter] = j;
					fieldSet[counter + 1] = i;
					fieldSet[POSITION_OF_COST] += this.fields[j][i];
					counter += 2;
				}
			}
			created = true;
			break directions;
		}
		if (!created) {
			System.out.println("kann kein StartFieldSet erstellen :(");
			System.exit(0);
		}
		this.fieldSetPositions[p.x][p.y] = fieldSetId;
		this.fieldSetInformation.add(fieldSetId, fieldSet);
		return fieldSetId;
	}
	
	/**
	 * Erstellt ein FIeldSet der gegeben groesse von einer Linie.
	 * einziger unterschied zu der erstellung vom Punkt aus:
	 * Die Linie muss im erstellten FieldSet sein und es gibt nicht nur einen Pubkt von dem aus das FieldSet aufgespannt werden kann.
	 * @param ps Die Linie als Punkte Array
	 * @return Die Id des erstellten FieldSet(normalerweise 0)
	 */
	private int createFieldSetFromLine(final Point[] ps) {
		
		Point min = null;
		Point max = null;
		int inXDir = 0;
		int inYDir = 0;
		
		
		if (ps[0].y == ps[ps.length - 1].y) {
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			inXDir = 1;
			for (Point p : ps) {
				if (p.x < minX) {
					minX = p.x;
					min = p;
				}
				if (p.x > maxX) {
					maxX = p.x;
					max = p;
				}
			}
		} else {
			inYDir = 1;
			int minY = Integer.MAX_VALUE;
			int maxY = Integer.MIN_VALUE;
			for (Point p : ps) {
				if (p.y < minY) {
					minY = p.y;
					min = p;
				}
				if (p.y > maxY) {
					maxY = p.y;
					max = p;
				}
			}
		}
		
		int[] fieldSet = new int[this.fieldSetInformationArraySize];
		int fieldSetId = this.fieldSetCounter;
		this.fieldSetCounter++;
		
		fieldSet[POSITION_OF_COST] = 0;
		fieldSet[POSITION_OF_COST_TO_GET_HERE] = 0; //Kosten um hierher zu gelangen = 0 da startKnoten
		fieldSet[POSITION_OF_COST_OF_HEURISTIK] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_COST_FROM_START_TO_GOAL] = -1; //startKnoten braucht keine Heuristik
		fieldSet[POSITION_OF_PARENT_ID] = -1; //Startknoten hat keinen Vater
		
		Point startAt = new Point(0, 0);
		int counter = POSITION_OF_FIRST_X_COORD; // ab der fuenften Position werden erst die Koordinaten gespeichert
		boolean created = false;

		directions:
		for (Point dir : DIRECTION_TO_CREATE_FIELDSET) {

			counter = POSITION_OF_FIRST_X_COORD;
			if (inXDir == 1 && dir.x < 0 || inYDir == 1 && dir.y < 0)  {
				startAt = max;
			} else if (inXDir == 1 && dir.x > 0 || inYDir == 1 && dir.y < 0) {
				startAt = min;
			}
			for (int i = startAt.y; (i < startAt.y + pathSize.height) && ((i > startAt.y + (dir.y * pathSize.height)) || dir.y != -1); i += 1 * dir.y) {
				for (int j = startAt.x; (j < startAt.x + pathSize.width) && ((j > startAt.x + (dir.x * pathSize.width)) || dir.x != -1); j += 1 * dir.x) {
					if (!this.isFieldInFields(j, i)) {
						continue directions;
					}
					fieldSet[counter] = j;
					fieldSet[counter + 1] = i;
					fieldSet[POSITION_OF_COST] += this.fields[j][i];
					counter += 2;
				}
			}
			created = true;
			break directions;
		}
		if (!created) {
			System.out.println("kann kein StartFieldSet erstellen :(");

		}
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		Point tL = null;
		for (int i = POSITION_OF_FIRST_X_COORD; i < fieldSet.length; i += 2) {
			if (fieldSet[i] <= minX && fieldSet[i + 1] <= minY) {
				tL = new Point(fieldSet[i], fieldSet[i + 1]);
			}
		}
		this.fieldSetPositions[tL.x][tL.y] = fieldSetId;
		this.fieldSetInformation.add(fieldSetId, fieldSet);
		startFieldSet = fieldSet;
		return fieldSetId;
	}
	
	/**
	 * findet neue Nachbarn zum aktuellen Fieldset und updated alte falls noetig.
	 * @param fieldSetId Die FieldSetId vom der aus die Nachbarn gesucht werden sollen
	 * @return int[] adjacenciesId
	 */
	private int[] getAdjanencies(final int fieldSetId) {
		
		int[] adjacencies = new int[legalMovements.length]; // Es kann nur soviele neue Nachbarn geben, wie es Moegliche bewegungen geben kann
		int newAdjacencies = 0; //zaehler fuer neue Nachbarn
		int[] fieldSet = this.fieldSetInformation.get(fieldSetId); //das aktuelle FieldSet mit allen Informationen
		int[] adjacent;
		
		movementloop://Label um mit continue hier weiter zu machen
		for (Point move : legalMovements) { //fuer jede erlaubte Bewegung

			int adjacentId = this.existsFieldSetAlready(fieldSet[POSITION_OF_FIRST_X_COORD], fieldSet[POSITION_OF_FIRST_Y_COORD], move);
			//hier wird ueberprueft ob dieses FieldSet schon einmal besucht wurde, falls ja muss es nicht neu berechnet werden
			if (-1 != adjacentId) {
				
				if (UPDATE_ALREADY_VISITED) {
					this.updateParentAndCostBetweenStartAndFieldSet(adjacentId, fieldSetId); //Falls es das FieldSet schon gibt wird jetzt ueberprueft ob die aktuelle route zu diesem FieldSet billiger ist.
				}
				
				adjacencies[newAdjacencies] = adjacentId;
				newAdjacencies++;
				continue movementloop; // wir sind fertig mit diesem Nachbarn und machen mit dem Naechsten anwaerter weiter
			}
			
			
			//Hier wird das array zurueckgesetzt
			adjacent = new int[this.fieldSetInformationArraySize];
			adjacent[POSITION_OF_COST] = 0;
			adjacent[POSITION_OF_COST_TO_GET_HERE] = 0;
			adjacent[POSITION_OF_COST_OF_HEURISTIK] = -1;
			adjacent[POSITION_OF_COST_FROM_START_TO_GOAL] = -1;
			adjacent[POSITION_OF_PARENT_ID] = fieldSetId;

			//wenn ein Adjacent die forschleife durchlaeuft ohne abzubrechen ist er ein gueltiger adjacent
			
			for (int i = POSITION_OF_FIRST_X_COORD; i < fieldSet.length; i += 2) { //plus 2 weil sowohl x als auch y veraendert werden sollen und diese nacheinander im Array gespeichert sind siehe struktur von fieldSetInformation
				if (!this.isFieldInFields(fieldSet[i] + move.x, fieldSet[i + 1] + move.y)) { // Wenn sich ein Feld ausserhalb des Areas befindet oder im Area aber trotzdem nicht begehbar ist(kosten = -1) dann breche ab
					continue movementloop; //kein gueltiger Adjacent, wir machen mit dem Naechsten anwaerter weiter
				}
				adjacent[i] = fieldSet[i] + move.x; // Hier eine x-Koordinate eines Feldes im FieldSet gespeichert
				adjacent[i + 1] = fieldSet[i + 1] + move.y; // Hier eine y-Koordinate eines Feldes im FieldSet gespeichert
				adjacent[POSITION_OF_COST] += this.fields[adjacent[i]][adjacent[i + 1]]; //kosten fuer einzelnes Feld
				
			}

			
			adjacentId = this.fieldSetCounter++; //FieldSetId wird gesetzt und incrementiert.
			this.fieldSetInformation.add(adjacentId, adjacent); //neues FieldSet wird gespeichert
			
			this.fieldSetPositions[adjacent[POSITION_OF_FIRST_X_COORD]][adjacent[POSITION_OF_FIRST_Y_COORD]] = adjacentId; //der Startpunkt des neuen FieldSets wird gespeichert fuer schnellen zugriff auf ein FieldSet
	
			adjacent[POSITION_OF_COST_TO_GET_HERE] = this.getCostBetweenFieldSetAndStart(fieldSetId, adjacent[0]); //kosten vom start bis hier		
			adjacent[POSITION_OF_COST_FROM_START_TO_GOAL] = this.getCostToGetToGoalFromHere(adjacentId, goal); //kosten vom start bis hier + geschaetzte Kosten bis zum Ziel nach Heuristik
			
			adjacencies[newAdjacencies] = adjacentId;
			newAdjacencies++;
			
		}
		
		return adjacencies;
	}
	
	/**
	 * berechnet rekursiv die kosten zwischen start und aktuellem Punkt indem es fuer jedene Vater die Funktion nocheinmal aufruft.
	 * @param fieldSetId Die Id des FieldSets vom dem aus gesucht werden soll
	 * @return cost die Kosten
	 */
	private int getCostBetweenFieldSetAndStart(final int fieldSetId) {
		int[] fieldSet = this.fieldSetInformation.get(fieldSetId);
		if (fieldSet[POSITION_OF_COST_TO_GET_HERE] != -1) {
			return fieldSet[1];
		}
		if (fieldSet[POSITION_OF_PARENT_ID] != -1) { //FieldSet hat keinen Vater mehr und ist somit startpunkt
			return fieldSet[POSITION_OF_COST] + this.getCostBetweenFieldSetAndStart(fieldSet[POSITION_OF_PARENT_ID]);
		} 
		return 0;
	}
	
	/**
	 * gleiche wie oben nur mit anderen parametern. Diese Funktion wird zuerst aufgerufen und ruft dann getCostBetweenFieldSetAndStart(int fieldSetId) auf
	 * @param parentFieldSetId Die Id des FieldSets vom dem aus die Suche gestartet wird (erster Rekursionsaufruf).
	 * @param cost Die Kosten dieses Feld zu betreten
	 * @return die gesamten Kosten des Weges
	 */
	private int getCostBetweenFieldSetAndStart(final int parentFieldSetId, final int cost) {
		if (parentFieldSetId != -1) {
			return cost + this.getCostBetweenFieldSetAndStart(parentFieldSetId);
		} 
		return 0;
	}
	
	/**
	 * ueberpueft ob ein anderer Weg zum FieldSet guenstiger ist als der aktuelle, wenn ja dann werden informationen geupdated.
	 * @param fieldSetId Das FieldSet von dem asugegangen wird
	 * @param parentFieldSetId das VaterFieldSet
	 */
	private void updateParentAndCostBetweenStartAndFieldSet(final int fieldSetId, final int parentFieldSetId) {
		int[] fieldSet = this.fieldSetInformation.get(fieldSetId);
		int newCost = getCostBetweenFieldSetAndStart(parentFieldSetId, fieldSet[0]);
		if (newCost < fieldSet[POSITION_OF_COST_TO_GET_HERE]) {
			fieldSet[POSITION_OF_COST_TO_GET_HERE] = newCost; // weg ist guenstiger also update Kosten
			fieldSet[POSITION_OF_PARENT_ID] = parentFieldSetId; // und Vater
			
		}
	}
	
	/**
	 * ueberprueft on ein Field im aktuelen Area liegt.
	 * @param x Koordinate des Feldes
	 * @param y Koordinate des Feldes
	 * @return ob das Feld im Gebiet liegt oder nicht
	 */
	private boolean isFieldInFields(final int x, final int y) {
		if (x >= 0 && y >= 0 && x < this.areaSize.width && y < this.areaSize.height && this.fields[x][y] != -1) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * ueberprueft on ein FieldSet die ubergebenen x und y Koordinaten beinhaltet.
	 * @param fieldSetId Die Id des FieldSets in welches das Feld liegen soll
	 * @param x Koordinate des Feldes
	 * @param y Koordinate des Feldes
	 * @return ob das Feld im FieldSet ist oder nicht
	 */
	private boolean isFieldInFieldSet(final int fieldSetId, final int x, final int y) {
		int[] fieldSet = this.fieldSetInformation.get(fieldSetId);
		for (int i = POSITION_OF_FIRST_X_COORD; i < fieldSet.length; i += 2) {
			if (fieldSet[i] == x && fieldSet[i + 1] == y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ueberprueft ob es ein Fieldset gibt, welches seinen Startpunkt(oberes Linkes Feld) auf den gegebenen Koordinaten hat.
	 * <br>
	 * gibt gefundenes FieldSet zurueck
	 * 
	 * @param x Koordinate des moeglichen FieldSets
	 * @param y Koordinate des moeglichen FieldSets
	 * @return Die Id des gefundenen FieldSets
	 */
	private int getFieldSetFromFields(final int x, final int y) {
		
		if (!this.isFieldInFields(x, y)) {
			return -1;
		}
		
		if (this.fieldSetPositions[x][y] != -1) {
			return this.fieldSetPositions[x][y];
		} else {
			return -1;
		}
	}
	
	/**
	 * uberprueft ob es ein FieldSet, welches deinen Startpunkt auf dem Feld (startX+move.x,startY+move.y) hat, bereits existiert.
	 * @param startX Koordinate die es noch zu bewegen gilt um neues FieldSet zu erreichen.
	 * @param startY Koordinate die es noch zu bewegen gilt um neues FieldSet zu erreichen.
	 * @param move die Bewegung die gemacht werden soll.
	 * @return ob vom Startpunkt aus in die gegebene Richtung bereits ein FieldSet existiert welches seinen Startpunkt auf dem berechneten Punkt registriert hat.
	 */
	private int existsFieldSetAlready(final int startX, final int startY, final Point move) {
		
		int xInNewFieldSet = startX + move.x;
		int yInNewFieldSet = startY + move.y;
		
		return this.getFieldSetFromFields(xInNewFieldSet, yInNewFieldSet);
	}

	

	
	/**
	 * Gibt die kosten fuer eine geschaetzte komplette strecke vom start zum fieldset(genaue Kosten) und dann zum Ziel(schaetzwert) zurueck.
	 * @param id Das FieldSet was fuer die schaetzung relevant ist
	 * @param ziel das Ziel
	 * @return die geschaetzten Kosten um vom Start zum Ziel ueber dieses Fieldset zu gelangen
	 */
	@SuppressWarnings("unused")
	public final int getCostToGetToGoalFromHere(final int id, final Point ziel) {
		int goalX = ziel.x;
		int goalY = ziel.y;
		
		int nearestDist = Integer.MAX_VALUE;
		int nearestX = Integer.MAX_VALUE;
		int nearestY = Integer.MAX_VALUE;
		int[] fieldSet = this.fieldSetInformation.get(id);
		
		//berechnet den Punkt aus dem FieldSet, der dem Ziel am naechsten ist
		for (int i = POSITION_OF_FIRST_X_COORD; i < this.fieldSetInformationArraySize; i += 2) {
				if (Math.abs(goalX - fieldSet[i]) + Math.abs(goalY - fieldSet[i + 1]) < nearestDist) {
					nearestX = fieldSet[i];
					nearestY = fieldSet[i + 1];
					nearestDist = Math.abs(goalX - fieldSet[i]) + Math.abs(goalY - fieldSet[i + 1]);
					
				}
		}
		
		//leitet aus dem Pubkt der dem Ziel am naechsten ist die noch zu gehende Strecke ab
		int xtoGo = Math.abs(nearestX - ziel.x);
		int ytoGo = Math.abs(nearestY - ziel.y);
		if (HEURISTIK_TO_USE == HEURISTIK_A) {
			return this.heuristikA(xtoGo, ytoGo, fieldSet);
		} else {
			return this.heuristikB(xtoGo, ytoGo, fieldSet);
		}
	}
	
	/**
	 * Heuristik A findet nicht immer den besten weg, ist aber viel schneller als Heuristik B.
	 * Diese Heuristik kann die Kosten des noch zu gehenden weges ueberschaetzen, wenn die kosten fuer einen optimalen Weg ueberschaetzt werden, wird ein nicht optimaler Weg gefunden.
	 * @param xtoGo Differenz zwischen FieldSet und Ziel auf der X Achse
	 * @param ytoGo Differenz zwischen FieldSet und Ziel auf der Y Achse
	 * @param fieldSet Das FieldSet von dem aus geschaetzt wird
	 * @return die geschaetzten Kosten
	 */
	public final int heuristikA(final int xtoGo, final int ytoGo, final int[] fieldSet) {
		return (xtoGo + ytoGo) * pathSize.height * pathSize.width * fieldSet[0] + fieldSet[1];
	}

	
	/**
	 * Heuristik B findet immer den besten weg, ist aber viel langsamer als Heuristik A.
	 * Diese Heuristik uebschaetzt die Kosten nicht, vielmehr unterschaetzt diese Heuristik die kosten, was dazu fuehrt das die erwarteten Kosten fuer einen Schritt kleiner sind als die eigentlichen.
	 * @param xtoGo Differenz zwischen FieldSet und Ziel auf der X Achse
	 * @param ytoGo Differenz zwischen FieldSet und Ziel auf der Y Achse
	 * @param fieldSet Das FieldSet von dem aus geschaetzt wird
	 * @return die geschaetzten Kosten
	 */
	public final int heuristikB(final int xtoGo, final int ytoGo, final int[] fieldSet) {
		return (xtoGo + ytoGo) * pathSize.height * pathSize.width + fieldSet[1];
	}	
	
	/**
	 * NUR ZUM TESTEN: zeigt den Weg auf der Konsole an.
	 * @param goalId die Id des FieldSets das dass Ziel beinhaltet
	 */
	public final void printRoute(final int goalId) {
		int [] fieldSet  = this.fieldSetInformation.get(goalId);
		LinkedList<Point> points = new LinkedList<Point>();
		do {
			for (int i = POSITION_OF_FIRST_X_COORD; i < fieldSetInformationArraySize; i += 2) {
				points.add(new Point(fieldSet[i], fieldSet[i + 1]));
			}
			fieldSet = this.fieldSetInformation.get(fieldSet[POSITION_OF_PARENT_ID]);
		}
		while (fieldSet[POSITION_OF_PARENT_ID] != -1);			
		

			for (int i = 0; i < this.areaSize.height; i++) {
				for (int j = 0; j < this.areaSize.width; j++) {

					if (points.contains(new Point(j, i))) {
						System.out.print("O");
					} else {
						System.out.print("'");
					}
				}
				System.out.println();
			}
	}
	
	/**
	 * Speichert die gefundene Route in einer Liste, welche alle Punkte beinhaltet, die auf der Route vorhanden sind.
	 * @param goalId Die Id des letzten FieldSet 
	 * @return Die Liste an punkten, welche die Route darstellt.
	 */
	public ArrayList<Point> saveResult(final int goalId) {
		int [] fieldSet  = this.fieldSetInformation.get(goalId);
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = POSITION_OF_FIRST_X_COORD; i < this.fieldSetInformationArraySize; i += 2) {
			points.add(new Point(fieldSet[i], fieldSet[i + 1]));
		}
		do {
			fieldSet = this.fieldSetInformation.get(fieldSet[POSITION_OF_PARENT_ID]);
			for (int i = POSITION_OF_FIRST_X_COORD; i < this.fieldSetInformationArraySize; i += 2) {
				points.add(new Point(fieldSet[i], fieldSet[i + 1]));
			}
			
		}
		while (fieldSet[POSITION_OF_PARENT_ID] != -1);	
		return points;
	}
	
	/**
	 * Der eigentliche Algo, der den Weg findet(AStar).
	 * @return Die Route als ArrayList von Punkten
	 */
	public final ArrayList<Point> run() {

		int startId = 0;
		if (startFieldSet == null) {
			startId = this.createFieldSetFromPoint(start); // erstellt StartFieldSet vom punkt
		}
		if (this.isFieldInFieldSet(startId, goal.x, goal.y)) {
			return new ArrayList<Point>();
		}
		
		int[] adj = this.getAdjanencies(startId); //alle gueltigen Nachbarn
		//Hier werden die Nachbarn in der OpenList gespeichert
		for (int a : adj) {
			if (!closedList.contains(a) && !openListContains.contains(a)) {
				openList.add(a);
				openListContains.add(a);
			}
		}
			
		
		

		while (!openList.isEmpty()) {

			
			//bestes Fieldet von der Schlange holen und in Closed List speichern
			int bestId = this.openList.poll();
			openListContains.remove(bestId);
			closedList.add(bestId);
			
			int[] adjs = this.getAdjanencies(bestId); //alle gueltigen Nachbarn
			
			
			for (int a : adjs) {
				if (this.isFieldInFieldSet(a, goal.x, goal.y)) { //wurde Ziel gefunden
					return this.saveResult(a);
				}

				if (!closedList.contains(a) && !openListContains.contains(a)) {

					openList.add(a);
					openListContains.add(a);
					
				}
				
			}
			
		}			
		return new ArrayList<Point>();
	}

	/**
	 * @return the legalMovements
	 */
	public final Point[] getLegalMovements() {
		return legalMovements;
	}

	/**
	 * @param legalMoves the legalMovements to set
	 */
	public final void setLegalMovements(final Point[] legalMoves) {
		this.legalMovements = legalMoves;
	}	
}
