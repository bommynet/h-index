package view;


import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

import keyboard.MovementListener;

import view.draw.QixView;

/**
 * Die Klasse QixFrame baut das Spielfenster und seine Inhalte auf, initialisiert
 * und verwaltet alle, f&uuml;r die View wichtigen QixView-Objekte und startet und
 * verwaltet den GraphicThread.
 * @author Thomas Laarmann
 * @version 130624
 */
public class GameWindow {
//### VARIABLEN #####################################################
	/** das anzuzeigende Fenster */
	private JFrame frame;
	
	/** das Spielfeld (grafischer Teil) */
	private DBPanel spielfeld;
	
	/** grafischer Teil des Spielers */
	private QixView spieler;
	

//### KONSTRUKTOR ###################################################
	/**
	 * Erstellt einen neuen JFrame als View, setzt das Standardverhalten des
	 * Frames und f&uuml;gt die einzelnen Elemente (Spielfeld,...) auf den View
	 * ein. Abschlie&szlig;end werden die ben&ouml;tigten Threads gestartet.
	 */
	public GameWindow() {
		//JFrame inistialisieren
		frame = new JFrame("Qix: Gruppe 16");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//Spielfeld initialisieren
		spielfeld = new DBPanel();
		
		//spielfeld in JFrame einfï¿½gen
		frame.add(spielfeld, BorderLayout.CENTER);
		
		this.getGraphicsThread().start();
		
		//abschliessende Einstellungen und anzeigen
		frame.setResizable(false);
		frame.pack(); //=> setzt die Grï¿½ï¿½e des Frames so an, dass alle Elemente rein passen
		frame.setVisible(true);
	}
	

//### FUNKTIONEN ####################################################
	/**
	 * Gibt das Spielfeld an den Aufrufer zur&uuml;ck. (bin mir noch nicht sicher,
	 * ob diese Funktion evtl. durch den Controller ben&ouml;tigt wird!)
	 * @return die DBPanel Instanz des Spielfelds
	 */
	public DBPanel getSpielfeld() {
		//TODO: Funktion getSpielfeld(): prï¿½fen ob sinnvoll
		return spielfeld;
	}
	
	/**
	 * Gibt die Breite des Spielfeldes zur&uuml;ck.
	 * @return Breite in Pixeln
	 */
	public int getWidthPF() {
		return spielfeld.getWidth();
	}
	
	/**
	 * Gibt die H&ouml;he des Spielfeldes zur&uuml;ck.
	 * @return Hoehe in Pixeln
	 */
	public int getHeightPF() {
		return spielfeld.getHeight();
	}
	
	/**
	 * Setzt bzw. erstellt ein neues Spielergrafik-Objekt.
	 * @param player neues IDrawable-Objekt
	 * @return true - das Objekt wurde gefunden und ersetzt, false - wenn nicht
	 */
	public boolean setPlayer(QixView player) {
		//wenn Spieler noch nicht existiert -> anlegen
		if(spieler == null) {
			this.spielfeld.drawableAdd( DBPanel.LAYER_TOP, player );
			this.spieler = player;
			return true;
		}
		//versuchen das Objekt zu ersetzen
		QixView tmp = this.spielfeld.drawableSet(DBPanel.LAYER_TOP, spieler, player);
		//hat funktioniert, wenn nicht null zurueckgegeben wurde
		if(tmp != null) {
			this.spieler = player;
			return true;
		}
		//Objekt ersetzen ergab null, also einen Fehler
		return false;
	}
	
	/**
	 * F&uuml;gt ein neues GegnerObjekt auf dem Spielfeld ein.
	 * @param enemy das einzuf&uuml;gende Objekt
	 */
	public void addEnemy(QixView enemy) {
		spielfeld.drawableAdd( DBPanel.LAYER_MIDDLE, enemy );
	}
	
	/**
	 * FŸr ein neues UI-Element ein.
	 */
	public void addUiElement(QixView ui) {
		spielfeld.drawableAdd(DBPanel.LAYER_UI, ui);
	}
	
	public void removeUiElement(QixView ui) {
		spielfeld.drawableRemove(DBPanel.LAYER_UI, ui);
	}
	
	/**
	 * Entfernt eine Gegnergrafik aus dem View.
	 * @param index Index-Position des Gegner-Objekt
	 */
	public void removeEnemy(int index) {
		spielfeld.drawableRemove( DBPanel.LAYER_MIDDLE, index );
	}
	
	/**
	 * Entfernt eine Gegnergrafik aus dem View.
	 * @param enemy Referenz auf des Gegner-Objekt
	 */
	public void removeEnemy(QixView enemy) {
		spielfeld.drawableRemove( DBPanel.LAYER_MIDDLE, enemy );
	}
	
	/**
	 * Entfernt alle Gegnergrafiken aus dem View.
	 */
	public void removeEnemyAll() {
		spielfeld.drawableClear( DBPanel.LAYER_MIDDLE );
	}
	
	/**
	 * F&uuml;gt ein neues BlockObjekt auf dem Spielfeld ein.
	 * @param block das einzuf&uuml;gende Objekt
	 */
	public void addBlock(QixView block) {
		spielfeld.drawableAdd( DBPanel.LAYER_BOTTOM, block );
	}


	/**
	 * Gibt einen Pointer zum GraphicThread zur&uuml;ck, um ihn zu starten,
	 * wenn <italic>alles</italic> vorbereitet ist.
	 * @return GraphicThread
	 */
	private Thread getGraphicsThread() {
		return new Thread( spielfeld );
	}
	
	/**
	 * Setzt einen neuen MovementListener zur Tastaturabfrage ein.
	 * @param ml der neue MovemenetListener
	 */
	public void addMovementListener(MovementListener ml) {
		frame.addKeyListener(ml);
	}
	
	/**
	 * Gibt eine Referenz auf das JFrame zur&uuml;ck, zur korrekten Verarbeitung
	 * der Dialoge im GameController.
	 * @return JFrame als Component
	 */
	public Component getComponent() {
		return (Component)frame;
	}
}
