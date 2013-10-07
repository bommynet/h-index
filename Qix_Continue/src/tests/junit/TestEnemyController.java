package tests.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.GameWindow;

import controller.EnemyController;

public class TestEnemyController {
	private GameWindow win;
	
	/*
	 * WIRFT EINE EXCEPTION AUF DER KONSOLE AUS, DA ER IN EINEM NICHT VORHANDENEN
	 * BILD ZEICHNEN WILL. IST ALSO NORMAL. :)
	 */
	
	@Before
	public void setUp() {
		//Fenster erstellen
		win = new GameWindow(  );
	}

	/**
	 * Testen, ob die richtige Anzahl an Gegnern erstellt wird.
	 */
	@Test
	public void testCountEnemy() {
		//Gegner erstellen
		EnemyController ec = new EnemyController( win );
		assertEquals(1, ec.countEnemy());
		
		ec = new EnemyController( win, 5, EnemyController.BASIC );
		assertEquals(5, ec.countEnemy());
		
		ec = new EnemyController( win, 5, EnemyController.RANDOM );
		assertEquals(5, ec.countEnemy());
	}

}
