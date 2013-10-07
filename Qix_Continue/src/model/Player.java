package model;

/**
 * Diese Klasse stellt den Spieler als MovingPoint dar.
 * Sie verf�gt �ber ein paar Hilfmethoden, die die Steuerung des
 * Spielers erleichtern und einen Variable f�r die Anzahl der Leben
 * des Spielers.
 * @author Niklas Rother
 */
public class Player extends MovingPoint
{
	/**
	 * Konstante, die von den Methode SetDirection benutzt wird.
	 * Gibt die Geschwindigkeit des Spielers in px/sec an.
	 */
	public static final float SPEED_X = 150.0f;
	/**
	 * Siehe SPEED_X.
	 */
	public static final float SPEED_Y = 150.0f;
	
	/**
	 * Anzahl der Leben, die der Spieler bei der Erzeugung hat.
	 */
	public static final int START_LIVES = 3;
	
	private int livesLeft = START_LIVES;
	private Direction lastDirection = Direction.Stop;
	
	/**
	 * Soll anzeigen, ob der Spieler innerhalb des Spielfelds ist. 
	 * Wenn true kann der Spieler die Richtung nicht �ndern.
	 */
	private boolean inField = false;
	
	/**
	 * Gibt die Anzahl der verbleibenden Leben zur�ck.
	 */
	public int getLivesLeft()
	{
		return livesLeft;
	}
	
	public boolean isInField()  {
		return this.inField;
	}
	
	public void toggleInField() {
		inField = !inField;
	}
	
	public void setInField(boolean inField) {
		this.inField = inField;
	}
	
	/**
	 * Setzt die Anzahl der verbleibenden Leben.
	 */
	protected void setNumLives(int livesLeft)
	{
		this.livesLeft = livesLeft;
		setChanged();
		notifyObservers("lives");
	}
	
	/**
	 * Entfernt ein Leben, und gibt true zur�ck, wenn noch
	 * mindestens ein Leben verbleibt, ansonsten false.
	 */
	public boolean removeOneLive()
	{
		livesLeft--;
		setChanged();
		notifyObservers("lives");
		return livesLeft > 0;
	}
	
	/**
	 * Gibt die letzte Bewegungsrichtung zur�ck.
	 */
	public Direction getLastDirection()
	{
		return lastDirection;
	}
	
	/**
	 * Erzeugt eine neue Instanz der Player-Klasse an der
	 * angeben Position und der Geschwindigkeit null.
	 */
	public Player(float x, float y)
	{
		super(x, y, 0, 0);
	}
	
	/**
	 * Hilfs-Methode um die Bewegungsrichtung des Spielers einfach �ndern
	 * zu k�nnen. Setzt VelX/Y auf richtigen Werte aus SPEED_X/Y je nach
	 * Bewegungsrichtung.
	 * Stop h�lt den Spieler an. Diese Mehtode ist f�r die Steuerung per Tastatur gedacht.
	 */
	public void SetDirection(Direction direction)
	{
		switch (direction)
		{
			case Stop:
				setVelX(0);
				setVelY(0);
				break;
			case Up:
				setVelX(0);
				setVelY(-SPEED_Y);
				break;
			case Down:
				setVelX(0);
				setVelY(SPEED_Y);
				break;
			case Left:
				setVelX(-SPEED_X);
				setVelY(0);
				break;
			case Right:
				setVelX(SPEED_X);
				setVelY(0);
				break;
		}
		if (!inField) {
		lastDirection = direction;
		}
		
	}
	/**
	 * H�lt den Spieler sofort an.
	 * (Es wird empfolen, stattdessen SetDirection(Stop) zu benutzen)
	 */
	@Override
	public void Stop()
	{
		super.Stop();
		lastDirection = Direction.Stop;
	}
	
	/**
	 * Autor: Kolja
	 * Die Spielerbewegung.
	 * Der Spieler bewegt sich in die Richtung der zuletzt gedr�ckten Taste.
	 * Wird eine Taste losgelassen, bewegt er sich in Richtung der verbliebenen Richtungstaste.
	 * Edit: Der Spieler kann nun innerhalb des Feldes (inField == true) nicht mehr 
	 * die Richtung wechseln.
	 */
	public void doMovement(boolean left, boolean right, boolean up, boolean down)
	{
		if(inField) {
			if (getLastDirection()==Direction.Right) {
				if(right) {
					SetDirection(Direction.Right);
				} else {
					SetDirection(Direction.Stop);
				}
			}
			else if (getLastDirection()==Direction.Left) {
				if(left) {
					SetDirection(Direction.Left);
				} else {
					SetDirection(Direction.Stop);
				}
			}	
			else if (getLastDirection()==Direction.Up) {
				if(up) {
					SetDirection(Direction.Up);
				} else {
					SetDirection(Direction.Stop);
				}
			}
			else if (getLastDirection()==Direction.Down) {
				if(down) {
					SetDirection(Direction.Down);
				} else {
					SetDirection(Direction.Stop);
				}
			}
		} else {
			if (getLastDirection()==Direction.Right) {
			if(left)
				SetDirection(Direction.Stop);
			else if(up)
				SetDirection(Direction.Up);
			else if(down)
				SetDirection(Direction.Down);
			else if(right)
				SetDirection(Direction.Right);
			else
				SetDirection(Direction.Stop);
		}
			else if (getLastDirection()==Direction.Left) {
				if(right)
					SetDirection(Direction.Stop);
				else if(up)
					SetDirection(Direction.Up);
				else if(down)
					SetDirection(Direction.Down);
				else if(left)
					SetDirection(Direction.Left);
				else
					SetDirection(Direction.Stop);
			}
			else if (getLastDirection()==Direction.Up) {
				if(right)
					SetDirection(Direction.Right);
				else if(left)
					SetDirection(Direction.Left);
				else if(down)
					SetDirection(Direction.Stop);
				else if(up)
					SetDirection(Direction.Up);
				else
					SetDirection(Direction.Stop);
			}
			else if (getLastDirection()==Direction.Down) {
				if(right)
					SetDirection(Direction.Right);
				else if(up)
					SetDirection(Direction.Stop);
				else if(left)
					SetDirection(Direction.Left);
				else if(down)
					SetDirection(Direction.Down);
				else
					SetDirection(Direction.Stop);
			}
			else if (getLastDirection()==Direction.Stop) {
				if(right)
					SetDirection(Direction.Right);
				else if(left)
					SetDirection(Direction.Left);
				else if(up)
					SetDirection(Direction.Up);
				else if(down)
					SetDirection(Direction.Down);
				else
					SetDirection(Direction.Stop);
			}
		}
	}
	
	public void setPos(Point pos) {
		this.setX(pos.getX());
		this.setY(pos.getY());
		setChanged();
		notifyObservers();
	}
}
