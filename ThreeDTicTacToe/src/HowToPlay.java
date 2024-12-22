import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The HowToPlay class represents a panel that displays instructions on how to play the game.
 * It includes navigation buttons to move between instruction slides.
 */
public class HowToPlay extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    private File c1, c2, c3;
    private int slide = 1;
    private MyFrame frame; // Reference to the main MyFrame

    /**
     * Constructor for the HowToPlay class. Initializes the panel and sets up mouse listener.
     */
    HowToPlay(MyFrame frame) {
    	this.frame = frame;
    	this.requestFocusInWindow();
        this.setVisible(true);
        this.addMouseListener(this);

        // Set the file path for win conditions
        this.c1 = new File("condition1.png");
        this.c2 = new File("condition2.png");
        this.c3 = new File("condition3.png");
    }

    /**
     * Override of the paint method to customize the drawing on the panel.
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.BLACK);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(650, 740, 150, 60);
        g2d.drawString("Next >", 705, 775);
        g2d.drawRect(50, 740, 150, 60);
        g2d.drawString("< Back", 100, 775);
        g2d.drawRect(350, 740, 150, 60);
        g2d.drawString("Play!", 410, 775);
        switch (slide) {
            case 1:
                try {
                    image = ImageIO.read(c1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g2d.drawString("One of the win conditions is when someone has their cubes in a 3D diagonal row!", 175, 650);
                break;
            case 2:
                try {
                    image = ImageIO.read(c2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g2d.drawString("Another win condition is when someone has their cubes in any XYZ diagonal cubes row!", 150, 650);
                break;
            case 3:
                try {
                    image = ImageIO.read(c3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g2d.drawString("Last win condition is when someone has their cubes in any XYZ cubes row!", 190, 650);
                break;
        }

        // Check if the image is loaded before drawing
        if (image != null) {
            g.drawImage(image, 175, 30, null);
        }
    }

    // Implement MouseListener methods as needed
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (mouseY >= 740 && mouseY <= 800) {

            if (mouseX >= 650 && mouseX <= 800) {
                if (slide < 3) {
                    slide++;
                    repaint();
                }
            } else if (mouseX >= 50 && mouseX <= 200) {
                if (slide > 1) {
                    slide--;
                    repaint();
                }
            } else if (mouseX >= 350 && mouseX <= 500) {
                frame.switchPanel(0);;
                this.removeAll();
                this.setVisible(false);
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
