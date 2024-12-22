import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

/**
 * ThreeDTicTacToe class represents the main panel for a 3D Tic-Tac-Toe game.
 * It extends JPanel and implements the MouseListener interface for user interactions.
 */
public class ThreeDTicTacToe extends JPanel implements MouseListener {
    
    // Serialization ID
    private static final long serialVersionUID = 1L;
    
    // Constants for grid size, cube size, and spacing
    private final int gridSize = 4;
    private final int cubeSize = 50;
    private final int spacing = (int) (gridSize * 28.5);
    
    // 3D array to store cubes in the game grid
    private Cube[][][] cubes;
    
    // Background color constant
    private final Color BACKGROUND_COLOR = Color.black;
    
    // Enumeration for game modes and player turns
    private enum Mode {SINGLE_PLAYER, MULTIPLAYER};
    public enum Player{ONE, TWO, AI};
    public static Player turn; 
    
    // Game mode, running state, and AI-related variables
    private Mode GameMode;
    private boolean running = true;
    private ThreeDAI ai;
    private WinChecker winChecker;
    private int starter = (int)(2*Math.random()+1);
    private Timer timer;
    private boolean aiIsThinking = false;
    private MyFrame parentFrame;
    
    /**
     * Constructor for ThreeDTicTacToe.
     * @param GAME_MODE Game mode selector (1 for single player, 2 for multiplier)
     */
    public ThreeDTicTacToe(int GAME_MODE, MyFrame parentFrame) {
        this.parentFrame = parentFrame;
        // Set panel properties
        this.setVisible(true);
        this.addMouseListener(this);
        this.setBackground(BACKGROUND_COLOR);
        this.requestFocusInWindow();

        // Initialize the 3D array of cubes
        cubes = new Cube[gridSize][gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                for (int z = 0; z < gridSize; z++) {
                    int xOffset = x * (cubeSize + spacing) + 4 * cubeSize;
                    int yOffset = y * (cubeSize + spacing) + 4 * cubeSize;
                    int zOffset = z;
                    cubes[x][y][z] = new Cube(xOffset, yOffset, zOffset, cubeSize, BACKGROUND_COLOR);
                }
            }
        }
        
        // Set game mode and initialize AI and win checker
        switch(GAME_MODE) {
            case 1: 
                GameMode = Mode.SINGLE_PLAYER;
                ai = new ThreeDAI(gridSize, cubes);
                switch(starter) {
                    case 1: turn = Player.ONE;
                            break;
                    case 2: turn = Player.AI;
                }
                break;
            case 2: GameMode = Mode.MULTIPLAYER;
                    switch(starter) {
                        case 1: turn = Player.ONE;
                                break;
                        case 2: turn = Player.TWO;
                    }
        }
        
        winChecker = new WinChecker(gridSize, cubes);
    }

    /**
     * Override of the paint method to draw the game elements on the panel.
     * @param g Graphics context for drawing
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw each cube in the 3D array
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                for (int z = 0; z < gridSize; z++) {
                    cubes[x][y][z].draw(g2d);
                }
            }
        }
        
        // Display game state information
        String state="";
        if(winChecker.checkWin() == -1) {
            switch(turn) {
                case ONE: 
                    if(GameMode == Mode.SINGLE_PLAYER)state ="Your turn";
                    else state ="Red's turn";
                    break;
                case TWO: 
                    state ="Blue's turn";
                    break;
                case AI: state ="AI's turn";
            }
        }
        else if(winChecker.checkWin() == 1) {
            switch(turn) {
                case ONE: 
                    if(GameMode == Mode.SINGLE_PLAYER)state ="AI won!";
                    else state ="Blue won!";
                    break;
                case TWO: 
                    state ="Red won!";
                    break;
                case AI: state ="You won!";
            }
        }
        else if(winChecker.checkWin() == 0) state = "Tie";
        
        // Display game over message and instructions
        if(!running) {
            g2d.setColor(new Color(0,0,0,95));
            g2d.fillRect(125, 325, 600, 200);
            g2d.setColor(Color.WHITE);
            g2d.drawString("Click anywhere to restart.", 340, 460);
            g2d.setColor(new Color(154, 205, 50));
            Font font = new Font("Arial", Font.BOLD, 55);
            g2d.setFont(font);
            g2d.drawString(state, 320, 400);
        }
        else g2d.drawString(state, 10, 20);
    }

    /**
     * Override of the mouseClicked method for handling mouse clicks.
     * @param e MouseEvent containing information about the click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        // Handle mouse clicks during gameplay
        if(running) {
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    for (int z = 0; z < gridSize; z++) {
                        if (cubes[x][y][z].contains(mouseX, mouseY)) {
                            if(!cubes[x][y][z].captured()){
                                // Update cube selection and player turn based on game mode
                                switch(GameMode) {
                                    case MULTIPLAYER:
                                        switch(turn) {
                                            case ONE: 
                                                cubes[x][y][z].clicked(1);
                                                turn = Player.TWO;
                                                repaint();
                                                break;
                                            case TWO: 
                                                cubes[x][y][z].clicked(2);
                                                turn = Player.ONE;
                                                repaint();
                                                break;
                                        }
                                        break;
                                    case SINGLE_PLAYER:
                                        switch(turn) {
                                            case ONE: 
                                                cubes[x][y][z].clicked(1);  	                     		
                                                turn = Player.AI;
                                                repaint();
                                                ai.takeAction();
                                                repaint();
                                                break;
                                            case AI:
                                                break;
                                        }
                                        break;
                                } 
                            }
                            break;
                        }
                    }
                }
            } 
        }
        // Handle mouse clicks after the game ends
        //else new MyFrame(0);
        else parentFrame.switchPanel(0);
        
        // Check for a winner or a tie
        if(winChecker.checkWin() == 1) {
            displayWinnerCubes();
            running =false;
        }
        else if(winChecker.checkWin() == 0) {
            running =false;
        }
    }

    // Unused mouse event methods
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        // Start AI turn when mouse enters the panel in single-player mode
        if(GameMode == Mode.SINGLE_PLAYER) {
            startAi();
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Start the AI turn using a Timer for a delay.
     */
    public void startAi() {
        ActionListener aiAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (turn == Player.AI && running) {
                    ai.takeAction();
                    repaint();
                    aiIsThinking = false;
                    if(winChecker.checkWin() != -1) {
                        displayWinnerCubes();
                        running =false;
                    }
                }
            }
        };

        timer = new Timer(5, aiAction);
        if(!aiIsThinking) timer.start();	
    }

    /**
     * Display the winning cubes with a different visual effect.
     */
    public void displayWinnerCubes() {
        winChecker = new WinChecker(gridSize, cubes);
        if(winChecker.checkWin() == 1){
            // Change transparency of all cubes
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    for (int z = 0; z < gridSize; z++) {
                        cubes[x][y][z].changeTransparency();
                    }
                }
            }
            
            // Make the winning cubes stand out
            for(int i = 0; i < gridSize ; i++) {
                winChecker.getWinerCubes()[i].makeWinner();
            }
            repaint();
        }
    }
}
