package me3.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import me3.a4.BoardT;
import me3.a4.StateScore;


public class TestBoardT {

    private BoardT b;

    @Before
    public void init(){
        b = new BoardT(new StateScore(10));
    }

    @Test
    public void nonNullBoard(){
        for (int i = 0; i < BoardT.SIZE; i++)
            for (int j = 0; j < BoardT.SIZE; j++)
                assertNotNull(b.getCell(i, j));
    }

    @Test
    public void testConsume(){
        ArrayList<Point> cs = new ArrayList<>();
        cs.add(new Point(0,0));
        b.consume(cs);
        assertNotNull(b.getCell(0, 0));
    }

    @Test
    public void testConsume2(){
        ArrayList<BoardT.Colour> dots = new ArrayList<>();
        for (int i = 0; i < 5; i ++)
            dots.add(b.getCell(i, 3));
        ArrayList<Point> cs = new ArrayList<>();
        cs.add(new Point(3,5));
        b.consume(cs);
        for (int i = 1; i < 6; i ++)
            assertTrue(dots.contains(b.getCell(i, 3)));

    }

}
