package tests.junit;

import static org.junit.Assert.*;

import model.Direction;
import model.Line;
import model.Point;

import org.junit.Test;

public class TestLine
{

	@Test
	public void testGetOrientation()
	{
		Line a = new Line(new Point(0, 0), new Point(5, 10));
		Line b = new Line(new Point(0, 0), new Point(11, 10));
		
		assertEquals(a.GetOrientation(), Direction.Up);
		assertEquals(b.GetOrientation(), Direction.Right);
	}

}
