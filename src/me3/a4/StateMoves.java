package me3.a4;
/**
   @brief The file containing the class for the Moves mode, extends GameState.
   @authot Omar Alkersh - alkersho
   @file StateMoves.java
   @date 2020-03-28
 */

/**
   @brief The class for the Moves mode, extends GameState.
 */
public class StateMoves extends GameState {
    private int moves;
    private int maxMoves;
    private int score;
    private int scoreGoal;

    /**
       @brief Constructor for StateMoves, the class responsible for the moves mode.

       @param score The score goal required to win.
       @param moves The maximum amount of moves.
     */
    public StateMoves(int score, int moves){
        super();
        this.score     = 0;
        this.moves     = 0;
        this.maxMoves  = moves;
        this.scoreGoal = score;
    }

    /**
       @brief does nothing here.
     */
    public void update(){
        return;
    }

    /**
       @brief Updates the score and the move count.

       @param n The number of  dots consumed.
       @param c The colour of the dots.
     */
    public void update(int n, BoardT.Colour c){
        score += super.calcScore(n, c);
        moves++;
        if (score >= scoreGoal)
            state = GameState.State.WIN;
        if (moves >= maxMoves)
            if (score >= scoreGoal)
                state = GameState.State.WIN;
            else
                state = GameState.State.LOSE;
    }

    /**
       @return the current score
     */
    public int getScore(){
        return score;
    }

    /**
       @return the Score goal.
     */
    public int getScoreGoal(){
        return scoreGoal;
    }

    /**
       @return The number of remaining moves.
     */
    public int getRemMoves(){
        return maxMoves - moves;
    }
}
