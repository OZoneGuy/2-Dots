package me3.a4;

public class StateMoves extends GameState {
    private int moves;
    private int maxMoves;
    private int score;
    private int scoreGoal;

    public StateMoves(int score, int moves){
        super();
        this.score     = 0;
        this.moves     = 0;
        this.maxMoves  = moves;
        this.scoreGoal = score;
    }

    public void update(){
        return;
    }

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

    public int getScore(){
        return score;
    }

    public int getScoreGoal(){
        return scoreGoal;
    }

    public int getRemMoves(){
        return maxMoves - moves;
    }
}
