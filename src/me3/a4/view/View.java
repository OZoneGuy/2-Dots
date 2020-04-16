package me3.a4.view;
/**
   @brief The file responsible for the view in the CVM.
   @author Omar Alkersh - alkersho
   @file View.java
   @data 2020-04-01
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import me3.a4.BoardT;
import me3.a4.GameState;
import me3.a4.StateMoves;
import me3.a4.StateScore;
import me3.a4.StateTime;

/**
   @brief The class responsible for the view in the CVM.
*/
public class View extends Frame{

    private GameState state;
    private BoardP boardP;

    /**
       @brief The constructor for the View class.

       @param winListener The listener responsible for window events, WIP.
       @param buttonSGameListener The Actio listener responsible for the start game button.
       @param closeListener The Actio listener responsible for the close button.

     */
    public View(WindowListener winListener,
                ActionListener buttonSGameListener,
                ActionListener closeListener) {

        this.addWindowListener(winListener);

        removeAll();

        add(getMainMenu(buttonSGameListener, closeListener));
        setVisible(true);
    }


    private Container getMainMenu(ActionListener start,
                                  ActionListener exit){
        Panel menu = new Panel(new GridLayout(2, 1, 0, 10));
        Button startB = new Button("Start");
        Button exitB = new Button("Exit");

        startB.addActionListener(start);
        exitB.addActionListener(exit);

        menu.add(startB);
        menu.add(exitB);

        return menu;
    }

    /**
       @brief Show the menu to choose the game mode.

       @param timerA The action Listener for the "Timed mode" button.
       @param movesA The action Listener for the "Moves mode" button.
       @param scoreA The action Listener for the "Score mode" button.
    */
    public void gameMenu(ActionListener timerA,
                         ActionListener movesA,
                         ActionListener scoreA){

        Panel menu = new Panel(new GridLayout(3, 1, 0, 10));
        Button timerB = new Button("Timed mode");
        Button movesB = new Button("Moves mode");
        Button scoreB = new Button("Score mode");

        timerB.addActionListener(timerA);
        movesB.addActionListener(movesA);
        scoreB.addActionListener(scoreA);

        menu.add(timerB);
        menu.add(movesB);
        menu.add(scoreB);

        removeAll();

        menu.setVisible(true);
        this.add(menu);
        this.setVisible(true);
    }

    /**
       @brief Shows the pause menu.

       @param contA The action Listener for the "Continue" button.
       @param newA The action Listener for the "New Game" button.
       @param exitA The action Listener for the "Exit" button.
    */
    public void showPause(ActionListener contA,
                          ActionListener newA,
                          ActionListener exitA){

        Dialog pauseMenu = new Dialog(this, "Pause Menu", true);

        Panel menu = new Panel(new GridLayout(3, 1, 0, 10));
        Button contB = new Button("Continue");
        Button newB = new Button("Start a new game");
        Button exitB = new Button("Exit");

        contB.addActionListener(contA);
        newB.addActionListener(newA);
        exitB.addActionListener(exitA);

        menu.add(contB);
        menu.add(newB);
        menu.add(exitB);

        ActionListener dismissD = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    pauseMenu.dispose();
                }
            };
        ActionListener closeD = new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    boardP = null;
                    pauseMenu.dispose();
                }
            };

        contB.addActionListener(dismissD);
        newB.addActionListener(closeD);
        exitB.addActionListener(closeD);

        pauseMenu.add(menu);

        pauseMenu.setVisible(true);
    }

    /**
       @brief Starts the game and shows the game screen

       @param state The game state to control the game rules.
       @param b The BoardT
       @param mouseClick The mouse event listener, listens for click events in the dots board
    */
    public void startGame(GameState state, BoardT b,
                          MouseListener mouseClick, KeyListener keyListener){
        this.boardP = new BoardP(b, mouseClick);
        this.state = state;

        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());
        panel.add(boardP, BorderLayout.CENTER);

        Panel labelPanel = new Panel();
        labelPanel.setLayout(new FlowLayout());
        updateGameLabels(labelPanel);

        panel.add(labelPanel, BorderLayout.PAGE_START);

        panel.setVisible(true);
        labelPanel.setVisible(true);
        boardP.setVisible(true);
        boardP.repaint();

        this.removeAll();
        this.add(panel);
        this.boardP.addKeyListener(keyListener);
        this.setVisible(true);
        boardP.paintComponents(this.getGraphics());
    }

    /**
       @brief Draws a line connecting two dots.

       @param i1 The row of the first dot
       @param j1 The column of the first dot
       @param i2 The row of the second dot
       @param j2 The column of the first dot
     */
    public void drawConnection(int i1, int j1,
                               int i2, int j2){
        if (this.boardP != null)
            this.boardP.drawConnection(i1, j1, i2, j2);

    }

    /**
       @brief Draws a line between a dot and the mouse

       @param i The row of the dot
       @param j The column of the dot
     */
    public void connectToMouse(int i, int j){
        if (this.boardP != null)
            this.boardP.drawToMouse(i, j);
    }

    /**
       @brief Shows the win screen
     */
    public void showWin(){
        Panel winP = new Panel();
        winP.setLayout(new BorderLayout());
        winP.add(new Label("You win!!"), BorderLayout.CENTER);

        this.removeAll();
        this.boardP = null;
        this.state = null;
        for (KeyListener k: this.getKeyListeners())
            this.removeKeyListener(k);

        this.add(winP);
        this.setVisible(true);
    }

    /**
       @brief Shows the lose screen
     */
    public void showLose(){
        Panel loseP = new Panel();
        loseP.setLayout(new BorderLayout());
        loseP.add(new Label("You Lost :("), BorderLayout.CENTER);

        this.removeAll();
        this.boardP = null;
        this.state = null;
        for (KeyListener k: this.getKeyListeners())
            this.removeKeyListener(k);

        this.add(loseP);
        this.setVisible(true);
    }

    public void updateBoard(){
        this.boardP.updateBoard();
    }

    private void updateGameLabels(Panel lPanel){
        lPanel.removeAll();
        if (this.state instanceof StateTime){
            StateTime tState = (StateTime) this.state;
            lPanel.add(new Label("Score:"));
            lPanel.add(new Label(String.valueOf(tState.getScore())));
            lPanel.add(new Label("Time:"));
            lPanel.add(new Label(String.valueOf(tState.getRemTime())));
        }
        else if (this.state instanceof StateScore){
            StateScore tState = (StateScore) this.state;
            lPanel.add(new Label("Score:"));
            lPanel.add(new Label(String.valueOf(tState.getScore())));
        }
        else if (this.state instanceof StateMoves){
            StateMoves tState = (StateMoves) this.state;
            lPanel.add(new Label("Score:"));
            lPanel.add(new Label(String.valueOf(tState.getScore())));
            lPanel.add(new Label("Moves:"));
            lPanel.add(new Label(String.valueOf(tState.getRemMoves())));
        }
        lPanel.setVisible(true);
    }
    public void updateGameLabels(){
        Panel lPanel = (Panel) ((Container) this.getComponent(0)).getComponent(1);
        if (this.state instanceof StateTime){
            StateTime tState = (StateTime) this.state;
            ((Label) lPanel.getComponent(1) ).setText(String.valueOf(tState.getScore()));
            ((Label) lPanel.getComponent(3) ).setText(String.valueOf(tState.getRemTime()));
        }
        else if (this.state instanceof StateScore){
            StateScore tState = (StateScore) this.state;
            ((Label) lPanel.getComponent(1) ).setText(String.valueOf(tState.getScore()));
        }
        else if (this.state instanceof StateMoves){
            StateMoves tState = (StateMoves) this.state;
            ((Label) lPanel.getComponent(1) ).setText(String.valueOf(tState.getScore()));
            ((Label) lPanel.getComponent(3) ).setText(String.valueOf(tState.getRemMoves()));
        }
    }

    private Color getColour(BoardT.Colour c){
        if (c == BoardT.Colour.BLUE)
            return Color.BLUE;
        if (c == BoardT.Colour.GREEN)
            return Color.GREEN;
        if (c == BoardT.Colour.ORANGE)
            return Color.ORANGE;
        if (c == BoardT.Colour.PINK)
            return Color.PINK;
        return Color.RED;
    }

    // Inner class for the dots board
    private class BoardP extends JPanel {

        private BoardT b;

        private List<Line2D.Double> lines;
        private Point2D toMouse;
        private Point2D mouseP;

        BoardP(BoardT b, MouseListener mouseListener){
            this.b = b;
            lines = new ArrayList<>();
            this.setVisible(false);
            this.addMouseListener(mouseListener);
        }

        void updateBoard(){
            lines.clear();
            this.repaint();
            this.toMouse = null;
        }

        void drawConnection(int i1, int j1,
                            int i2, int j2){
            double x1 = ((double) this.getWidth()) * ( j1 + 1 )/ 7;
            double y1 = ((double) this.getHeight()) * ( i1 + 1 )/ 7;
            double x2 = ((double) this.getWidth()) * ( j2 + 1 )/ 7;
            double y2 = ((double) this.getHeight()) * ( i2 + 1 )/ 7;
            lines.add(new Line2D.Double(x1, y1,
                                        x2, y2));
            // remove all mouse tracking
            for (MouseMotionListener m:this.getMouseMotionListeners())
                this.removeMouseMotionListener(m);
            mouseP = null;
            toMouse = null;
            repaint();
        }

        void drawToMouse(int i, int j){
        	i++;
        	j++;
            double x = ((double) this.getWidth()) * j / 7;
            double y = ((double) this.getHeight()) * i/ 7;
            toMouse = new Point2D.Double(x, y);
            mouseP = new Point2D.Double(x, y);
            addMouseMotionListener(new MouseMotionAdapter(){
                    @Override
                    public void mouseMoved(MouseEvent e){
                        mouseP = e.getPoint();
                        repaint();
                    }
                });
            this.repaint();
        }

        private BoardT.Colour getNodeColour(Point2D p){
            int i = (int) ((7*(p.getY() - 30))/this.getHeight());
            int j = (int) ((7*(p.getX() - 30))/this.getWidth());

            return b.getCell(i, j);
        }

        @Override
        public void paint(Graphics g){
        	super.paint(g);
            Graphics2D g2d = (Graphics2D) g;

            // draw the dots on the board
            for (int i = 1; i <= BoardT.SIZE; i++)
                for (int j = 1; j <= BoardT.SIZE; j++){
                    g2d.setColor(getColour(b.getCell(i- 1, j-1)));
                    double x = ((double) this.getWidth()) * j / 7 - 30;
                    double y = ((double) this.getHeight()) * i/ 7 - 30;
                    g2d.fill(new Ellipse2D.Double(x, y, 60, 60));
                }

            // draw lines between dots
            g2d.setStroke(new BasicStroke((float)30.0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (Line2D.Double line : lines) {
                g2d.setColor(getColour(getNodeColour(line.getP1())));
                g2d.drawLine((int) line.getP1().getX(), (int) line.getP1().getY(),
                        (int) line.getP2().getX(), (int) line.getP2().getY());
            }

            // draw line to mouse
            if (this.toMouse != null){
                g2d.setColor(getColour(getNodeColour(toMouse)));
                g2d.drawLine((int) toMouse.getX(), (int) toMouse.getY(),
                			 (int) mouseP.getX(), (int) mouseP.getY());
            }
        }
    }
}
