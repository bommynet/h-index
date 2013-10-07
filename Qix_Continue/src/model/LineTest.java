package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineTest {

	@Test
	public void test()
	{
		Line l1 = new Line(new Point(0,0), new Point(0,2));
		Line l2 = new Line(new Point(-1,0), new Point(1,0));
		assertEquals(l1.CollidesWith(l2), true);
	}

}
