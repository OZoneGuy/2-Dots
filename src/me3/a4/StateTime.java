package me3.a4;
/**
   @brief The file containing the class for the Time mode. Extends GameState.
   @author Omar Alkersh - alkersho
   @file StateTime.java
   @date 2020-03-28
 */

/**
   @brief The class for the Time mode. Extends GameState.
*/
public class StateTime extends GameState {

    private final double endTime;
    private double curTime;
    private int score;
    private int scoreGoal;
    private boolean paused;

    /**
       @brief Constructor for StateTime, the class responsible for the timed mode

       @param time The time length of the round
       @param score The score goal required to win.
     */
    public StateTime(int time, int score){
        super();
        this.curTime = System.currentTimeMillis();
        this.endTime = curTime + time*1000;
        this.score = 0;
        this.scoreGoal = score;
        this.paused = false;
    }

    /**
       @brief Updates the time and updates the state as necessary
     */
    public void update(){
        curTime = System.currentTimeMillis();
        if (curTime >= endTime)
            if (score >= scoreGoal)
                state = GameState.State.WIN;
            else
                state = GameState.State.LOSE;
    }

    /**
       @brief Same as update() but also updates the score.

       @param n the number of dots consumed
       @param c the colour of the dots consumed
     */
    public void update(int n, BoardT.Colour c){
        curTime = System.currentTimeMillis();
        score += super.calcScore(n, c);
        if (score >= scoreGoal)
            state = GameState.State.WIN;
        if (curTime >= endTime)
            if (score >= scoreGoal)
                state = GameState.State.WIN;
            else
                state = GameState.State.LOSE;
    }

    /**
       @return gets the game score
     */
    public int getScore(){
        return score;
    }

    /**
       @brief calculates the remaining time and returns it

       @reutrn the remaining time.
     */
    public double getRemTime(){
        return (endTime - curTime) / 1000;
    }

    /**
       @brief Sets pause to false, game is running. WIP
     */
    public void unPause(){
        paused = false;
    }

    /**
       @brief Sets pause to true, pauses the game. WIP
     */
    public void pause(){
        paused = true;
    }

    /**
       @return True if the game is paused, pause == true
     */
    public boolean isPaused(){
        return paused;
    }
}
