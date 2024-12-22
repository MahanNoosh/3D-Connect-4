import javax.swing.*;

/**
 * The MyFrame class represents the main JFrame for the 4x4x4 Tic Tac Toe game.
 * It is responsible for creating and displaying different panels based on the game mode.
 */
public class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int windowSize = 850;
    private JPanel currentPanel;

    /**
     * Constructor for MyFrame. Initializes the JFrame and adds the menu panel by default.
     */
    MyFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 100, windowSize, windowSize);
        switchPanel(0); // Start with the menu panel
        setVisible(true);
    }

    /**
     * Switches the current panel to the specified game mode.
     * 
     * @param playerCount The game mode to be initiated (0 for menu, 1 for single player, 2 for multiplayer, 3 for instructions)
     */
    public void switchPanel(int playerCount) {
        if (currentPanel != null) {
            remove(currentPanel);
        }

        switch (playerCount) {
            case 0:
                currentPanel = new SelectMode(this);
                break;
            case 1:
            case 2:
                currentPanel = new ThreeDTicTacToe(playerCount, this);
                break;
            case 3:
                currentPanel = new HowToPlay(this);
                break;
            default:
                throw new IllegalArgumentException("Invalid game mode: " + playerCount);
        }

        add(currentPanel);
        currentPanel.requestFocusInWindow();
        revalidate(); // Refresh the JFrame to display the new panel
        repaint();
    }

    /**
     * The main method that launches the application.
     * 
     * @param args (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyFrame::new);
    }
}

