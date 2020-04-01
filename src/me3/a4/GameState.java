package me3.a4;

public abstract class GameState {
    public enum State {
        WIN,
        LOSE,
        RUNNING;
    }

    protected State state;

    /**
       @brief Called at each cycle of the program main loop.
     */
    public abstract void update();
    /**
       @brief Called at each player 'move'

       @param n The number of dots consumed.
       @param c The colour of the dots consumed.
     */
    public abstract void update(int n, BoardT.Colour c);

    /**
       @param Return the state of the game.

       @return The current state of the game.
     */
    public State state(){
        return state;
    }

    /**
       @brief Returns if the game is running or not. Check if state == RUNNING.

       @reutrn True of the state of the game is RUNNING, false otherwise.
     */
    public boolean running(){
        return state == State.RUNNING;
    }

    protected int calcScore(int n, BoardT.Colour c){
        return (int) Math.pow(2, n - 1);
    }
}
