package tests.junit;

import static org.junit.Assert.*;

import model.Point;
import model.Rectangle;

import org.junit.Before;
import org.junit.Test;

public class TestRectangle {
	private Rectangle r;
	
	@Before
	public void setUp() {
		r = new Rectangle( new Point(10,20), new Point(30,40) );
	}

	@Test
	public void testConstructorSortPoints() {
		r = new Rectangle( new Point(30,40), new Point(10,20) );
		assertEquals(10, r.getTopLeft().getX(), 0);
		assertEquals(20, r.getTopLeft().getY(), 0);
	}

	@Test
	public void testGetTopLeft() {
		assertEquals(10, r.getTopLeft().getX(), 0);
		assertEquals(20, r.getTopLeft().getY(), 0);
	}

	@Test
	public void testGetBottomRight() {
		assertEquals(30, r.getBottomRight().getX(), 0);
		assertEquals(40, r.getBottomRight().getY(), 0);
	}

	@Test
	public void testGetWidth() {
		assertEquals(20, r.getWidth(), 0);
	}

	@Test
	public void testGetHeight() {
		assertEquals(20, r.getHeight(), 0);
	}

}
