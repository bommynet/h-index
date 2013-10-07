package model.interfaces;

/**
 * Simples Interface um eine einfache Steuerung der grafischen Objekte
 * zu erm&ouml;glichen.
 * @author Thomas Laarmann
 * @version 130629
 */
public interface IMoveable {
	/** Bewegt das Objekt nach links. */
	public void moveLeft();
	/** Bewegt das Objekt nach rechts. */
	public void moveRight();
	/** Bewegt das Objekt nach oben. */
	public void moveUp();
	/** Bewegt das Objekt nach unten. */
	public void moveDown();
}
