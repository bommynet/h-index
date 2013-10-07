package tests.junit;

import static org.junit.Assert.*;

import model.BorderCollisionType;
import model.Line;
import model.MovingPoint;
import model.Point;
import model.Rectangle;

import org.junit.Test;

public class TestRectContains {

	@Test
	public void testContainsPoint() {
		//Test Rectangle
		Rectangle r = new Rectangle( new Point(0,0), new Point(10,10) );
		//Test Point: sollte mit (5,5) im Rechteck ( (0,0)->(10,10) ) liegen
		Point p1 = new Point(5,5);
		//Test Point: sollte mit (15,15) nicht im Rechteck ( (0,0)->(10,10) ) liegen
		Point p2 = new Point(15,15);
		
		//Teste Funktion
		assertEquals(true, r.Contains(p1));
		assertEquals(false, r.Contains(p2));
	}

	@Test
	public void testContainsRectangle() {
		//Test Rectangle
		Rectangle r = new Rectangle( new Point(0,0), new Point(10,10) );
		//Test Rectangle: sollte mit ( (3,3)->(6,6) ) im Rechteck ( (0,0)->(10,10) ) liegen
		Rectangle r1 = new Rectangle( new Point(3,3), new Point(6,6) );
		
		//Teste Funktion
		assertEquals(true, r.Contains(r1));
	}

	@Test
	public void testContainsLine() {
		//Test Rectangle
		Rectangle r = new Rectangle( new Point(0,0), new Point(10,10) );
		//Test Line: sollte mit (3,3)->(6,6) im Rechteck liegen
		Line l = new Line(new Point(3,3), new Point(6,6));
		//Test ausfuehren
		assertEquals(true,r.Contains(l));
	}

	@Test
	public void testFindBorderCollisionType() {
		//Test Rectangle
		Rectangle r = new Rectangle( new Point(0,0), new Point(10,10) );
		//Test MovingPoint: steht 1px rechts neben dem Rectangle und hat eine
		//x-Geschwindigkeit von -2
		MovingPoint p = new MovingPoint(11, 6, -2, 0);
		//Bewegung ausfuehren (ms=1000) fuer genau 2px Schritt
		p.Update(1000);
		//Test ausfuehren
		assertEquals(BorderCollisionType.Right, r.FindBorderCollsionType(p));
	}

}
