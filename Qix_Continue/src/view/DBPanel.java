package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import config.Config;

import view.draw.QixView;



/**
 * Dieses Panel repr&auml;sentiert den grafischen Teil des Spielfeldes und zeichnet
 * den Hintergund, wie alle Objekte die sich auf dem Spielfeld befinden. Zu
 * zeichnende Objekte werden an die Klasse mit drawableAdd(IDrawable) &uuml;ber
 * geben. Die Grafik wird per Doppelbufferung auf das Panel gezeichnet und wird
 * aktuell alle 10ms neu aufgefrischt.
 * @author Thomas Laarmann
 * @version 130624
 */
public class DBPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1082186762741377789L;

//### KONSTANTEN ####################################################
	public static final int LAYER_UI = 0;
	public static final int LAYER_TOP = 1;
	public static final int LAYER_MIDDLE = 2;
	public static final int LAYER_BOTTOM = 3;
	
//### VARIABLEN #####################################################
	/** Breite des Spielfelds */
	private int width;
	/** H&ouml;he des Spielfelds */
	private int height;
	/** Hintergrundfarbe des Spielfelds */
	private Color bgcolor;
	
	/** Speicher f&uuml;r alle zu zeichnende Objekte */
	private ArrayList<QixView> listUi = new ArrayList<QixView>();
	private ArrayList<QixView> listTop = new ArrayList<QixView>();
	private ArrayList<QixView> listMiddle = new ArrayList<QixView>();
	private ArrayList<QixView> listBottom = new ArrayList<QixView>();
	
	/** f&uuml;r eventuellen Abbruch der Zeichenschleife */
	private boolean running = true;

//### KONSTRUKTOR ###################################################
	/**
	 * Erstellt ein ne&uuml;s DBPanel und initialisiert es mit den Standard
	 * werten f&uuml;r Gr&ouml;&szlig;e und Hintergrundfarbe, welche in
	 * Config angegeben werden.
	 */
	public DBPanel() {
		this.width = Config.WIDTH;
		this.height = Config.HEIGHT;
		this.bgcolor = Config.PLAYFIELD_COLOR;
		this.initPanel();
	}
	
	/**
	 * Erstellt ein ne&uuml;s DBPanel und initialisiert es mit den &uuml;bergebenen
	 * Parametern f&uuml;r die Gr&ouml;&szlig;e und Hintergrundfarbe des ne&uuml;n Panels.
	 * @param width Breite in Pixeln
	 * @param height H&ouml;he in Pixeln
	 * @param background Hintergrundfarbe
	 */
	public DBPanel(int width, int height, Color background) {
		this.width = width;
		this.height = height;
		this.bgcolor = background;
		this.initPanel();
	}
	
	/**
	 * Initialisieren der Panelgr&ouml;&szlig;e und einschalten des doppelten Speichers
	 * f&uuml;r die Grafikauffrischung.
	 */
	private void initPanel() {
		Dimension dim = new Dimension(width, height);
		this.setPreferredSize( dim );
		this.setMinimumSize( dim );
		this.setMaximumSize( dim );
		this.setDoubleBuffered( true );
	}

//### FUNKTIONEN ####################################################	
	/**
	 * F&uuml;gt ein neues, zu zeichnendes Objekt auf den gew&auml;hlten
	 * Layer ein. Der Layer wird durch die Methode getLayer(int) ermittelt.
	 * @param layer LAYER_UI, LAYER_TOP, LAYER_MIDDLE oder LAYER_BOTTOM
	 * @param newObj zu zeichnendes Objekt
	 */
	public void drawableAdd(int layer, QixView newObj) {
		this.getLayer( layer ).add( newObj );
	}
	
	/**
	 * Entfernt ein Objekt aus der Liste der zu zeichnenden Elemente.
	 * @param layer Layer von dem das Objekt entfernt wird
	 * @param index Position des Objekts in der Liste
	 */
	public void drawableRemove(int layer, int index) {
		this.getLayer( layer ).remove( index );
	}
	
	/**
	 * Entfernt ein Objekt aus der Liste der zu zeichnenden Elemente.
	 * @param layer Layer von dem das Objekt entfernt wird
	 * @param obj zu entfernendes Objekt aus der Liste
	 */
	public void drawableRemove(int layer, QixView obj) {
		this.getLayer( layer ).remove( obj );
	}
	
	/**
	 * Entfernt alle Objekte aus der Liste der zu zeichnenden Elemente.
	 * @param layer Layer das geleert werden soll
	 */
	public void drawableClear(int layer) {
		this.getLayer( layer ).clear();
	}
	
	/**
	 * Entfernt alle Objekte aus allen Listen.
	 */
	public void drawableClearAll() {
		listTop.clear();
		listMiddle.clear();
		listBottom.clear();
	}
	
	/**
	 * Gibt die Anzahl der Objekte aus der Liste der zu zeichnenden Elemente zur&uuml;ck.
	 * @param layer Layer auf dem die Objekte gez&auml;hlt werden
	 * @return Anzahl der Objekte >= 0
	 */
	public int drawableSize(int layer) {
		return this.getLayer( layer ).size();
	}
	
	/**
	 * &Uuml;berschreibt das Objekt an der gegebenen Position mit dem neuen Objekt.
	 * @param layer Layer auf dem sich das Objekt befindet
	 * @param index Position in der Liste, die &uuml;berschrieben werden soll
	 * @param newObj das neue Objekt, welches eingef&uuml;gt wird
	 * @return gibt das neue Objekt zur&uuml;ck oder null bei Fehlern
	 */
	public QixView drawableSet(int layer, int index, QixView newObj) {
		return this.getLayer( layer ).set( index, newObj );
	}
	
	/**
	 * &Uuml;berschreibt ein existierendes Objekt mit einem Neuen und gibt es bei
	 * Erfolg zur&uuml;ck an den Aufrufer.
	 * @param layer Layer auf dem sich das Objekt befindet
	 * @param oldObj zu &uuml;berschreibendes Objekt
	 * @param newObj neues Objekt
	 * @return gibt das neue Objekt zur&uuml;ck oder null bei Fehlern
	 */
	public QixView drawableSet(int layer, QixView oldObj, QixView newObj) {
		ArrayList<QixView> pList = this.getLayer( layer );
		if(!(pList.lastIndexOf(oldObj) >= 0)) return null;
		return pList.set( pList.lastIndexOf(oldObj), newObj);
	}
	
	/**
	 * Gibt das mit layer ausgew&auml;hlte ArrayList als Pointer zur&uuml;ck.
	 * @param layer LAYER_TOP, LAYER_MIDDLE oder LAYER_BOTTOM
	 * @return gew&auml;hltes ArrayList
	 */
	private ArrayList<QixView> getLayer(int layer) {
		if(layer == LAYER_UI)
			return listUi;
		if(layer == LAYER_TOP)
			return listTop;
		else if(layer == LAYER_MIDDLE)
			return listMiddle;
		else if(layer == LAYER_BOTTOM)
			return listBottom;
		else
			return null; //Error
	}

//### OVERRIDES: Super & Interface ##################################
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if(!this.isVisible()) return;
		Graphics2D g2d = (Graphics2D) g;
		
		//Hintergrund Spielfeld
		g2d.setColor(bgcolor);
		g2d.fillRect(0,0,width,height);
		
		//einzelne Objekte (spricht nur IDrawable an, da nicht mehr
		//als diese Funktionalität benötigt wird)
		//zuerst der Layer BOTTOM
		for(int i=0; i<listBottom.size(); i++)
			listBottom.get(i).drawObject(g2d);
		//dann Layer MIDDLE
		for(int i=0; i<listMiddle.size(); i++)
			listMiddle.get(i).drawObject(g2d);
		//dann Layer TOP
		for(int i=0; i<listTop.size(); i++)
			listTop.get(i).drawObject(g2d);
		//und zum Schluss den Layer UI
		for(int i=0; i<listUi.size(); i++)
			listUi.get(i).drawObject(g2d);
		
		/*
		 * INFO: Hier habe ich die foreach-Bloecke entfernt, da der Iterator
		 * Probleme macht, wenn ein Array gleichzeitig gelesen und verändert
		 * wird. Siehe Ticket #98
		 */
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(running) {
			this.repaint( 0, 0, 0, width, height);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
