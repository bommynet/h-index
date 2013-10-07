package model;

/**
 * Diese Klasse stellt einen Gegner als MovingPoint dar.
 * @author Niklas Rother
 */
public class Enemy extends MovingPoint
{
	/**
	 * Erzeugt eine neue Instanz der Enemy-Klasse mit den angebenen Werten.
	 */
	public Enemy(float x, float y, float velX, float velY)
	{
		super(x, y, velX, velY);
	}
	
	/**
	 * Kehrt das Vorzeichen von VelX um.
	 * (Simuliert ein Abprallen in der Vertikalen) 
	 */
	public void SwitchXDirection()
	{
		setVelX(getVelX() * -1);
	}
	
	/**
	 * Kehrt das Vorzeichen von VelY um.
	 * (Simuliert ein Abprallen in der Horizontalen) 
	 */
	public void SwitchYDirection()
	{
		setVelY(getVelY() * -1);
	}
}
