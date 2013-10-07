package controller;

import java.util.ArrayList;

import config.Config;

import view.GameWindow;
import view.draw.RectDraw;

import model.Direction;
import model.Line;
import model.Point;
import model.Rectangle;

/**
 * Diese Klasse stellt den Controller f&uuml;r das Block-Verhalten dar. Hier
 * werden die Bl&ouml;cke erstellt, ihre Positionen verwaltet und der freie,
 * nicht eroberte Bereich berechnet. Zus&auml;tzlich werden Eroberungen durch
 * den Spieler ebenfalls hier berechnet.
 * @author Thomas Laarmann
 * @version 130624
 */
public class BlockController {
//### KONSTANTEN ####################################################
	/** ID des Blocks an der linken Spielfeld-Seite */
	public static final int LEFT = 0;
	/** ID des Blocks an der oberen Spielfeld-Seite */
	public static final int TOP = 1;
	/** ID des Blocks an der rechten Spielfeld-Seite */
	public static final int RIGHT = 2;
	/** ID des Blocks an der unteren Spielfeld-Seite */
	public static final int BOTTOM = 3;

//### VARIABLEN #####################################################
	/** das Fenster, dass alle zeichenbaren Objekte erhalten soll */
	private GameWindow view;
	/** die Liste aller Block-Modelle */
	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>();
	/** Speicher f&uuml;r den verf&uuml;gbaren, freien Bereich auf dem Spielfeld */
	private Rectangle rectAvailable;
	/** die Fl&auml;che des leeren Spielfelds */
	private float basicArea;

//### KONSTRUKTOREN #################################################
	/**
	 * Erstellt einen neuen BlockController f&uuml;r das Spielfeld. Alle
	 * grafischen Objekte werden dem View zum Zeichnen &uuml;bergeben.
	 * Sollte der view == null sein, so werden keine zeichenbare Objekte
	 * &uuml;bergeben und die Dimension des Spielfelds wird auf den Standard
	 * aus Config gesetzt.
	 * @param view das Fenster, dass alle zeichenbaren Objekte erh&auml;lt
	 */
	public BlockController(GameWindow view) {
		//zum Testen auch erstellbar mit null-View
		int w = Config.WIDTH;
		int h = Config.HEIGHT;
		if(view != null) {
			this.view = view;
			w = view.getWidthPF();
			h = view.getHeightPF();
		}
		//4 unsichtbare Bl&ouml;cke f&uuml;r den Spielfeldrand setzen
		  //(-50,-50) -> (0,height+50) => linker Rand
		  createBlock( -50, -50, 20, h+50 );
		  //(-50,-50) -> (width+50,20) => oberer Rand
		  createBlock( -50, -50, w+50, 20 );
		  //(width,-50) -> (width+50,height+50) => rechter Rand
		  createBlock( w-20, -50, w+50, h+50 );
		  //(-50,height) -> (width+50,height+50) => unterer Rand
		  createBlock( -50, h-20, w+50, h+50 );
		//absichtlich zu gross, zwecks Kollision mit schnellen Objekten
		//verfuegbaren Bereich f&uuml;r div. Berechnungen erstellen
		rectAvailable = calcAvailableArea();
		//die Fläche, die 100% dar stellt
		basicArea = calcCurArea();
	}
	
//### FUNKTIONEN ####################################################
	
	/**
	 * Erstellt einen neuen Block mit den angegebenen Werten. Der neue
	 * Block wird zuerst als Modell erstellt, bekommt ein grafisches
	 * Gegenst&uuml;ck und der Beobachter wird eingrichtet. Ist der, im
	 * Konstruktor &uuml;bergebene view == null, so wird kein grafisches
	 * Objekt zum Zeichnen &uuml;bergeben.
	 * @param x1 x-Position des Punktes links oben
	 * @param y1 y-Position des Punktes links oben
	 * @param x2 x-Position des Punktes rechts unten
	 * @param y2 y-Position des Punktes rechts unten
	 */
	private void createBlock(int x1, int y1, int x2, int y2) {
		//Model erstellen
		Rectangle rc= new Rectangle(
				new Point(x1,y1), new Point(x2,y2) );
		//Grafik aus Model erstellen
		RectDraw rd = new RectDraw( rc, Config.BLOCK_COLOR );
		//Beobachter aktivieren
		rc.addObserver( rd );
		//Modell in Liste einfuegen
		blocks.add( rc );
		//Grafikobjekt in View einfuegen
		if(view!=null) view.addBlock( rd );
	}
	
	/**
	 * Z&auml;hlt alle vorhandenen Bl&ouml;cke im Controller.
	 * @return alle Bl&ouml;cke
	 */
	public int countBlocks() {
		return blocks.size();
	}
	
	/**
	 * Liefert die vorhandenen Bl&ouml;cke als Liste zur&uuml;ck.
	 * @return ArrayList mit allen Bl&ouml;cken
	 */
	public ArrayList<Rectangle> getBlockList() {
		return this.blocks;
	}
	
	/**
	 * Versucht einen neuen Bereich auf dem Spielfeld einzunehmen. Hierzu wird die
	 * durch den Spieler gezeichnete Linie &uuml;bergeben und ausgewertet. Ist die Linie
	 * nicht innerhalb des freien Spielfelds gezeichnet worden, wird nichts ver&auml;ndert
	 * und die Funktion gibt false zur&uuml;ck.
	 * Wenn die Linie doch im Spielfeld ist, wird zuerst festgestellt, ob diese ver
	 * tikal oder horizontal verl&auml;uft und dem entschrechend eine andere Berechnung
	 * als Grundlage gew&auml;hlt.
	 * vertikal: Es werden 2 Werte ermittelt
	 *   a1 = line.x - rectA.topLeft.x
	 *   a2 = rectA.bottomRight.x - line.x
	 * und verglichen. Ist a1 < a2, so wird der Block links verbreitert bis zur Linie,
	 * im anderen Fall wird der Block rechts verbreitert bis zur Linie; es wird also
	 * immer das kleinere Feld gew&auml;hlt.
	 * horizontal: Es werden 2 Werte ermittelt
	 *   b1 = line.y - rectA.topLeft.y
	 *   b2 = rectA.bottomRight.y - line.y
	 * und verglichen. Ist b1 < b2, so wird der Block oben erh&ouml;ht bis zur Linie,
	 * im anderen Fall wird der Block unten erh&ouml;ht bis zur Linie; es wird also
	 * immer das kleinere Feld gew&auml;hlt.
	 * Abschliessend wird das noch verf&uuml;gbare, freie Feld neu berechnet. 
	 * @param line die vom Spieler gezeichnete Linie
	 * @return true - wenn ein Bereich erobert wurde, false - sonst
	 */
	public boolean conquerArea(Line line) {
		//wenn die Linie ausserhalb des verfuegbaren Bereichs liegt, ignorieren
		//if(!rectAvailable.Contains(line)) return false;
		
		//In welcher Richtung verlaeuft die Linie?
		//vertikale Linie
		if(line.GetOrientation() == Direction.Up || line.GetOrientation() == Direction.Down) {
			//breite des linken Abschnitts
			float a1 = line.getStart().getX() - rectAvailable.getTopLeft().getX();
			//breite des rechten Abschnitts
			float a2 = rectAvailable.getBottomRight().getX() - line.getStart().getX();
			//wenn a1 < a2, dann erobere a1
			if(a1 < a2) {
				blocks.get(LEFT).setBottomRight(new Point(
						line.getStart().getX(), blocks.get(LEFT).getBottomRight().getY() ));
			} 
			//sonst a2
			else {
				blocks.get(RIGHT).setTopLeft(new Point(
						line.getStart().getX(), blocks.get(RIGHT).getTopLeft().getY() ));
			}
		}
		//horizontale Linie
		else {
			//hoehe des oberen Abschnitts
			float b1 = line.getStart().getY() - rectAvailable.getTopLeft().getY();
			//hoehe des unteren Abschnitts
			float b2 = rectAvailable.getBottomRight().getY() - line.getStart().getY();
			//wenn b1 < b2, dann erobere b1
			if(b1 < b2) {
				blocks.get(TOP).setBottomRight(new Point(
						blocks.get(TOP).getBottomRight().getX(), line.getStart().getY() ));
			} 
			//sonst b2
			else {
				blocks.get(BOTTOM).setTopLeft(new Point(
						blocks.get(BOTTOM).getTopLeft().getX(), line.getStart().getY() ));
			}
		}
		
		//verfuegbaren Bereich neu berechnen
		rectAvailable = calcAvailableArea();
		return true;
	}
	
	/**
	 * Berechnet den verf&uuml;gbaren Bereich auf dem Spielfeld. Dazu werden die
	 * innen liegenden Kanten der Grenzbl&ouml;cke als neue Spielfeld-Grenze
	 * gesetzt.
	 * _private_: Berechnung braucht nur bei Ver&auml;nderung erfolgen. Zum Auslesen
	 * die Funktion getAvailableArea() aufrufen.
	 * @return der verf&uuml;gbare Bereich als Rectangle
	 */
	protected Rectangle calcAvailableArea() {
		float x1 = blocks.get(LEFT).getBottomRight().getX();
		float y1 = blocks.get(TOP).getBottomRight().getY();
		float x2 = blocks.get(RIGHT).getTopLeft().getX();
		float y2 = blocks.get(BOTTOM).getTopLeft().getY();
		return new Rectangle( new Point(x1,y1), new Point(x2,y2) );
	}
	
	/**
	 * Gibt den verf&uuml;gbaren Bereich des Spielfelds zur&uuml;ck
	 * @return der verf&uuml;gbare Bereich als Rectangle
	 */
	public Rectangle getAvailableArea() {
		return this.rectAvailable;
	}
	
	/**
	 * Berechnet die aktuell nicht eroberte Fl&auml;che.
	 * @return freie Fl&auml;che als pixel&sup2;
	 */
	private float calcCurArea() {
		return (rectAvailable.getWidth() * rectAvailable.getHeight());
	}
	
	/**
	 * Berechnet den Unterschied zwischen gesamter und freier Fl&auml;che
	 * und setzt sie direkt ins Verh&auml;ltnis. Der Wert geht also von
	 * 0.0 (= gar nichts erobert) bis 1.0 (=alles erobert), wobei ein Wert
	 * von 0.75 bedeutet, dass bereits 75% der Fl&auml;che erobert wurden.
	 * @return eroberte Fl&auml;che als Verh&auml;ltnis
	 */
	public float getConqueredAreaValue() {
		return 1 - (calcCurArea() / basicArea);
	}
}
