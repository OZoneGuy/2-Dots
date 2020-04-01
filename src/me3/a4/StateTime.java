package me3.a4;

public class StateTime extends GameState {

    private double endTime;
    private double curTime;
    private int score;
    private int scoreGoal;
    private boolean paused;

    public StateTime(int time, int score){
        super();
        this.curTime = System.currentTimeMillis() / 1000;
        this.endTime = curTime + time;
        this.score = 0;
        this.scoreGoal = score;
        this.paused = false;
    }

    public void update(){
        curTime = System.currentTimeMillis() / 1000;
        if (curTime >= endTime)
            if (score >= scoreGoal)
                state = GameState.State.WIN;
            else
                state = GameState.State.LOSE;
    }

    public void update(int n, BoardT.Colour c){
        curTime = System.currentTimeMillis() / 1000;
        score += super.calcScore(n, c);
        if (score >= scoreGoal)
            state = GameState.State.WIN;
        else
            state = GameState.State.LOSE;
    }

    public int getScore(){
        return score;
    }

    public double getRemTime(){
        return endTime - curTime;
    }

    public void unPause(){
        paused = false;
    }

    public void pause(){
        paused = true;
    }

    public boolean isPaused(){
        return paused;
    }
}
