package model;

/**
 * Dieses Enum enhält die möglichen Werte eine Kollsion
 * zwischen einem Rechteck und einem MovingPoint.
 * 
 * @author Niklas Rother
 */
public enum BorderCollisionType
{
	/**
	 * Der Punkt liegt nicht in dem Rechteck.
	 */
	None,
	/**
	 * Der Punkt hat gerade die obere Seite berührt.
	 */
	Top,
	/**
	 * Der Punkt hat gerade die linke Seite berührt.
	 */
	Left,
	/**
	 * Der Punkt hat gerade die untere Seite berührt.
	 */
	Bottom,
	/**
	 * Der Punkt hat gerade die rechte Seite berührt.
	 */
	Right,
	/**
	 * Der Punkt liegt schon seit dem letzten Update innerhalb des Rechtecks.
	 */
	Contained
}
