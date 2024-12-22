import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The Cube class represents a single cube in the 3D Tic-Tac-Toe game grid.
 * It contains information about its position, size, color, ownership, and visual representation.
 */
class Cube {
    
    private int x, y, z, cubeSize, frontX, frontY, backX, backY;
    private Color color;
    private enum CapturedBy {PLAYER_ONE, PLAYER_TWO, NO_ONE, PLAYER_ONE_WINNER, PLAYER_TWO_WINNER};
    private CapturedBy status = CapturedBy.NO_ONE;  // Cube status
    private final int transparencyAfterWin = 100; // Range 0-255 for transparency of non-winner cubes after someone won the game
    private final Color 
            BORDER_COLOR = Color.WHITE,            // Players' border color
            PLAYER_ONE = Color.RED,                // Player One color
            PLAYER_TWO = Color.BLUE,               // Player Two color
            DEFAULT_COLOR;
    private Color                                           // Colors for some operations (display winner and lower transparency of others)
            PLAYER_ONE_COLOR = PLAYER_ONE,
            PLAYER_TWO_COLOR = PLAYER_TWO,
            PLAYER_ONE_WINNER_COLOR = PLAYER_ONE,
            PLAYER_TWO_WINNER_COLOR = PLAYER_TWO;
    
    private int captureValue = 0;

    /**
     * Constructor for Cube.
     * @param x X-coordinate of the cube
     * @param y Y-coordinate of the cube
     * @param z Z-coordinate of the cube
     * @param cubeSize Size of the cube
     * @param BACKGROUND_COLOR Default background color for cubes
     */
    public Cube(int x, int y, int z, int cubeSize, final Color BACKGROUND_COLOR) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.cubeSize = cubeSize;
        this.DEFAULT_COLOR = BACKGROUND_COLOR;
    }

    /**
     * Checks if the cube contains the specified point.
     * @param mouseX X-coordinate of the point
     * @param mouseY Y-coordinate of the point
     * @return True if the cube contains the point, false otherwise
     */
    public boolean contains(int mouseX, int mouseY) {
        return mouseX >= backX && mouseX <= frontX + cubeSize &&
               mouseY >= backY && mouseY <= frontY + cubeSize;
    }

    /**
     * Sets the color of the cube.
     * @param color Color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Draws the cube on the specified Graphics2D context.
     * @param g2d Graphics2D context for drawing
     */
    public void draw(Graphics2D g2d) {
        switch(status) {            // Color the cube depending on who captured it
            case NO_ONE:
                setColor(DEFAULT_COLOR);
                break;
            case PLAYER_ONE:
                setColor(PLAYER_ONE_COLOR);
                break;
            case PLAYER_TWO:
                setColor(PLAYER_TWO_COLOR);
                break;
            case PLAYER_ONE_WINNER:
                setColor(PLAYER_ONE_WINNER_COLOR);
                break;
            case PLAYER_TWO_WINNER:
                setColor(PLAYER_TWO_WINNER_COLOR);
                break;
        }

        g2d.setColor(color);
          
        int[] pointsX = {frontX + cubeSize, backX + cubeSize, backX, backX, frontX, frontX + cubeSize/3, frontX + cubeSize/3 , frontX + cubeSize};
        int[] pointsY = {frontY, backY, backY, backY + cubeSize, frontY + cubeSize, frontY + cubeSize, frontY + cubeSize/3, frontY + cubeSize/3};
        g2d.fillPolygon(pointsX, pointsY, 8);
          
        if(z == 0)  g2d.fillRect(frontX + cubeSize/3, frontY + cubeSize/3, cubeSize - cubeSize/3, cubeSize- cubeSize/3);
        
        setColor(BORDER_COLOR); // Draw the border of the cubes
        g2d.setColor(color);

        frontX = x - z * (cubeSize/3);
        frontY = y - z * (cubeSize/3);

        // Front square
        if(z == 0) g2d.drawRect(frontX, frontY, cubeSize, cubeSize);
        else {
            frontX -= z * (cubeSize/3);
            frontY -= z * (cubeSize/3);
            g2d.drawLine(frontX, frontY, frontX + cubeSize, frontY);
            g2d.drawLine(frontX, frontY, frontX, frontY + cubeSize);
            g2d.drawLine(frontX + cubeSize, frontY, frontX + cubeSize, frontY + cubeSize/3);
            g2d.drawLine(frontX, frontY + cubeSize, frontX + cubeSize/3, frontY + cubeSize);
        }

        backX = frontX - cubeSize/3;
        backY = frontY - cubeSize/3;

        // Back square
        g2d.drawLine(backX, backY, backX + cubeSize, backY);
        g2d.drawLine(backX, backY, backX, backY + cubeSize);

        // Connection to the back square
        g2d.drawLine(frontX, frontY, backX , backY); // Top left
        g2d.drawLine(frontX + cubeSize, frontY, backX + cubeSize, backY); // Top right
        g2d.drawLine(frontX , frontY + cubeSize, backX , backY + cubeSize); // Bottom left
        
        //g2d.drawString(""+captureValue, frontX, frontY);
    }

    /**
     * Handles the click event on the cube.
     * @param player Player number (1 or 2)
     */
    public void clicked(int player) {
        switch(player) {
            case 1:
                status = CapturedBy.PLAYER_ONE;
                break;
            case 2:
                status = CapturedBy.PLAYER_TWO;
                break;
            case 0: 
                status = CapturedBy.NO_ONE;
                break;
        }
    }

    /**
     * Marks the cube as a winner.
     */
    public void makeWinner() {
        switch(status) {
            case PLAYER_ONE: 
                status = CapturedBy.PLAYER_ONE_WINNER;
                break;
            case PLAYER_TWO: 
                status = CapturedBy.PLAYER_TWO_WINNER;
                break;
        }
    }

    /**
     * Changes transparency of the cube after a win.
     */
    public void changeTransparency() {
        PLAYER_ONE_COLOR = new Color(PLAYER_ONE.getRed(), PLAYER_ONE.getGreen(), PLAYER_ONE.getBlue(), transparencyAfterWin);
        PLAYER_TWO_COLOR = new Color(PLAYER_TWO.getRed(), PLAYER_TWO.getGreen(), PLAYER_TWO.getBlue(), transparencyAfterWin);
    }

    /**
     * Checks if the cube is captured.
     * @return True if captured, false otherwise
     */
    public boolean captured(){
        return status != CapturedBy.NO_ONE;
    }

    /**
     * Gets the player who captured the cube.
     * @return Player number (1 or 2)
     */
    public int getPlayer() {
        switch(status) {            // Get who captured the cube
            case PLAYER_ONE:
            case PLAYER_ONE_WINNER:
                return 1;
                
            case PLAYER_TWO:
            case PLAYER_TWO_WINNER:
                return 2;
                
            default: return 0;
        }
    }

    /**
     * Sets the value of the cube.
     * @param value Value to set
     */
    public void setValue(int value) {
        captureValue = value;
    }

    /**
     * Gets the value of the cube.
     * @return Value of the cube
     */
    public int getValue(){
        return captureValue;
    }
}
