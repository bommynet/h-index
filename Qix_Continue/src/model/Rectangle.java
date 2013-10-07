package model;

import java.util.Observable;
import java.util.Observer;

/**
 * Diese Klasse stellt eine 2D-Rechteck dar.
 * @author Niklas Rother
 *
 */
public class Rectangle extends QixModel implements Observer
{
	private Point topLeft, bottomRight;
	
	/**
	 * Gibt die obere linke Ecke zur√ºck.
	 */
	public Point getTopLeft()
	{
		return topLeft;
	}
	
	/**
	 * Setzt die obere linke Ecke.
	 */
	public void setTopLeft(Point top)
	{
		this.topLeft.deleteObserver(this);
		top.addObserver(this);
		
		this.topLeft = top;
		setChanged();
		notifyObservers("top");
	}
	
	/**
	 * Gibt die untere rechte Ecke zur√ºck.
	 */
	public Point getBottomRight()
	{
		return bottomRight;
	}
	
	/**
	 * Setzt die untere rechte Ecke.
	 */
	public void setBottomRight(Point bottom)
	{
		this.bottomRight.deleteObserver(this);
		bottom.addObserver(this);
		
		this.bottomRight = bottom;
		setChanged();
		notifyObservers("bottom");
	}
	
	/**
	 * Gibt die Breite des Rechtecks zur√ºck (in X-Richtung)
	 */
	public float getWidth()
	{
		return bottomRight.getX() - topLeft.getX();
	}
	
	/**
	 * Gibt die H√∂he des Rechtecks zur√ºck (in Y-Richtung)
	 */
	public float getHeight()
	{
		return bottomRight.getY() - topLeft.getY();
	}
	
	/**
	 * Gibt den oberen rechten Punkt des Rechtecks zurück.
	 */
	public Point getTopRight()
	{
		return new Point(bottomRight.getX(), topLeft.getY());
	}
	
	/**
	 * Gibt den unteren linken Punkt des Rechtecks zurück.
	 */
	public Point getBottomLeft()
	{
		return new Point(topLeft.getX(), bottomRight.getY());
	}
	
	/**
	 * Erstellt eine neue Instanz der Rectangle-Klasse mit den anegeben
	 * oberen linken und unteren rechten Punkten.
	 */
	public Rectangle(Point topLeft, Point bottomRight)
	{
		//Punkte sortieren, damit topLeft auch wirklich immer der obere ist (macht manche Sachen einfacher)
		if(!(topLeft.getX() < bottomRight.getX()))
		{
			this.topLeft = bottomRight;
			this.bottomRight = topLeft;
		} else {
			this.topLeft = topLeft;
			this.bottomRight = bottomRight;
		}
		
		assert topLeft.getY() > bottomRight.getY() : "Oben ist unter unten.";
		//TODO: Was ist, wenn z.B. ein Punkte links unten angeben wird als BottomRight?
	}
	
	/**
	 * Gibt true zur√ºck, wenn die Linie mit dem Rechteck kollidiert, sonst false.
	 */
	public boolean CollidesWith(Line l)
	{
		return this.Contains(l.getStart()) || this.Contains(l.getEnd());
	}
	
	/**
	 * Gibt true zur√ºck, wenn die beiden Rechtecke kollidieren, sonst false.
	 */
	public boolean CollidesWith(Rectangle other)
	{
		return this.Contains(other.topLeft) || this.Contains(other.bottomRight);
	}
	
	/**
	 * Gibt true zu√ºck, wenn der angebene Punkt in diesem Rechteck enthalten ist
	 * (oder auf dem Rand liegt), sonst false.
	 */
	public boolean Contains(Point p)
	{
		return this.topLeft.getX() <= p.getX() && this.topLeft.getY() <= p.getY() && this.bottomRight.getX() >= p.getX() && this.bottomRight.getY() >= p.getY();
	}
	
	/**
	 * Gibt true zu√ºck, wenn das angebene Rechteck in diesem Rechteck enthalten ist
	 * (oder auf dem Rand liegt), sonst false.
	 */
	public boolean Contains(Rectangle r)
	{
		return this.Contains(r.topLeft) && this.Contains(r.bottomRight);
	}
	
	/**
	 * Gibt true zu√ºck, wenn die angebene Linie in diesem Rechteck enthalten ist
	 * (oder auf dem Rand liegt), sonst false.
	 */
	public boolean Contains(Line l)
	{
		return this.Contains(l.getStart()) && this.Contains(l.getEnd());
	}
	
	/**
	 * Überprüft, ob der MovingPoint gerade (seit dem letzten Update) einer der Grenzen des
	 * Rechtecks überschritten hat. Für die genaue Bedeutung der Rückgabewerte siehe die
	 * Dokumentation von BorderCollsionType.
	 */
	public BorderCollisionType FindBorderCollsionType(MovingPoint p)
	{
		if(!Contains(p)) //Gar nicht drin
			return BorderCollisionType.None;
		if(Contains(p.getLastPos())) //Schon die ganze Zeit drin.
			return BorderCollisionType.Contained;
		
		Line lastToCurrent = new Line(p.getLastPos(), p);
		if(new Line(topLeft, getBottomLeft()).CollidesWith(lastToCurrent))
			return BorderCollisionType.Left;
		if(new Line(topLeft, getTopRight()).CollidesWith(lastToCurrent))
			return BorderCollisionType.Top;
		if(new Line(getTopRight(), bottomRight).CollidesWith(lastToCurrent))
			return BorderCollisionType.Right;
		if(new Line(bottomRight, getBottomLeft()).CollidesWith(lastToCurrent))
			return BorderCollisionType.Bottom;
		
		//Diese Stelle sollte nicht erreicht werden
		return null;
	}

	/**
	 * Aktualisiert den Zustand des Rechtecks.
	 */
	@Override
	public void Update(float eleapsedMillis)
	{
		topLeft.Update(eleapsedMillis);
		bottomRight.Update(eleapsedMillis);
	}
	
	/**
	 * Diese Methode leitet Änderungen der zugrundeliegenden
	 * Punkte nach außen weiter (über Oberserver)
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		setChanged();
		if(o == topLeft)
			notifyObservers("top/" + (String)arg);
		else if(o == bottomRight)
			notifyObservers("botom/" + (String)arg);
		else
			notifyObservers(arg);
	}
}
