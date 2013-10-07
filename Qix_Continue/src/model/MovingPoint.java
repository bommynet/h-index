package model;

public class MovingPoint extends Point
{
	private float velX, velY;
	private Point lastPos;
	
	/**
	 * Gibt die Geschwindigkeit in px/sec in X-Richtung zurück.
	 */
	public float getVelX()
	{
		return velX;
	}
	
	/**
	 * Setzt die Geschwingkeit in px/sec in X-Richtung.
	 */
	protected void setVelX(float velX)
	{
		this.velX = velX;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Gibt die Geschwindigkeit in px/sec in Y-Richtung zurück.
	 */
	public float getVelY()
	{
		return velY;
	}
	
	/**
	 * Setzt die Geschwindigkeit in px/sec in Y-Richtung.
	 */
	protected void setVelY(float velY)
	{
		this.velY = velY;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Gibt die letzte Position des Punktes (vor dem letzten Update) zurück.
	 */
	public Point getLastPos()
	{
		return lastPos;
	}
	
	/**
	 * Erzeugt einen neuen Punkt mit der angebenen Position
	 * und der angebenen Geschwindigkeit.
	 */
	public MovingPoint(float x, float y, float velX, float velY)
	{
		super(x, y);
		this.velX = velX;
		this.velY = velY;
	}
	
	/**
	 * Setzt die Geschwindigkeit auf 0.
	 */
	public void Stop()
	{
		velX = velY = 0;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Aktualisiert die Position den Punktes.
	 */
	@Override
	public void Update(float eleapsedMillis)
	{
		super.Update(eleapsedMillis);
		try
		{
			lastPos = (Point)clone();
		}
		catch (CloneNotSupportedException e)
		{ }
		setX(getX() + velX * eleapsedMillis / 1000);
		setY(getY() + velY * eleapsedMillis / 1000);
	}
}
