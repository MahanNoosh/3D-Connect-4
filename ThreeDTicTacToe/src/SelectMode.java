
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The SelectMode class represents the main menu for selecting the game mode in the 4x4x4 Tic Tac Toe game.
 * It provides options for Single Player, Multiplayer, and accessing the instructions on how to win the game.
 */
public class SelectMode extends JPanel implements MouseListener {

    // Coordinates and dimensions for menu buttons
    private int buttonX = 350;
    private int baseButtonY = 350;
    private int buttonHeight = 60;
    private int buttonWidth = 150;

    private MyFrame frame; // Reference to the main MyFrame

    /**
     * Constructor for SelectMode. Initializes the panel and adds mouse listener.
     *
     * @param frame Reference to the main MyFrame to allow panel switching.
     */
    public SelectMode(MyFrame frame) {
        this.frame = frame;
        this.requestFocusInWindow();
        this.setVisible(true);
        this.addMouseListener(this);
    }

    /**
     * Overrides the paint method to draw the menu interface.
     *
     * @param g Graphics context for drawing
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.BLACK);
        g2d.setColor(Color.WHITE);

        // Draw Single Player button
        g2d.drawString("Single Player", buttonX + 35, baseButtonY + 35);
        g2d.drawRect(buttonX, baseButtonY, buttonWidth, buttonHeight);

        // Draw Multiplayer button
        g2d.drawString("Multiplayer", buttonX + 40, baseButtonY + 155);
        g2d.drawRect(buttonX, baseButtonY + (2 * buttonHeight), buttonWidth, buttonHeight);

        // Draw How to win? button
        g2d.drawString("How to win?", buttonX + 36, baseButtonY + 275);
        g2d.drawRect(buttonX, baseButtonY + (4 * buttonHeight), buttonWidth, buttonHeight);

        // Set font for game title
        Font font = new Font("Arial", Font.BOLD, 36);
        g2d.setFont(font);
        g2d.drawString("4x4x4 Tic Tac Toe", 270, 200);
    }

    /**
     * Handles the mouse click events on menu buttons.
     *
     * @param e MouseEvent object containing details of the mouse click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check which button is clicked and initiate the corresponding game mode
        if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth) {
            if (mouseY >= baseButtonY && mouseY <= baseButtonY + buttonHeight) {
                frame.switchPanel(1); // Single Player
            } else if (mouseY >= baseButtonY + (2 * buttonHeight) && mouseY <= baseButtonY + (2 * buttonHeight) + buttonHeight) {
                frame.switchPanel(2); // Multiplayer
            } else if (mouseY >= baseButtonY + (4 * buttonHeight) && mouseY <= baseButtonY + (4 * buttonHeight) + buttonHeight) {
                frame.switchPanel(3); // How to win?
            }
        }
    }

    // Other mouse event methods (not used in this implementation)
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

