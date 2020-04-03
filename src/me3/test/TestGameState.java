package me3.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import me3.a4.BoardT;
import me3.a4.GameState;
import me3.a4.StateMoves;
import me3.a4.StateScore;
import me3.a4.StateTime;

public class TestGameState {

    private StateScore stateScore;
    private StateMoves stateMoves;

    @Before
    public void init(){
        stateScore = new StateScore(10);
        stateMoves = new StateMoves(10, 3);
    }

    @Test
    public void timeLose(){
        StateTime stateTime = new StateTime(1, 10);
        while (stateTime.running()){
            stateTime.update();
        }
        assertEquals(GameState.State.LOSE, stateTime.state());
    }

    @Test
    public void timeWin(){
        StateTime stateTime = new StateTime(1, 10);
        while (stateTime.running())
            stateTime.update(5, BoardT.Colour.RED);
        assertEquals(GameState.State.WIN, stateTime.state());
    }

    @Test
    public void timeTimer(){
        StateTime stateTime = new StateTime(1, 10);
        double curTime = System.currentTimeMillis();
        double endTime = curTime + 1*1000;
        while (endTime > curTime){
            curTime = System.currentTimeMillis();
            stateTime.update();
        }
        stateTime.update();
        assertEquals(GameState.State.LOSE, stateTime.state());
    }

    @Test
    public void movesWin(){
        stateMoves.update(3, BoardT.Colour.RED);
        stateMoves.update(3, BoardT.Colour.RED);
        stateMoves.update(3, BoardT.Colour.RED);
        assertEquals(GameState.State.WIN, stateMoves.state());
    }

    @Test
    public void movesLose(){
        stateMoves.update(1, BoardT.Colour.RED);
        stateMoves.update(1, BoardT.Colour.RED);
        stateMoves.update(1, BoardT.Colour.RED);
        assertEquals(GameState.State.LOSE, stateMoves.state());
    }

    @Test
    public void movesRunning(){
        stateMoves.update(1, BoardT.Colour.RED);
        stateMoves.update(1, BoardT.Colour.RED);
        assertEquals(GameState.State.RUNNING, stateMoves.state());
    }

    @Test
    public void scoreRunning(){
        stateScore.update();
        stateScore.update(1, BoardT.Colour.BLUE);
        assertEquals(GameState.State.RUNNING, stateScore.state());
    }

    @Test
    public void scoreWin(){
        stateScore.update(6, BoardT.Colour.BLUE);
        assertEquals(GameState.State.WIN, stateScore.state());
    }

}
