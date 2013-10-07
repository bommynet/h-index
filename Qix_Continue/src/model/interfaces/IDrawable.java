package model.interfaces;

import java.awt.Graphics2D;

/**
 * Dieses Interface implementiert eine Funktion, welche das Zeichnen
 * auf einem Graphics2D-Objekt erm&ouml;glicht.
 * @author Thomas Laarmann
 * @version 1.0
 */
public interface IDrawable {
	/**
	 * Diese Funktion zeichnet beim Aufruf das gew&uuml;nschte Objekt 
	 * (Bild, Form, o.&auml;.) auf dem &uuml;bergebenen Graphics2D-Objekt.
	 * @param g wird durch die aufrufende paintComponent() &uuml;bergeben
	 */
	public void drawObject(Graphics2D g);
}
