package me3.a4;

/**
   @brief The file containing the class for the Score mode. Extends GameState.
   @author Omar Alkersh - alkersho
   @file StateScore.java
   @date 2020-03-28
*/

/**
   @brief The class for the Score mode. Extends GameState.
*/
public class StateScore extends GameState {
    private int score;
    private int scoreGoal;

    /**
       @brief The Constructor for StateScore, the class responsible for the Score mode.

       @param score The goal score.
     */
    public StateScore(int score){
        super();
        this.scoreGoal = score;
        this.score = 0;
    }

    /**
       @brief does nothing.
     */
    public void update(){
        return;
    }

    /**
       @breif Updates the score and the game state as necessary.
     */
    public void update(int n, BoardT.Colour c){
        this.score += super.calcScore(n, c);
        if (score >= scoreGoal)
            state = GameState.State.WIN;
    }

    /**
       @return the current score.
     */
    public int getScore(){
        return this.score;
    }

    /**
       @return the score goal.
     */
    public int getMaxScore(){
        return this.scoreGoal;
    }

}
