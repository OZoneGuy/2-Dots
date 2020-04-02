package me3.a4;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import me3.a4.view.View;

public class Controller {

    private BoardT board;
    private GameState state;
    private View view;
    private List<Point> connections;
    private boolean connecting;

    public Controller() {
        this.view = new View(winListener, pauseNewA, exitA);
    }

    public static void main(String[] args) {
        new Controller();
    }

    private void run() {
        view.startGame(state, board, gameMouseListener, gameKeyListener);
        connecting = false;
        connections = new ArrayList<Point>();
        Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (state.running()) {
                       state.update();
                        view.updateGameLabels();
                        if (!state.running())
                            if (state.state() == GameState.State.WIN)
                                view.showWin();
                            else
                                view.showLose();
                    }
                }
            };

        Thread t = new Thread(runnable);
        t.start();

    }

    private WindowListener winListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                view.showPause(pauseContA, pauseNewA, exitA);
            }
        };

    private ActionListener pauseNewA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.gameMenu(GMtimerA, GMmovesA, GMscoreA);
            }
        };

    private ActionListener pauseContA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    StateTime tState = (StateTime) state;
                    tState.unPause();
                } catch (ClassCastException ignore) {

                }
            }
        };

    private ActionListener exitA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };

    private ActionListener GMtimerA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = new StateTime(60, 20);
                board = new BoardT(state);
                run();
            }
        };

    private ActionListener GMmovesA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = new StateMoves(20, 20);
                board = new BoardT(state);
                run();
            }
        };

    private ActionListener GMscoreA = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = new StateScore(30);
                board = new BoardT(state);
                run();
            }
        };

    private MouseAdapter gameMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = (int) ((7 * (e.getY() - 30)) / e.getComponent().getHeight());
                int j = (int) ((7 * (e.getX() - 30)) / e.getComponent().getWidth());
                if (!connecting) {
                    connections.add(new Point(j, i));
                    connecting = true;
                    view.connectToMouse(i, j);
                } else {
                    if (!isValidDot(new Point(j, i))) {
                        terminateConnection();
                        return;
                    }
                    Point lastDot = connections.get(connections.size() - 1);
                    view.drawConnection(i, j, (int) lastDot.getY(), (int) lastDot.getX());
                    connections.add(new Point(j, i));
                    view.connectToMouse(i, j);
                }
            }

        };

    private void terminateConnection() {
        connecting = false;
        board.consume(connections);
        Point lastDot = connections.get(connections.size() - 1);
        state.update(connections.size(), board.getCell((int) lastDot.getY(), (int) lastDot.getX()));
        connections.clear();
        view.updateBoard();
    }

    private boolean isValidDot(Point dot) {
        Point lastDot = connections.get(connections.size() - 1);
        int dist = (int) (Math.abs(dot.getX() - lastDot.getX()) + Math.abs(dot.getY() - lastDot.getY()));
        if (dist != 1)
            return false;
        return board.getCell((int) dot.getY(), (int) dot.getX()) == board.getCell((int) lastDot.getY(),
                                                                                  (int) lastDot.getX());
    }

    private KeyAdapter gameKeyListener = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    try {
                        StateTime tState = (StateTime) state;
                        tState.pause();
                    } catch (ClassCastException ignore) {

                    }
                    view.showPause(pauseContA, pauseNewA, exitA);
                }
            }
        };

    private void exit() {
        System.exit(0);
    }

}
