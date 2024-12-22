import javax.swing.*;

/*Search sources:
 * Stack overflow
 * ChatGPT (Used for getting idea and learning about JFrame and JPanel, not coding)
 * YouTube for AI, but didn't help and I came up with my own AI(AI was monster! took me weeks to find the best calculations).
 */






/**
 * The MyFrame class represents the main JFrame for the 4x4x4 Tic Tac Toe game.
 * It is responsible for creating and displaying different panels based on the game mode.
 */
public class MyFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final int windowSize = 850;
    ThreeDTicTacToe panel;
    SelectMode selection;
    HowToPlay instruction;

    /**
     * Constructor for MyFrame. Initializes the JFrame and adds the appropriate panel based on the game mode.
     * @param playerCount The game mode to be initiated (0 for menu, 1 for single player, 2 for multiplayer, 3 for instructions)
     */
    MyFrame(int playerCount) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        switch(playerCount) {
            case 0:
                selection = new SelectMode();
                this.setBounds(500, 100, windowSize, windowSize);
                this.add(selection);
                this.setVisible(true);
                break;
            case 1:
            case 2:
                panel = new ThreeDTicTacToe(playerCount);
                this.setBounds(500, 100, windowSize, windowSize);
                this.add(panel);
                this.setVisible(true);
                break;
            case 3:
                instruction = new HowToPlay();
                this.setBounds(500, 100, windowSize, windowSize);
                this.add(instruction);
                this.setVisible(true);
                break;
        }
    }

    /**
     * The main method that launches the application.
     * @param args (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyFrame(0); // Start with the menu panel
        });
    }
}
