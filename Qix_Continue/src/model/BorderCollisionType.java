package model;

/**
 * Dieses Enum enh�lt die m�glichen Werte eine Kollsion
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
	 * Der Punkt hat gerade die obere Seite ber�hrt.
	 */
	Top,
	/**
	 * Der Punkt hat gerade die linke Seite ber�hrt.
	 */
	Left,
	/**
	 * Der Punkt hat gerade die untere Seite ber�hrt.
	 */
	Bottom,
	/**
	 * Der Punkt hat gerade die rechte Seite ber�hrt.
	 */
	Right,
	/**
	 * Der Punkt liegt schon seit dem letzten Update innerhalb des Rechtecks.
	 */
	Contained
}
