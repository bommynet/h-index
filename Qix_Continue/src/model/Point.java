package model;

/**
 * Diese Klasse repräsentiert einen 2D-Punkt.
 * @author Niklas Rother
 *
 */
public class Point extends QixModel implements Cloneable
{
	private float x,y;
	
	/**
	 * Gibt die X-Koordinate zurück.
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * Setzt die X-Koordinate.
	 */
	protected void setX(float x)
	{
		this.x = x;
		setChanged();
		notifyObservers("x");
	}
	
	/**
	 * Gibt die X-Koordinate zurück.
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * Setzt die X-Koordinate.
	 */
	protected void setY(float y)
	{
		this.y = y;
		setChanged();
		notifyObservers("y");
	}
	
	/**
	 * Erstellt eine neue Instanz der Point-Klasse mit den angegeben Koordinaten.
	 */
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Gibt true zurück, wenn die beiden Punkte an der selben Stelle liegen.
	 * Hierbei wird auf volle Pixel gerundet.
	 */
	public boolean CollidesWith(Point other)
	{
		return (int)this.x == (int)other.x && (int)this.y == (int)other.y;
	}

	/**
	 * Diese Methode tut nichts.
	 */
	@Override
	public void Update(float eleapsedMillis)
	{
		//Tut nichts
	}
	
	/**
	 * Gibt eine Kopie des Punkte zur�ck.
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return new Point(x, y);
	}
}
