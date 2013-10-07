package view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Observable;

import model.Line;

/**
 * Dies stellt ein QixView-Objekt dar, welches eine Linie auf die Graphics2D-
 * Schnittstelle zeichnet.
 * @author Thomas Laarmann
 * @version 130624
 */
public class LineDraw extends QixView {
//### VARIABLEN #####################################################
	/** x-Position vom Beginn der Linie */
	private int x1;
	/** y-Position vom Beginn der Linie */
	private int y1;
	/** x-Position vom Ende der Linie */
	private int x2;
	/** y-Position vom Ende der Linie */
	private int y2;
	/** Farbe der Linie */
	private Color farbe;
	
//### KONSTRUKTOR ###################################################
	/**
	 * Initialisiert ein LineDraw-Objekt mit der Linienposition von
	 * (0,0)->(0,0) und der Farbe weiss.
	 */
	public LineDraw() {
		//Position der Linie initialisieren
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		//Farbe der Linie initialisieren
		this.farbe = Color.WHITE;
	}
	
	/**
	 * Initialisiert ein LineDraw-Objekt mit einem vorhandenen Line-
	 * Objekt als Model und setzt eine Farbe.
	 * @param linie
	 * @param farbe
	 */
	public LineDraw(Line linie, Color farbe) {
		//Position der Linie initialisieren
		this.setLine( linie );
		//Farbe der Linie initialisieren
		this.farbe = farbe;
	}
	
//### FUNKTIONEN ####################################################
	/**
	 * &Uuml;bernimmt die Position der &uuml;bergebenen Linie.
	 * @param linie neue Daten als Line
	 */
	protected void setLine( Line linie ) {
		this.x1 = (int) linie.getStart().getX();
		this.y1 = (int) linie.getStart().getY();
		this.x2 = (int) linie.getEnd().getX();
		this.y2 = (int) linie.getEnd().getY();
	}
	
	/**
	 * Setzt eine neue Farbe f&uuml;r die Linie.
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
		//Zeichenfarbe aendern
		g.setColor(farbe);
		//Linie zeichnen
		g.drawLine(x1, y1, x2, y2);
	}

	/* (non-Javadoc)
	 * @see view.draw.QixView#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obj, Object arg1) {
		//ist das Objekt kein Line, Daten-Update abbrechen
		if(!(obj instanceof Line)) return;
		//Linie auslesen und als neue Daten setzen
		this.setLine( (Line)obj );
	}
}
