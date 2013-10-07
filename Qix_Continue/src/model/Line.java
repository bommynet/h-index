package model;

import java.util.Observable;
import java.util.Observer;

import model.QixModel;

/**
 * Diese Klasse stellt eine 2D-Line dar.
 * @author Niklas Rother
 *
 */
public class Line extends QixModel implements Observer
{
	private Point start, end;
	
	/**
	 * Gibt den Startpunkt zurück.
	 */
	public Point getStart()
	{
		return start;
	}
	
	/**
	 * Sezt den Startpunkt.
	 */
	protected void setStart(Point start)
	{
		this.start.deleteObserver(this);
		start.addObserver(this);
		
		this.start = start;
		setChanged();
		notifyObservers("start");
	}
	
	/**
	 * Gibt den Endpunkt zurück.
	 */
	public Point getEnd()
	{
		return end;
	}
	
	/**
	 * Sezt den Endpunkt.
	 */
	protected void setEnt(Point end)
	{
		this.end.deleteObserver(this);
		end.addObserver(this);
		
		this.end = end;
		setChanged();
		notifyObservers("end");
	}

	/**
	 * Erstellt eine neue Instannz der Line-Klasse mit den angeben
	 * Start- und Endpunkten.
	 */
	public Line(Point start, Point end)
	{
		this.start = start;
		this.end = end;
		//Wir reistrieren uns selbst als Observer, damit wir die Ereignisse weiterleiten k�nnen.
		start.addObserver(this);
		end.addObserver(this);
	}
	
	/**
	 * Autor: Jan Wittler
	 * Gibt true zurück, wenn die beiden Linie sich schneiden, sonst false.
	 * Gibt false zurück, wenn eine Linie die Länge 0 hat.
	 * 
	 * @param other andere Linie
	 * @return Kollision
	 */
	public boolean CollidesWith(Line other)
	{
		//Punkte as: "a start",  ae: "a end",  bs: "b start",  be: "b end"
		//werden zugewiesen.
		float asx = this.getStart().getX();
		float asy = this.getStart().getY();
		float aex = this.getEnd().getX();
		float aey = this.getEnd().getY();
		
		float bsx = other.getStart().getX();
		float bsy = other.getStart().getY();
		float bex = other.getEnd().getX();
		float bey = other.getEnd().getY();
		
		//Entscheidungsbaum
		// 
		// Sind die Strecken echt (Start != Ende)
		//   Nein: false!
		//   Ja  : Scheiden sich die Geraden in genau einem Punkt? (Determinante)
		//           Ja  : Liegt der Schnittpunkt auf beiden Strecken?
		//                   Ja  : true!
		//                   Nein: false!
		//           Nein: Sind die Geraden identisch? (Test verschiedene Parallelen)
		//                   Nein: false!
		//                   Ja  : Sind die Strecken verschränkt 
		//                         oder haben sie gemeinsame Start-/Endpunkte?
		//                           Nein: false!
		//                           Ja  : true!
		//
		
		if (asx == aex && asy == aey || bsx == bex && bsy == bey)
			return false; //Keine Strecke
		
		//Geradengleichung as + u*(ae-as) = bs +v*(be-bs)
		// u*(ae-as) + v*(bs-be) = bs - as
		// 
		//      u        v
		// [ aex-asx  bsx-bex | bsx-asx ]
		// [ aey-asy  bsy-bey | bsy-asy ]
		//
		//    u    v
		// [ a b | e ]  u = (bf-de) / (bc-ad)
		// [ c d | f ]  v = (af-ec) / (ad-bc)
		//              u = (de-bf) / (ad-bc)              
		//
		float a = aex-asx;
		float b = bsx-bex;
		float c = aey-asy;
		float d = bsy-bey;
		float e = bsx-asx;
		float f = bsy-asy;
		
		float det = a*d - b*c;
		if(det != 0)
		{
			//Schnittpunkt
			float u = (d*e - b*f) / det;
			float v = (a*f - e*c) / det;
			//liegt der Schnittpunkt auf der Strecke?
			if ( u >= 0 && u <= 1 && v >= 0 && v <= 1 ) 
				return true;
			else		
				return false;
		}
		else
		{
			//parallel
			
			//Identisch?
			//Wenn ja, dann sind (as-bs) und (ae-be) parallel.
			//               und (as-be) und (ae-bs)
			//Das gilt, wenn Skalarprodukt / Länge*Länge = +/-1
			float dotProduct1 = (asx-bsx)*(aex-bex) + (asy-bsy)*(aey-bey);
			float lengthSquaredProduct1 = ( (asx-bsx)*(asx-bsx) + (asy-bsy)*(asx-bsy) )
					* ( (aex-bex)*(aex-bex) + (aey-bey)*(aex-bey) );
			
			float dotProduct2 = (asx-bex)*(aex-bsx) + (asy-bey)*(aey-bsy);
			float lengthSquaredProduct2 = ( (asx-bex)*(asx-bex) + (asy-bey)*(asy-bey) )
					* ( (aex-bsx)*(aex-bsx) + (aey-bsy)*(aey-bsy) );
			
			if (dotProduct1 * dotProduct1 == lengthSquaredProduct1
					&& dotProduct2 * dotProduct2 == lengthSquaredProduct2)
			{
				//Geraden parallel und identisch
				if ( asx == bsx && asy == bsy 
						|| asx == bex && asy == bey
						|| aex == bsx && aey == bsy
						|| aex == bex && aey == bey)
					return true; //Geraden teilen sich Start-/End-Punkte
				
				//Sind die Linien verschränkt?
				//x- und y-Anteile werden einzeln überprüft,
				//da die Strecken auf einer Koordinatenachse liegen könnten.
				if (asx < aex)
				{
					if (asx < bsx && bsx < aex || asx < bex && bex < aex )
						return true;
				}
				if (asx > aex)
				{
					if (asx > bsx && bsx > aex || asx > bex && bex > aex )
						return true;
				}
				
				if (asy < aey)
				{
					if (asy < bsy && bsy < aey || asy < bey && bey < aey )
						return true;
				}
				if (asy > aey)
				{
					if (asy > bsy && bsy > aey || asy > bey && bey > aey )
						return true;
				}
				//Die Linien sind nicht verschränkt, also keine Kollision
				return false;
			}
			else
			{
				//Geraden parallel aber nicht identisch
				return false;
			}
		}
		//
		// Es funktioniert; klingt komisch, ist aber so!
		//
	}
	
	/**
	 * Gibt die Orientierung der Linie zur�ck.
	 * (Vom Start zum Endpunkt)
	 */
	public Direction GetOrientation()
	{
		float dx = end.getX() - start.getX();
		float dy = end.getY() - start.getY();
		
		if(dx == 0 && dy == 0)
			return Direction.Stop;
		
		if(dx == 0 || dy/dx  >= 1)
			return start.getY() < end.getY() ? Direction.Up : Direction.Down;
		
		if(dy/dx < 1)
			return start.getX() < end.getX() ? Direction.Right : Direction.Left;
		
		return null; //Das sollte nicht passieren...
	}

	/**
	 * Aktualisiert den Zustand der Linie.
	 */
	@Override
	public void Update(float eleapsedMillis)
	{
		start.Update(eleapsedMillis);
		end.Update(eleapsedMillis);
	}

	/**
	 * Diese Methode leitet �nderungen der zugrundeliegenden
	 * Punkte nach au�en weiter (�ber Oberserver)
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		setChanged();
		if(o == start)
			notifyObservers("start/" + (String)arg);
		else if(o == end)
			notifyObservers("end/" + (String)arg);
		else
			notifyObservers(arg);
	}
}
