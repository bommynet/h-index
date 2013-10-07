package config;

import java.awt.Color;

import model.MusicStyle;

import controller.EnemyController;


/**
 * Die Config soll alle wichtigen Einstellungen zentriert zur Verf&uuml;gung stellen.
 * @version 130629
 */
public class Config {
//### Initialisierung ############################################### 
	/** Initialisiert den EnemyController mit dieser Anzahl an Gegnern */
	public static final int NEW_ENEMY_COUNT = 2;
	/** Initialisiert den EnemyController mit = MUSIC_STYLE.OFF; diesem GegnerTyp */
	public static final int NEW_ENEMY_TYPE = EnemyController.RANDOM;
	
//### Spielfeld #####################################################
	/** Spielfeldbreite */
	public static final int WIDTH = 800;
	/** Spielfeldh&ouml;he */
	public static final int HEIGHT = 600;
	/** Hintergrundfarbe des Spielfelds */
	public static final Color PLAYFIELD_COLOR = Color.BLACK;
	
//### Gegner ########################################################
	/** Minimale Geschwindigkeit der Gegner in px/s */
	public static final float ENEMY_SPEED = 150;
	/** Maximale Abweichung der Geschwindigkeit der Gegner in px/s */
	public static final float ENEMY_SPEED_DELTA = 300;
	/** H&auml;ufigkeit der Aktualisierung */
	public static final float ENEMY_UPDATE = 4;
	/** Bilddatei der Gegner */
	public static final String ENEMY_IMG = "bunt.png";

//### Bloecke #######################################################
	/** Farbe der Rand- und eroberten Bl&ouml;cke */
	public static final Color BLOCK_COLOR = Color.YELLOW;
	
//### Herzleiste ####################################################
	/** X-Position der Lebensanzeige */
	public static final int HEARTS_POSX = 670;
	/** Y-Position der Lebensanzeige */
	public static final int HEARTS_POSY = 50;
	/** Gr&ouml;&szlig;e der Lebensanzeige */
	public static final int HEARTS_SIZE = 40;

//### Musik #######################################################
	public static MusicStyle MUSIC_STYLE = MusicStyle.Off;
	
//### Sonstiges #####################################################
	/** Erm&ouml;glicht die Abschaltung aller Threads &uuml;ber diese Variable */
	public static boolean running = true;
	
//### DEBUG #########################################################
	/** true: gibt auf der Console aus, wie und wo ein Gegner kollidiert ist */
	public static final boolean ENEMY_COLLIDE = false;
	
//### KONSTRUKTOR ###################################################
	/** Config() kann nicht initialisiert werden */
	private Config() {}
}


