package me3.a4;

public class StateScore extends GameState {
    private int score;
    private int scoreGoal;

    public StateScore(int score){
        super();
        this.scoreGoal = score;
        this.score = 0;
    }

    public void update(){
        return;
    }

    public void update(int n, BoardT.Colour c){
        this.score += super.calcScore(n, c);
        if (score >= scoreGoal)
            state = GameState.State.WIN;
        else
            state = GameState.State.LOSE;
    }

    public int getScore(){
        return this.score;
    }

    public int getMaxScore(){
        return this.scoreGoal;
    }

}
