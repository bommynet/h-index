package controller;

import java.util.ArrayList;

import config.Config;

import view.GameWindow;
import view.draw.ImageDraw;
import model.BorderCollisionType;
import model.Enemy;
import model.Rectangle;

/**
 * Diese Klasse stellt einen Controller f&uuml;r die Gegner dar. Dieser erstellt
 * pro Gegner ein Model- und ein Grafik-Objekt und f&uuml;gt sie in die richtigen
 * Listen ein. In diesem Controller werden die Gegner gesteuert, auf Kollision
 * gepr&uuml;ft und entfernt wenn n&ouml;tig.
 * @author Thomas Laarmann
 * @version 130629
 */
public class EnemyController implements Runnable {
//### KONSTANTEN ####################################################
	/** Gegnertyp = Zufall */
	public final static int RANDOM = 0;
	/** Gegnertyp = Standard */
	public final static int BASIC = 1;
	
//### VARIABLEN #####################################################
	/** View muss bei Controller bekannt sein */
	private GameWindow view;
	/** View muss auch die Bl&ouml;cke kennen */
	private ArrayList<Rectangle> blocks;
	/** eine Liste von Gegner-Modellen */
	private ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	
//### KONSTRUKTOREN #################################################
	/**
	 * Erstellt einen Standardgegner, welcher von der Mitte des Spielfelds
	 * startet und im 45&deg;-Winkel nach rechts oben beschleunigt.
	 * @param view der View!
	 */
	public EnemyController(GameWindow view) {
		this.view = view;
		this.createEnemy( (view.getWidthPF()/2), (view.getHeightPF()/2), Config.ENEMY_SPEED, (-1)*Config.ENEMY_SPEED );
	}
	
	/**
	 * Erstellt eine bestimmte Anzahl an Gegnern mit einem gegebenen Bewegungsmuster.
	 * @param view der View!
	 * @param anzahl Anzahl der zu erstellenden Gegner
	 * @param typ Bewegungstyp der Gegner
	 */
	public EnemyController(GameWindow view, int anzahl, int typ) {
		this.view = view;
		//nach "typ" unterscheiden und Routinen starten
		if( typ == BASIC ) {
			float veloX = Config.ENEMY_SPEED; float veloY = (-1)*Config.ENEMY_SPEED;
			for(int i=0; i<anzahl; i++) {
				this.createEnemy( (view.getWidthPF()/2), (view.getHeightPF()/2), veloX, veloY);
				//der Reihe nach im Uhrzeigersinn beschleunigen
				if(veloX > 0 && veloY < 0)
					veloY *= -1;
				else if(veloX > 0 && veloY > 0)
					veloX *= -1;
				else if(veloX < 0 && veloY > 0)
					veloY *= -1;
				else if(veloX < 0 && veloY < 0)
					veloX *= -1;
			}
		} else if( typ == RANDOM ) {
			for(int i=0; i<anzahl; i++) {
				createEnemyRandom();
			}
		}
	}
	
//### FUNKTIONEN ####################################################
	/**
	 * Erstellt einen Gegner an einer zuf&auml;lligen Position mit einer
	 * zuf&auml;lligen Laufrichtung.
	 */
	private void createEnemyRandom() {
		//zufaellig irgendwo aufs Spielffeld setzen
		float x = (float)Math.random() * (view.getWidthPF() - 40);
		float y = (float)Math.random() * (view.getHeightPF() - 40);
		
		//Beschleunigung X-Achse
		float veloX = ((float)Math.random() * Config.ENEMY_SPEED_DELTA) + Config.ENEMY_SPEED;
		//50:50 Chance, dass veloX negativ wird
		veloX = veloX * (Math.random()>0.5 ? 1 : -1);
		
		//Beschleunigung Y-Achse
		float veloY = ((float)Math.random() * Config.ENEMY_SPEED_DELTA) + Config.ENEMY_SPEED;
		//50:50 Chance, dass veloY negativ wird
		veloY = veloY * (Math.random()>0.5 ? 1 : -1);
		
		//Gegnerobjekt erstellen
		this.createEnemy( x, y, veloX, veloY );
	}
	
	/**
	 * Erstellt einen neuen Gegner (Model + View) mit den &uuml;bergebenen Werten
	 * und f&uuml;gt die Komponenten in die List enemy und in den View ein.
	 * @param x x-Position des Gegners auf den Spielfeld
	 * @param y y-Position des Gegners auf dem Spielfeld
	 * @param veloX Tempo des Gegners in x-Richtung
	 * @param veloY Tempo des Gegners in y-Richtung
	 */
	private void createEnemy(float x, float y, float veloX, float veloY) {
		//Model des neuen Gegners erstellen
		Enemy newEmy = new Enemy( x, y, veloX, veloY );
		//Grafik des neuen Gegners erstellen
		ImageDraw newEmyDraw = new ImageDraw( Config.ENEMY_IMG, 40, 40 );
		//Beobachter einrichten
		newEmy.addObserver( newEmyDraw );
		//Model in Liste einfuegen
		enemy.add( newEmy );
		//Grafik in Liste einfuegen
		view.addEnemy( newEmyDraw );
	}
	
	/**
	 * &Uuml;bergibt den Pointer zur Liste, welche die Bl&ouml;cke enth&auml;lt.
	 * Wird f&uuml;r die Kollisionsabfrage benutzt.
	 * @param pBlock 
	 */
	public void setBlockPointer(ArrayList<Rectangle> pBlock) {
		this.blocks = pBlock;
	}
	
	/**
	 * Gibt einen Pointer zur Liste der Gegnerobjekte zur&uuml;ck.
	 * @return ArrayList von Gegnern
	 */
	public ArrayList<Enemy> getEnemyList() {
		return enemy;
	}
	
	/**
	 * Gibt die Anzahl der aktuell vorhandenen Gegner zur&uuml;ck.
	 * @return Anzahl der Gegner
	 */
	public int countEnemy() {
		return enemy.size();
	}
	
	/**
	 * Gibt einen Pointer zum EnemyThread zur&uuml;ck, um ihn zu starten,
	 * wenn <i>alles</i> vorbereitet ist.
	 * @return EnemyThread
	 */
	public Thread getEnemyThread() {
		return new Thread( this );
	}
	
	/**
	 * Testet einen Gegner auf Collision mit den Block-Elementen.
	 * @param e zu testender Gegner
	 */
	private void testForCollision(Enemy e) {
		//jeden Block f&uuml;r Kollisionen abfragen
		for(int j=0; j<blocks.size(); j++) {
			//Test auf Kollision...
			BorderCollisionType bct = blocks.get(j).FindBorderCollsionType( e );
			//...und reagieren, wenn nicht nichts passiert.
			if(!(bct == BorderCollisionType.None)) {
				switch( bct ) {
					case Bottom: e.SwitchYDirection(); break;
					case Top:    e.SwitchYDirection(); break;
					case Left:   e.SwitchXDirection(); break;
					case Right:  e.SwitchXDirection(); break;
					case Contained: killEnemy(e); break; //Gegner zerst�ren wenn eingekesselt!
					default:     break;
				}
			}
			//DEBUG!
			if( Config.ENEMY_COLLIDE )
				if(!(bct == BorderCollisionType.None)) System.out.println( bct + " X:"+
						e.getX() + "|Y:" + e.getY() );
			//DEBUG!
		}
	}

	/**
	 * Zerst&ouml;rt den &uuml;bergebenden Gegner: Meldet ihn aus den Observern
	 * ab und entfernt das Modell.
	 * @param e der zu entfernende Gegner
	 */
	private void killEnemy(Enemy e) {
		//Beobachter entfernen
		e.deleteObservers();
		//Grafik zerstören
		view.removeEnemy( enemy.indexOf(e) );
		//Modell zerstören
		enemy.remove(e);
	}

//### OVERRIDES: Interface ##########################################
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while( Config.running ) {
			//einmal pro Durchlauf schlafen (am Anfang wegen >continue<-Anweisungen)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//nichts machen, wenn noch keine Gegner vorhanden sind
			if(enemy.isEmpty()) continue;
			//jeden Gegner updaten und auf Kollision testen
			for(int i=0; i<enemy.size(); i++) {
				enemy.get(i).Update( Config.ENEMY_UPDATE );
				
				if( blocks != null ) {
					//nichts machen, wenn keine Bloecke vorhanden sind
					if(blocks.isEmpty()) continue;
					//Kollisionsabfrage für Gegner(i) ausführen
					this.testForCollision( enemy.get(i) );
				}
			}
		}
	}
}
