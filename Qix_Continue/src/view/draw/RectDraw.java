/**
 * 
 */
package view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;

import model.Rectangle;

/**
 * Dies stellt ein QixView-Objekt dar, welches ein einfarbiges, gef&uuml;lltes Rechteck
 * auf die Graphics2D-Schnittstelle zeichnet.
 * @author Thomas Laarmann
 * @version 130624
 */
public class RectDraw extends QixView {
//### VARIABLEN #####################################################
	/** x-Position der linken, oberen Ecke */
	private int x;
	/** y-Position der linken, oberen Ecke */
	private int y;
	/** Breite des Rechtecks */
	private int width;
	/** H&ouml;he des Rechtecks */
	private int height;
	/** Farbe des Rechtecks */
	private Color farbe;
	
//### KONSTRUKTOR ###################################################
	/**
	 * Initialisiert ein RectDraw-Objekt mit der Position (0,0), der
	 * Gr&ouml;&szlig;e 40x40 und der Farbe weiss.
	 */
	public RectDraw() {
		//Position der Linie initialisieren
		this.x = 0;
		this.y = 0;
		this.width = 40;
		this.height = 40;
		//Farbe der Linie initialisieren
		this.farbe = Color.WHITE;
	}
	
	/**
	 * Initialisiert ein RectDraw-Objekt mit einem vorhandenen Rectangle-
	 * Objekt als Model und setzt eine Farbe.
	 * @param rechteck
	 * @param farbe
	 */
	public RectDraw(Rectangle rechteck, Color farbe) {
		//Position der Linie initialisieren
		this.setRect( rechteck );
		//Farbe der Linie initialisieren
		this.farbe = farbe;
	}
	
//### FUNKTIONEN ####################################################
	/**
	 * &Uuml;bernimmt die Position des &uuml;bergebenen Rechtecks.
	 * @param rechteck neue Daten als Rectangle
	 */
	protected void setRect( Rectangle rechteck ) {
		this.x = (int) rechteck.getTopLeft().getX();
		this.y = (int) rechteck.getTopLeft().getY();
		this.width = (int) rechteck.getWidth();
		this.height = (int) rechteck.getHeight();
	}
	
	/**
	 * Setzt eine neue Farbe f&uuml;r das Rechteck.
	 * @param farbe neue Farbe
	 */
	public void setColor(Color farbe) {
		this.farbe = farbe;
	}

//### OVERRIDES: Interfaces #########################################
	/* (non-Javadoc)
	 * @see view.draw.QixView#drawObject(java.awt.Graphics2D)
	 */
	@Override
	public void drawObject(Graphics2D g) {
		//Zeichenfarbe einstellen
		g.setColor( farbe );
		//Gefuelltes Rechteck zeichnen
		g.fillRect(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see view.draw.QixView#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obj, Object arg1) {
		//ist obj kein Rectangle, Daten-Update abbrechen
		if(!(obj instanceof Rectangle)) return;
		//Daten auslesen und speichern
		this.setRect( (Rectangle)obj );
	}

}
