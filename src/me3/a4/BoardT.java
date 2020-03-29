package me3.a4;

import java.util.List;
import java.util.Map;
import java.util.Random;


/**
   @brief A module which contains an ADT to represent the game board.
   @file BoardT.java
   @author Omar Alkersh - alkerhso
   @data 2020-03-28
*/

/**
   @brief An ADT to represent the game board.
*/
public class BoardT {
    public enum Colour {
        BLUE,
        GREEN,
        PINK,
        RED,
        ORANGE;
    }

    public final int SIZE = 6;

    private List<List<Colour>> b;
    private GameState state;

    /**
       @brief Get the colour of the dot in location (i, j)

       @param i The row number.
       @param j The column number.

       @return The Colour of the dot at the location.
    */
    public Colour getCell (int i, int j){
        return b.get(i).get(j);
    }

    /**
       @brief Consumes the dots at the given locations on the list.

       @param pos The positions to consume.
    */
    public void consume (Map<Integer, Integer> pos){
        Colour c = Colour.BLUE;
        int n = 0;
        // set the consumed locations to null
        for (int i:pos.keySet()){
            int j = pos.get(i);
            if (c != Colour.BLUE)
                if (getCell(i, j) != c)
                    throw new IllegalArgumentException("The locations specified do not have the same colour.");
            c = getCell(i, j);
            b.get(i).set(j, null);
            n++;
        }

        // update the board.
        updateBoard();
        // update the game state
        state.update(n, c);
    }

    /**
       @brief updates the board after it has items have been consumed. Moves dots down if they have a null object below them and repopulates the board.
     */
    public void updateBoard(){
        for (int i = SIZE - 2 ; i >= 0; i--)
            for (int j = SIZE - 1; j >=0; j--)
                if (getCell(i + 1, j) == null){
                    b.get(i + 1).set(j, b.get(i).get(j));
                    b.get(i).set(j, null);
                }
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (b.get(i).get(j) == null)
                    b.get(i).set(j, randomColour());
    }

    private Colour randomColour(){
        Random rand = new Random();
        double r = rand.nextDouble();
        if (r < 0.2)
            return Colour.BLUE;
        if (r < 0.4)
            return Colour.GREEN;
        if (r < 0.6)
            return Colour.ORANGE;
        if (r < 0.8)
            return Colour.PINK;
        return Colour.RED;
    }
}
