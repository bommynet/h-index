package view.draw;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import model.interfaces.IDrawable;

/**
 * Dies stellt ein QixView-Objekt dar, welches die Basis f&uuml;r alle Objekte
 * bildet, welche &uuml;ber die Graphics2D-Schnittstelle gezeichnet werden sollen.
 * @author Thomas Laarmann
 * @version 130624
 */
public abstract class QixView implements IDrawable, Observer {

	/*
	 * (non-Javadoc)
	 * @see model.interfaces.IDrawable#drawObject(java.awt.Graphics2D)
	 */
	@Override
	public abstract void drawObject(Graphics2D g);

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public abstract void update(Observable arg0, Object arg1);

}
