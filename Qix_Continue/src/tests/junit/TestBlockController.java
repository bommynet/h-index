package tests.junit;

import static org.junit.Assert.*;

import model.Line;
import model.Point;
import model.Rectangle;

import org.junit.Before;
import org.junit.Test;

import controller.BlockController;

public class TestBlockController {
	private BlockController bc;
	
	@Before
	public void setUp() {
		bc = new BlockController( null );
	}
	
	/**
	 * Ueberpruefen, ob wirklich 4 Bloecke erstellt wurden.
	 */
	@Test
	public void testCountBlocks() {
		assertEquals(4, bc.countBlocks());
	}

	/**
	 * 
	 */
	@Test
	public void testGetAvailableArea() {
		Rectangle r = bc.getAvailableArea();
		assertEquals( 0, r.getTopLeft().getX(), 0 );
		assertEquals( 0, r.getTopLeft().getY(), 0 );
		assertEquals( 800, r.getBottomRight().getX(), 0 );
		assertEquals( 600, r.getBottomRight().getY(), 0 );
	}

	/**
	 * 
	 */
	@Test
	public void testConquerArea() {
		//Eine Linie ausserhalb des Spielfelds setzen
		boolean conquer1 = bc.conquerArea( new Line( new Point(0,-10), new Point(800,-10)) );
		assertEquals( false, conquer1 );
		//2 Bereiche erobern...
		//...erster gültig
		boolean conquer2 = bc.conquerArea( new Line( new Point(0,10), new Point(800,10)) );
		assertEquals( true, conquer2 );
		//...zweiter im bereits eroberten Bereich und somit ungültig
		boolean conquer3 = bc.conquerArea( new Line( new Point(0,5), new Point(800,5)) );
		assertEquals( false, conquer3 );

		//und die neue Größe abfragen
		Rectangle r = bc.getAvailableArea();
		assertEquals( 0, r.getTopLeft().getX(), 0 );
		assertEquals( 10, r.getTopLeft().getY(), 0 );
		assertEquals( 800, r.getBottomRight().getX(), 0 );
		assertEquals( 600, r.getBottomRight().getY(), 0 );

		//zu kurze Linie zum Berechnen schicken
		boolean conquer4 = bc.conquerArea( new Line( new Point(100,20), new Point(100,50)) );
		assertEquals( true, conquer4 );
		
		//und die neue Größe erneut abfragen
		r = bc.getAvailableArea();
		assertEquals( 100, r.getTopLeft().getX(), 0 );
		assertEquals( 10, r.getTopLeft().getY(), 0 );
		assertEquals( 800, r.getBottomRight().getX(), 0 );
		assertEquals( 600, r.getBottomRight().getY(), 0 );
	}

}
